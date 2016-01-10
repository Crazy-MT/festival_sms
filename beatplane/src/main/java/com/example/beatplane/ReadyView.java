package com.example.beatplane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/*游戏开始前的界面*/
public class ReadyView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
	private Bitmap background;
	private Bitmap text;
	private Bitmap fly;
	private Bitmap button;
	private Bitmap button2;
	private Canvas canvas;			// 画布资源
	private Paint paint; 			// 画笔
	private SurfaceHolder sfh;
	private Thread thread;			// 绘图线程
	private int currentFrame;
	private float fly_x;
	private float fly_y;
	private float fly_height;
	private float text_x;
	private float text_y;
	private float button_x;
	private float button_y;
	private float button_y2;
	private float scalex;		
	private float scaley;		
	private float screen_width;
	private float screen_height;
	private boolean threadFlag;
	private boolean isBtChange;
	private boolean isBtChange2;
	private String startGame = "开始游戏";
	private String exitGame = "退出游戏";
	private MainActivity mainActivity;
	private GameSoundPool sounds;
	public ReadyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mainActivity = (MainActivity) context;
		sounds = new GameSoundPool(mainActivity);
		sounds.setOpen(true);
		sounds.initSysSound();
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		thread = new Thread(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		screen_width = this.getWidth();
		screen_height = this.getHeight();
		initBitmap(); // 初始化图片资源
		threadFlag = true;
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		release();
		threadFlag = false;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1){
			float x = event.getX();
			float y = event.getY();
			if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y && y < button_y + button.getHeight())
			{
				sounds.playSound(1, 0);
				isBtChange = true;
				drawSelf();
				mainActivity.toMainView();
			}
			else if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y2 && y < button_y2 + button.getHeight())
			{
				sounds.playSound(1, 0);
				isBtChange2 = true;
				drawSelf();
				threadFlag = false;
				System.exit(0);
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE){
			float x = event.getX();
			float y = event.getY();
			if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y && y < button_y + button.getHeight())
			{
				isBtChange = true;
			}
			else{
				isBtChange = false;
			}
			if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y2 && y < button_y2 + button.getHeight())
			{
				isBtChange2 = true;
			}
			else{
				isBtChange2 = false;
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP){
			isBtChange = false;
			isBtChange2 = false;
			return true;
		}
		return false;
	}
	// 初始化图片
	public void initBitmap() {
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
		text = BitmapFactory.decodeResource(getResources(), R.drawable.text);
		fly = BitmapFactory.decodeResource(getResources(), R.drawable.fly);
		button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
		button2 = BitmapFactory.decodeResource(getResources(), R.drawable.button2);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
		text_x = screen_width/2 - text.getWidth()/2;
		text_y = screen_height/2 - text.getHeight();
		fly_x = screen_width/2 - fly.getWidth()/2;
		fly_height = fly.getHeight()/3;
		fly_y = text_y - fly_height - 20;
		button_x =  screen_width/2 - button.getWidth()/2;
		button_y = screen_height/2 + button.getHeight();
		button_y2 = button_y + button.getHeight() + 40;
	}
	// 绘图函数
	public void drawSelf() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK); // 绘制背景色
			canvas.save();
			// 计算背景图片与屏幕的比例
			canvas.scale(scalex, scaley, 0, 0);
			canvas.drawBitmap(background, 0, 0, paint);   // 绘制背景图
			canvas.restore();
			canvas.drawBitmap(text, text_x, text_y, paint);  
			if(isBtChange){
				canvas.drawBitmap(button2, button_x, button_y, paint);  
			}
			else{
				canvas.drawBitmap(button, button_x, button_y, paint);  
			}
			if(isBtChange2){
				canvas.drawBitmap(button2, button_x, button_y2, paint);
			}
			else{
				canvas.drawBitmap(button, button_x, button_y2, paint);
			}
			paint.setTextSize(40);
			Rect rect = new Rect();      
			//返回包围整个字符串的最小的一个Rect区域     
			paint.getTextBounds(startGame, 0, startGame.length(), rect);    		   
			float strwid = rect.width();    
			float strhei = rect.height();   
			canvas.drawText(startGame, screen_width/2 - strwid/2, button_y + button.getHeight()/2 + strhei/2, paint);
			canvas.drawText(exitGame, screen_width/2 - strwid/2, button_y2 + button.getHeight()/2 + strhei/2, paint);
			canvas.save();
			canvas.clipRect(fly_x, fly_y,fly_x + fly.getWidth(), fly_y + fly_height);
			canvas.drawBitmap(fly, fly_x, fly_y - currentFrame*fly_height, paint);
			currentFrame++;
			if(currentFrame>=3){
				currentFrame = 0;
			}
			canvas.restore();
		} 
		catch (Exception err) {
			err.printStackTrace();
		} 
		finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	public void release(){
		if(!background.isRecycled()){
			background.recycle();
		}
		if(!button.isRecycled()){
			button.recycle();
		}
		if(!button2.isRecycled()){
			button2.recycle();
		}
		if(!text.isRecycled()){
			text.recycle();
		}
		if(!fly.isRecycled()){
			fly.recycle();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (threadFlag) {	
			long startTime = System.currentTimeMillis();
			drawSelf();
			long endTime = System.currentTimeMillis();	
			try {
				if (endTime - startTime < 500)
					Thread.sleep(500 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
	}
}
