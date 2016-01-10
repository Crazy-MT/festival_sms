package com.example.infolife.widget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.BatteryManager;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.RemoteViews;

public class TimerService extends Service  {

	private Timer timer;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Bitmap bmp;// 显示的图片
	private Boolean flag = true;// 线程循环标志
	private List bmplist = new ArrayList(); // 定义显示图片list
	private int[] bitmapId = new int[] { R.drawable.n0, R.drawable.n1,
			R.drawable.n2, R.drawable.n3, R.drawable.n4, R.drawable.n5,
			R.drawable.n6, R.drawable.n7, R.drawable.n8, R.drawable.n9 };// 图片的资源id数组
	private int temp = 0;

	private RemoteViews rv;
	private ComponentName cn;
	private AppWidgetManager manager;
	private Context context ;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		this.flag = true; // 线程循环标志置为true
		new Thread() { // 动画线程

			public void run() {

				while (flag) {// 线程循环体
					try {

						//bmp = (Bitmap) bmplist.get(temp);
						temp++;
						if (temp > 7) {
							temp = 0;
						}
						if (temp == 0) {
							rv.setImageViewResource(R.id.iv_pic0,
									R.drawable.m0); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic1, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic2, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic3, null); // 更新显示图片
						} else if (temp == 1) {
							rv.setImageViewResource(R.id.iv_pic1,
									R.drawable.m1); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic0, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic2, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic3, null); // 更新显示图片
						} else if (temp == 2) {
							rv.setImageViewResource(R.id.iv_pic2,
									R.drawable.m2); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic1, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic0, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic3, null); // 更新显示图片
						} else if (temp == 3) {
							rv.setImageViewResource(R.id.iv_pic3,
									R.drawable.m3); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic1, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic2, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic0, null); // 更新显示图片
						} else if (temp == 4) {
							rv.setImageViewResource(R.id.iv_pic2,
									R.drawable.m2); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic1, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic0, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic3, null); // 更新显示图片
						} else if (temp == 5) {
							rv.setImageViewResource(R.id.iv_pic1,
									R.drawable.m1); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic0, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic2, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic3, null); // 更新显示图片
						} else if (temp == 6) {
							rv.setImageViewResource(R.id.iv_pic0,
									R.drawable.m0); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic1, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic2, null); // 更新显示图片
							rv.setImageViewBitmap(R.id.iv_pic3, null); // 更新显示图片
						}

						// rv.setImageViewBitmap(R.id.iv_pic0, bmp); // 更新显示图片
						manager.updateAppWidget(cn, rv); // 更新appwidget显示
						Thread.sleep(300);// 线程睡眠时间1000ms
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}.start(); // 线程开始
	}

	@Override
	public void onCreate() {
		super.onCreate();
		init();
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				updateViews();
			}
		}, 0, 1000);
	}

	private void init() {
		context = this ;
		rv = new RemoteViews(this.getPackageName(), R.layout.widget);
		cn = new ComponentName(getApplicationContext(), WidProvider.class);
		manager = AppWidgetManager.getInstance(getApplicationContext());
		//DataStorage.setInitMinute(context, getMinute(new Date()));
	}

	private void updateViews() {
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = getApplicationContext().registerReceiver(null,
				ifilter);
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

		String time = sdf.format(new Date());
		rv.setTextViewText(R.id.tv_time, time);
		//setFlagStatue();
		rv.setTextViewText(R.id.tv_power, "电量：" + level);
		manager.updateAppWidget(cn, rv);
	}



	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer = null;
		this.bmplist = null;// 回收内存
		this.bitmapId = null;
	}



}
