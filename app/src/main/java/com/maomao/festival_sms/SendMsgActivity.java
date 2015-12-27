package com.maomao.festival_sms;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maomao.festival_sms.bean.Festival;
import com.maomao.festival_sms.bean.FestivalLab;
import com.maomao.festival_sms.bean.Msg;
import com.maomao.festival_sms.biz.SmsBiz;
import com.maomao.festival_sms.view.FlowLayout;

import java.util.HashSet;

import static com.maomao.festival_sms.R.id.id_btn_add;
import static com.maomao.festival_sms.R.id.id_et_content;
import static com.maomao.festival_sms.R.id.id_fab_send;
import static com.maomao.festival_sms.R.id.id_fl_contacts;
import static com.maomao.festival_sms.R.id.id_layout_loading;

public class SendMsgActivity extends AppCompatActivity {

    private static final String KEY_FESTIVAL_ID = "festivalId";
    private static final String KEY_MSG_ID = "msgId";
    private static final int CODE_REQUEST = 1;

    private int mFestivalId;
    private int msgId;

    private Festival mFestival;
    private Msg mMsg;

    private EditText mEdMsg;
    private Button mBtnAdd;
    private FlowLayout mFlContacts;
    private FloatingActionButton mFabSend;

    private View mLayoutLoading;

    private HashSet<String> mContactNames = new HashSet<>();
    private HashSet<String> mContactNums = new HashSet<>();
    private LayoutInflater mflater;

    public static final String ACTION_SEND_MSG = "ACTION_SEND_MSG";
    public static final String ACTION_DELIVER_MSG = "ACTION_DELIVER_MSG";

    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;
    private BroadcastReceiver mSendBroadcastReceiver;
    private BroadcastReceiver mDeliverBroadcastReceiver;

    private SmsBiz mSmsBiz = new SmsBiz();
    private int mMsgSendCount ;
        private int mTotalCount ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
        mflater = LayoutInflater.from(this);
        initDatas();
        initViews();
        initEvents();

        initRecivers();
    }

    private void initRecivers() {
        Intent sendIntent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this, 0, sendIntent, 0);
        Intent deliverIntent = new Intent(ACTION_DELIVER_MSG);
        mDeliverPi = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

        registerReceiver(mSendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mMsgSendCount++;
                if (getResultCode() == RESULT_OK)
                    Log.e("TAG", "短信发送成功" + mMsgSendCount + "/" + mTotalCount);
                else
                    Log.e("TAG", "短信发送失败");
                if (mMsgSendCount == mTotalCount){
                    finish();
                }
            }



        }, new IntentFilter(ACTION_SEND_MSG));

        registerReceiver(mDeliverBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.e("TAG", "联系人接收成功");

            }
        }, new IntentFilter(ACTION_DELIVER_MSG));
    }

    private void initDatas() {
        mFestivalId = getIntent().getIntExtra(KEY_FESTIVAL_ID, -1);
        msgId = getIntent().getIntExtra(KEY_MSG_ID, -1);

        mFestival = FestivalLab.getInstance().getFestivalsById(mFestivalId);
        setTitle(mFestival.getName());
    }

    private void initViews() {
        mEdMsg = (EditText) findViewById(id_et_content);
        mBtnAdd = (Button) findViewById(id_btn_add);
        mFlContacts = (FlowLayout) findViewById(id_fl_contacts);
        mFabSend = (FloatingActionButton) findViewById(id_fab_send);
        mLayoutLoading = (View) findViewById(id_layout_loading);
        mLayoutLoading.setVisibility(View.GONE);

        if (msgId != -1) {
            mMsg = FestivalLab.getInstance().getMsgByid(msgId);
            mEdMsg.setText(mMsg.getContent());
        }
    }

    private void initEvents() {
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CODE_REQUEST);
            }
        });

        mFabSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContactNums.size() == 0) {
                    Toast.makeText(SendMsgActivity.this, "请先选择联系人", Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg = mEdMsg.getText().toString();
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(SendMsgActivity.this, "短信内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mLayoutLoading.setVisibility(View.VISIBLE);
                mTotalCount = mSmsBiz.sendMsg(mContactNums, mEdMsg.getText().toString(), mSendPi, mDeliverPi);
                mMsgSendCount = 0 ;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
                cursor.moveToFirst();
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                String number = getContactNumber(cursor);
                if (!TextUtils.isEmpty(number)) {
                    mContactNames.add(contactName);
                    mContactNums.add(number);
                    addTag(contactName);
                }
            }
        }
    }

    private void addTag(String contactName) {
        TextView textView = (TextView) mflater.inflate(R.layout.tag, mFlContacts, false);
        textView.setText(contactName);
        mFlContacts.addView(textView);
    }

    private String getContactNumber(Cursor cursor) {
        int numberCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String number = null;
        if (numberCount > 0) {
            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " + " + contactId, null, null);
            phoneCursor.moveToFirst();
            number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneCursor.close();
        }
        cursor.close();
        return number;
    }

    public static void toActivity(Context context, int festivalId, int msgId) {
        Intent intent = new Intent(context, SendMsgActivity.class);
        intent.putExtra(KEY_FESTIVAL_ID, festivalId);
        intent.putExtra(KEY_MSG_ID, msgId);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSendBroadcastReceiver);
        unregisterReceiver(mDeliverBroadcastReceiver);
    }
}
