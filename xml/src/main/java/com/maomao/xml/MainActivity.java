package com.maomao.xml;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private TextView xml_text;
    private ImageView xml_img;
    private TextView xml_text_summary;
    List<News> newses = new ArrayList<News>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    xml_text.setText("nothing");
                    break;
                case 1:
                    News news = (News) msg.obj;
                    xml_text.setText(news.getTitle());
                    xml_text_summary.setText(news.getDescription());
                    new Thread(saveImageRunnable).start();
                    break;
                case 2:
                    xml_img.setImageBitmap((Bitmap) msg.obj);
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
        xml_img = (ImageView) findViewById(R.id.img_xml);
        xml_text_summary = (TextView) findViewById(R.id.text_xml_summary);


        new Thread() {
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
                        NewsParser parser = new PullBookParser();

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
                        if (newses != null) {
                            Message message = new Message();
                            message.what = 1;
                            message.obj = newses.get(1);
                            mHandler.sendMessage(message);
                        }

                    }
                });
            }
        }.start();

    }

    private Runnable saveImageRunnable = new Runnable() {
        @Override
        public void run() {
            OkHttpClient mOkHttpClient = new OkHttpClient();
            Request requestImage = new Request.Builder()
                    .url(newses.get(1).getImage())
                    .build();
            Call callImage = mOkHttpClient.newCall(requestImage);
            callImage.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Toast.makeText(getApplicationContext() , "下载图片失败" , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    InputStream is = response.body().byteStream();
                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = false ;
                    Bitmap bitmap = BitmapFactory.decodeStream(is , null , ops);
                    Message message = new Message();
                    message.what = 2;
                    message.obj = bitmap;
                    mHandler.sendMessage(message);
                }
            });
        }
    };
}
