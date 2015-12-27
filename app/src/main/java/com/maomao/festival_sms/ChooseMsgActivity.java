package com.maomao.festival_sms;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.maomao.festival_sms.bean.Festival;
import com.maomao.festival_sms.bean.FestivalLab;
import com.maomao.festival_sms.bean.Msg;
import com.maomao.festival_sms.fragment.FestivalCategoryFragment;

public class ChooseMsgActivity extends AppCompatActivity {

    private ListView mLvMsgs;
    private FloatingActionButton mFabToSend ;
    private ArrayAdapter<Msg> mAdapter ;
    private int mFestivalId;
    private LayoutInflater mInflater ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_msg);
        mInflater = LayoutInflater.from(this);
        mFestivalId = getIntent().getIntExtra(FestivalCategoryFragment.ID_FESTIVAL, -1);

        setTitle(FestivalLab.getInstance().getFestivalsById(mFestivalId).getName());

        initViews();
        initEvent();
    }

    private void initEvent() {
        mFabToSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMsgActivity.toActivity(ChooseMsgActivity.this,mFestivalId , -1 );
            }
        });
    }

    private void initViews() {
        mLvMsgs = (ListView) findViewById(R.id.id_lv_msg);
        mFabToSend = (FloatingActionButton) findViewById(R.id.id_fab_toSend);


        mLvMsgs.setAdapter(new ArrayAdapter<Msg>(this,-1, FestivalLab.getInstance().getMsgsByFestivalId(mFestivalId)){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = mInflater.inflate(R.layout.item_msg,parent,false);
                }
                TextView content = (TextView) convertView.findViewById(R.id.id_tv_content);
                Button toSent = (Button) convertView.findViewById(R.id.bt_to_send);

                content.setText("     " + getItem(position).getContent());
                toSent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SendMsgActivity.toActivity(ChooseMsgActivity.this,mFestivalId , getItem(position).getId() );
                    }
                });

                return convertView;
            }
        });
    }

}
