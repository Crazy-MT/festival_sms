package com.maomao.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class FourActivity extends AppCompatActivity {

    private boolean isRemove = false;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e("callback ---------", msg.what + "");
            if (msg.what != 1) {
                threadHandler.sendEmptyMessage(2);
                return true;
            } else
                return false;
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            Log.e("主线程handler", Thread.currentThread().toString());
            Message message = new Message();
            message.what = 1;
            threadHandler.sendMessageDelayed(message, 1000);
        }
    };
    private Handler threadHandler;
    private HandlerThread handlerThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);

        handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        threadHandler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.e("callback thread -----", msg.what + "");
                if (msg.what != 1)
                    return true;
                else
                    return false;
            }
        }) {
            @Override
            public void handleMessage(Message msg) {
                if (!isRemove) {
                    Log.e("子线程handler", Thread.currentThread().toString());
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessageDelayed(message, 1000);
                }
            }
        };
    }

    public void sendMessage(View view) {
        handler.sendEmptyMessage(1);
    }

    public void removeMessage(View view) {

        handler.sendEmptyMessage(2);
//        handlerThread.quit();
        isRemove = true;
    }
}
