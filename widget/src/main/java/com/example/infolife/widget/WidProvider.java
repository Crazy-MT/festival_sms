package com.example.infolife.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.infolife.widget.service.CanvasService;
import com.example.infolife.widget.service.TimerService;

public class WidProvider extends AppWidgetProvider {

	/**
	 * widget小组件从屏幕移除
	 */
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}

	/**
	 * 最后一个widget被从屏幕移除
	 */
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		context.stopService(new Intent(context, TimerService.class));
	}

	/**
	 * widget添加到屏幕上执行 此时开始启动service，执行更新时间操作
	 */
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		context.startService(new Intent(context, TimerService.class));

	}

	/**
	 * 
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		

	}

	/**
	 * 刷新widget
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		// 通过remoteView和AppWidgetManager
	}

}
