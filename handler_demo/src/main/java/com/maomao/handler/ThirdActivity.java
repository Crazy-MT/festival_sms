package com.maomao.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Mao on 2016/1/19.
 * 子线程的handler ， 可以处理耗时操作。
 */
public class ThirdActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this) ;
        text.setText("handler Thread");
        setContentView(text);

        HandlerThread thread = new HandlerThread("handler Thread");
        thread.start();

        Handler handler = new Handler(thread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.e("handler Thread" , msg.toString());
            }
        };

        handler.sendEmptyMessage(1);

    }
}
