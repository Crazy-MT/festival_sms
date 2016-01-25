package com.example.infolife.widget.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.example.infolife.widget.R;
import com.example.infolife.widget.WidProvider;

/**
 * Created by infolife on 2016/1/21.
 */
public class CanvasService extends Service{

    private RemoteViews rv;
    private ComponentName cn;
    private AppWidgetManager manager;
    private Context context ;
    private Bitmap bg ;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        draw();
        rv.setImageViewBitmap(R.id.img_canvas ,bg);
        manager.updateAppWidget(cn, rv);
    }

    private void draw() {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#CD5C5C"));
        bg = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bg);
        canvas.drawRect(50, 50, 200, 200, paint);



    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void init() {
        context = this ;
        rv = new RemoteViews(this.getPackageName(), R.layout.widget_view_canvas);
        cn = new ComponentName(getApplicationContext(), WidProvider.class);
        manager = AppWidgetManager.getInstance(getApplicationContext());
        //DataStorage.setInitMinute(context, getMinute(new Date()));
    }
}
