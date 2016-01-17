package com.maomao.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int pic[] = {R.drawable.one, R.drawable.two, R.drawable.three};

    private int index = 0;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(MainActivity.this, "Callback", Toast.LENGTH_SHORT).show();
            return false;//若返回true则拦截成功
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(MainActivity.this, "拦截未成功", Toast.LENGTH_SHORT).show();
            mHandlerText.setText("msg.arg1:" + msg.arg1 + " msg.arg2:" + msg.arg2 + " person:" + msg.obj);
        }
    };

    private MyRunnable myRunnable = new MyRunnable();
    private ImageView mHandlerImg;
    private Button mRemoveRunnableBtn ;
    private TextView mHandlerText;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_removeRunnable:
                mHandler.removeCallbacks(myRunnable);
                mHandler.sendEmptyMessage(1);
        }
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            index++;
            index = index % 3;
            mHandlerImg.setImageResource(pic[index]);
            mHandler.postDelayed(myRunnable, 1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandlerImg = (ImageView) findViewById(R.id.iv_handler);
        mHandlerText = (TextView) findViewById(R.id.tv_handler);
        mRemoveRunnableBtn = (Button) findViewById(R.id.btn_removeRunnable);
        mRemoveRunnableBtn.setOnClickListener(this);
        mHandler.postDelayed(myRunnable, 1000);

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    /*Message message = new Message();
                    message.arg1 = 100;
                    message.arg2 = 200;*/
                    Message message = mHandler.obtainMessage();
                    Person person = new Person();
                    person.age = 24 ;
                    person.name = "MT" ;
                    message.obj = person;
                    message.sendToTarget();
                    //mHandler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    class Person {
        String name;
        int age;

        @Override
        public String toString() {
            return "name:" + name + " age:" + age;
        }
    }
}
