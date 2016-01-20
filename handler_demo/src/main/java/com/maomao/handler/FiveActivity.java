package com.maomao.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Mao on 2016/1/21.
 */
public class FiveActivity extends AppCompatActivity{

    private TextView textView ;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (1 == msg.what){
                textView.setText("1号变身成功");
            }
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        textView = (TextView) findViewById(R.id.tv_text);

        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                exchangeUI(0);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                exchangeUI(1);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                exchangeUI(2);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                exchangeUI(3);

            }
        }.start();
    }

    private void exchangeUI(int i) {
        switch (i){
            case 0 :
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("0号变身成功");
                    }
                });
                break;
            case 1 :
                mHandler.sendEmptyMessage(1);
                break;
            case 2 :
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("2号变身成功");
                    }
                });
                break;
            case 3 :
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("3号变身成功");
                    }
                });
                break;
        }
    }


}
