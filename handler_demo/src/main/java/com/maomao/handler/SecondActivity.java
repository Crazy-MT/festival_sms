package com.maomao.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


/**
 * Created by Mao on 2016/1/19.
 * 创建与线程相关的handler
 */
public class SecondActivity extends AppCompatActivity{

    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    private MyThread myThread ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView t
        myThread = new MyThread();
        myThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myThread.handler.sendEmptyMessage(1);
    }

    class MyThread extends Thread{
        private Handler handler;
        @Override
        public void run() {
            Looper.prepare();
            //handler与当前子线程关联
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Log.e("当前线程ID" , Thread.currentThread()+"");
                }
            };
            Looper.loop();
        }
    }
}
