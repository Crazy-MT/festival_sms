package com.maomao.httptest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

public class HttpThread extends Thread {
    private String url;
    private Handler handler;

    private WebView webView;
    private ImageView imageView;

    public HttpThread(String url, WebView webView, Handler handler) {
        super();
        this.url = url;
        this.webView = webView;
        this.handler = handler;
    }

    public HttpThread(String url,ImageView imageView, Handler handler ) {
        super();
        this.url = url;
        this.handler = handler;
        this.imageView = imageView;
    }

    @Override
    public void run() {
        try {
            URL httpUrl = new URL(url);
            try {
                HttpURLConnection conn = (HttpURLConnection) httpUrl
                        .openConnection();
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                InputStream in = conn.getInputStream();
                FileOutputStream out = null;
                File downloadFile = null ;
                String fileName = String.valueOf(System.currentTimeMillis());
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    File parent = Environment.getExternalStorageDirectory();
                    downloadFile = new File(parent, fileName);

                    out = new FileOutputStream(downloadFile);
                }
                byte[] b = new byte[2*1024];
                int len  ;
                if (out != null) {
                    while((len=in.read(b))!=-1){
                        out.write(b,0,len);
                    }
                }

                final Bitmap bitmap = BitmapFactory.decodeFile(downloadFile.getAbsolutePath());
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        imageView.setImageBitmap(bitmap);
                    }
                });

				/*
				 * final StringBuffer sb = new StringBuffer(); BufferedReader
				 * reader = new BufferedReader(new
				 * InputStreamReader(conn.getInputStream())); String str ;
				 * while((str=reader.readLine())!=null){ sb.append(str); }
				 * 
				 * handler.post(new Runnable() {
				 * 
				 * @Override public void run() { webView.loadData(sb.toString(),
				 * "text/html;charset=utf-8", null); } });
				 */
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
