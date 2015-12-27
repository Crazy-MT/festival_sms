package com.maomao.httptest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private WebView wv ;

    private Handler handler = new Handler();

    private ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        new HttpThread("http://g.hiphotos.baidu.com/image/pic/item/503d269759ee3d6df30c3eca41166d224e4adeed.jpg", imageView, handler).start();

    }


}
