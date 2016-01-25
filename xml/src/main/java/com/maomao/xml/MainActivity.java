package com.maomao.xml;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView xml_text ;
    private Thread mThread ;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    xml_text.setText("nothing");
                    break;
                case 1:
                    xml_text.setText(((News)msg.obj).getTitle());
                    break;
                default:
                    break;
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        xml_text = (TextView) findViewById(R.id.text_xml);


        new Thread(){
            @Override
            public void run() {

                OkHttpClient mOkHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://rss.gem.is/partner/amber.xml")
                        .build();

                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        mHandler.sendEmptyMessage(0);
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {

                        InputStream is = response.body().byteStream();
                        NewsParser parser = new PullBookParser() ;
                        List<News> newses = new ArrayList<News>();
                        News news = new News();
                        try {
                            newses = parser.parser(is);
                            //Log.e("ma" , newses.size()+"");
                            for (News news1 : newses) {
                                Log.e("tag", news1.toString());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (newses != null){
                            Message message = new Message() ;
                            message.what = 1 ;
                            message.obj = newses.get(1);
                            mHandler.sendMessage(message);
                        }

                    }
                });
            }
        }.start();

    }
}
