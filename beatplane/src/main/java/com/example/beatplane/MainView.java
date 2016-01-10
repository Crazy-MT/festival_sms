package com.example.beatplane;

import java.util.ArrayList;
import java.util.List;

import com.example.beatplane.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/*游戏界面的类*/
public class MainView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
	private Bitmap background; 		// 背景图片
	private Bitmap background2; 	// 背景图片
	private Bitmap playButton; 		// 按钮图片
	private Bitmap missile_bt;		//导弹按钮图标
	private Canvas canvas;			// 画布资源
	private Paint paint; 			// 画笔
	private SurfaceHolder sfh;
	private Thread thread;			// 绘图线程
	private MyPlane myPlane;
	private int scoreSum;			//总积分
	private int middleSum;			//中型敌机积分
	private int bigSum;				//大型敌机积分
	private int bossSum;			//BOSS敌机的积分
	private int missileSum;			//导弹的积分
	private int smallCount;
	private int middleCount;
	private int bigCount;
	private int missileCount;
	private int speedTime;
	private float bg_y;
	private float bg_y2;
	private float play_bt_w;
	private float play_bt_h;
	private float scalex;		
	private float scaley;		 
	private float missile_bt_y;
	private float screen_width;		 // 屏幕的宽度
	private float screen_height;	 // 屏幕的高度
	private boolean threadFlag;
	private boolean isPlay;
	private boolean isTouch;
	private boolean isChangeBullet;  //是否更换子弹类型
	private GameGoods missileGoods;	 //导弹物品
	private GameGoods bulletGoods;	 //子弹物品
	private BossPlane bossPlane;	 //BOSS飞机对象
	private List<GameObject> planes;
	private GameSoundPool sounds;
	private MainActivity mainActivity;
	private Handler myHandler;

	public MainView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mainActivity = (MainActivity)context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		sounds = new GameSoundPool(mainActivity);
		sounds.setOpen(true);
		sounds.initSound();
		planes = new ArrayList<GameObject>();
		myPlane = new MyPlane(this,getResources());
		bossPlane = new BossPlane(getResources(),mainActivity);
		planes.add(bossPlane);
		for(int i = 0;i < 2;i++){
			BigPlane bigPlane = new BigPlane(getResources());
			planes.add(bigPlane);
		}	
		for(int i = 0;i < 4;i++){
			MiddlePlane middlePlane = new MiddlePlane(getResources());
			planes.add(middlePlane);
		}
		for(int i = 0;i < 8;i++){
			SmallPlane smallPlane = new SmallPlane(getResources());
			planes.add(smallPlane);
		}
		missileGoods = new GameGoods(getResources(),1);
		bulletGoods = new GameGoods(getResources(),2);
		smallCount = 0;
		middleCount = 0;
		bigCount = 0;
		missileCount = 1;
		speedTime = 1;
		thread = new Thread(this);
		isPlay = true;
		myHandler = new Handler(){ 
			@Override
	        public void handleMessage(Message msg){
	            if(msg.what == 1){
	            	mainActivity.toEndView(scoreSum);
	            }
	        }
	    };
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		screen_width = this.getWidth();
		screen_height = this.getHeight();
		initBitmap(); // 初始化图片资源
		for(GameObject obj:planes){
			obj.setScreenWH(screen_width, screen_height);
		}
		myPlane.setScreenWH(screen_width, screen_height);
		missileGoods.setScreenWH(screen_width, screen_height);
		bulletGoods.setScreenWH(screen_width, screen_height);
		myPlane.setAlive(true);
		threadFlag = true;
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		threadFlag = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_UP){
			isTouch = false;
		}
		else if(event.getAction() == MotionEvent.ACTION_DOWN){
			float x = event.getX();
			float y = event.getY();
			if(x > 10 && x < 10 + play_bt_w && y > 10 && y < 10 + play_bt_h){
				if(isPlay){
					isPlay = false;
				}		
				else{
					isPlay = true;	
					synchronized(thread){
						thread.notify();
					}
				}
				return true;
			}
			//判断玩家飞机是否被按下
			else if(x > myPlane.getObject_x() && x < myPlane.getObject_x() + myPlane.getObject_width() 
					&& y > myPlane.getObject_y() && y < myPlane.getObject_y() + myPlane.getObject_height()){
				if(isPlay){
					isTouch = true;
				}
				return true;
			}
			//判断导弹按钮是否被按下
			else if(x > 10 && x < 10 + missile_bt.getWidth() 
					&& y > missile_bt_y && y < missile_bt_y + missile_bt.getHeight()){
				if(missileCount > 0){
					missileCount--;
					sounds.playSound(5, 0);
					for(GameObject pobj:planes){
						if(pobj.isAlive() && !pobj.isExplosion()){
							pobj.attacked(100);
							if(pobj.isExplosion()){
								//计算积分
								middleSum += pobj.getScore();
								bigSum += pobj.getScore();
								scoreSum += pobj.getScore();
								missileSum += pobj.getScore();
								bossSum += pobj.getScore();
							}	
						}
					}	
				}
				return true;
			}
		}
		//玩家飞机是否移动
		else if(event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1){
			if(isTouch){
				float x = event.getX();
				float y = event.getY();
				if(x > myPlane.getMiddle_x() + 20){
					if(myPlane.getMiddle_x() + myPlane.getSpeed() <= screen_width){
						myPlane.setMiddle_x(myPlane.getMiddle_x() + myPlane.getSpeed());
					}					
				}
				else if(x < myPlane.getMiddle_x() - 20){
					if(myPlane.getMiddle_x() - myPlane.getSpeed() >= 0){
						myPlane.setMiddle_x(myPlane.getMiddle_x() - myPlane.getSpeed());
					}				
				}
				if(y > myPlane.getMiddle_y() + 20){
					if(myPlane.getMiddle_y() + myPlane.getSpeed() <= screen_height){
						myPlane.setMiddle_y(myPlane.getMiddle_y() + myPlane.getSpeed());
					}		
				}
				else if(y < myPlane.getMiddle_y() - 20){
					if(myPlane.getMiddle_y() - myPlane.getSpeed() >= 0){
						myPlane.setMiddle_y(myPlane.getMiddle_y() - myPlane.getSpeed());
					}
				}
			}	
		}
		return false;
	}

	// 初始化图片
	public void initBitmap() {
		playButton = BitmapFactory.decodeResource(getResources(),R.drawable.play);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
		background2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_02);
		missile_bt = BitmapFactory.decodeResource(getResources(), R.drawable.missile_bt);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
		play_bt_w = playButton.getWidth();
		play_bt_h = playButton.getHeight()/2;
		bg_y = 0;
		bg_y2 = bg_y - screen_height;
		missile_bt_y = screen_height - 10 - missile_bt.getHeight();
	}
	//初始化对象
	public void initObject(){
		//初始化敌机对象
		for(GameObject obj:planes){		
			//初始化小型敌机
			if(obj instanceof SmallPlane){
				if(!obj.isAlive()){
					obj.initial(smallCount,0,0,speedTime);
					smallCount++;
					if(smallCount >= 8){
						smallCount = 0;
					}
					break;
				}
			}
			//初始化中型敌机
			else if(obj instanceof MiddlePlane){
				if(middleSum >= 8000){
					if(!obj.isAlive()){
						obj.initial(middleCount,0,0,speedTime);
						middleCount++;
						if(middleCount >= 4){
							middleCount = 0;
							middleSum = 0;
						}
						break;
					}
				}	
			}
			//初始化大型敌机
			else if(obj instanceof BigPlane){
				if(bigSum >= 20000){
					if(!obj.isAlive()){
						obj.initial(bigCount,0,0,speedTime);
						bigCount++;
						if(bigCount >= 2){
							bigCount = 0;
							bigSum = 0;
						}
						break;
					}
				}	
			}
			//初始化BOSS敌机
			else{
				if(bossSum >= 80000){
					if(!obj.isAlive()){
						obj.initial(0,0,0,0);
						bossPlane.setPlane(myPlane);
						bossSum = 0;
						break;
					}
				}
			}
		}
		//初始化子弹
		if(bossPlane.isAlive())
			bossPlane.initButtle();
		myPlane.initButtle();						
		//初始化导弹物品
		if(missileSum >= 30000){
			if(!missileGoods.isAlive()){
				missileGoods.initial(0,0,0,0);
				missileSum = 0;
			}
		}
		//初始化弹药物品
		if(scoreSum >= 50000){
			if(!isChangeBullet){
				if(!bulletGoods.isAlive()){
					bulletGoods.initial(0,0,0,0);
				}
			}
		}
		//提升等级
		if(scoreSum >= speedTime*100000 && speedTime < 6){
			speedTime++;	
		}
	}
	// 绘图函数
	public void drawSelf() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK); // 绘制背景色
			canvas.save();
			// 计算背景图片与屏幕的比例
			canvas.scale(scalex, scaley, 0, 0);
			canvas.drawBitmap(background, 0, bg_y, paint);   // 绘制背景图
			canvas.drawBitmap(background2, 0, bg_y2, paint); // 绘制背景图
			canvas.restore();
			//背景移动的逻辑
			if(bg_y > bg_y2){
				bg_y += 10;											
				bg_y2 = bg_y - background.getHeight();
			}
			else{
				bg_y2 += 10;											
				bg_y = bg_y2 - background.getHeight();
			}
			if(bg_y >= background.getHeight()){
				bg_y = bg_y2 - background.getHeight();
			}
			else if(bg_y2 >= background.getHeight()){
				bg_y2 = bg_y - background.getHeight();
			}
			//绘制物品
			if(missileGoods.isAlive()){
				if(missileGoods.isCollide(myPlane)){
					missileGoods.setAlive(false);
					missileCount++;
					sounds.playSound(6, 0);
				}
				else
					missileGoods.drawSelf(canvas);
			}
			if(bulletGoods.isAlive()){
				if(bulletGoods.isCollide(myPlane)){
					bulletGoods.setAlive(false);
					if(!isChangeBullet){
						myPlane.changeButtle();
						isChangeBullet = true;
						sounds.playSound(6, 0);
					}
				}
				else
					bulletGoods.drawSelf(canvas);
			}
			//绘制敌机
			for(GameObject obj:planes){		
				if(obj.isAlive()){
					obj.drawSelf(canvas);					
					if(!obj.isExplosion() && myPlane.isAlive()){		
						if(obj.isCollide(myPlane)){			//检测敌机是否与玩家的飞机碰撞					
							myPlane.setAlive(false);	
						}
					}
				}	
			}
			if(!myPlane.isAlive()){
				sounds.playSound(4, 0);			//飞机炸毁的音效
				threadFlag = false;
			}
			//绘制玩家的飞机
			myPlane.drawSelf(canvas);	
			myPlane.shoot(canvas, planes);//发射子弹			
			sounds.playSound(1, 0);	  //子弹音效		
			//绘制按钮
			canvas.save();
			canvas.clipRect(10, 10, 10 + play_bt_w,10 + play_bt_h);
			if(isPlay){
				canvas.drawBitmap(playButton, 10, 10, paint);			 
			}
			else{
				canvas.drawBitmap(playButton, 10, 10 - play_bt_h, paint);
			}
			canvas.restore();
			//绘制导弹按钮
			if(missileCount > 0){
				paint.setTextSize(40);
				paint.setColor(Color.BLACK);
				canvas.drawBitmap(missile_bt, 10,missile_bt_y, paint);
				canvas.drawText("X "+String.valueOf(missileCount), 20 + missile_bt.getWidth(), screen_height - 25, paint);//绘制文字
			}
			//绘制积分文字
			paint.setTextSize(30);
			paint.setColor(Color.rgb(235, 161, 1));
			canvas.drawText("积分:"+String.valueOf(scoreSum), 30 + play_bt_w, 40, paint);//绘制文字
			canvas.drawText("等级 X "+String.valueOf(speedTime), screen_width - 150, 40, paint);//绘制文字
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	public void release(){
		for(GameObject obj:planes){		
			obj.release();
		}
		if(!playButton.isRecycled()){
			playButton.recycle();
		}
		if(!background.isRecycled()){
			background.recycle();
		}
		if(!background2.isRecycled()){
			background2.recycle();
		}
		if(!missile_bt.isRecycled()){
			missile_bt.recycle();
		}
		missileGoods.release();
		bulletGoods.release();	
		myPlane.release();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (threadFlag) {	
			long startTime = System.currentTimeMillis();
			initObject();
			drawSelf();
			long endTime = System.currentTimeMillis();	
			if(!isPlay){
				synchronized (thread) {  
				    try {  
				    	thread.wait();  
				    } catch (InterruptedException e) {  
				        e.printStackTrace();  
				    }  
				}  		
			}	
			try {
				if (endTime - startTime < 100)
					Thread.sleep(100 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myHandler.sendEmptyMessage(1);
	}
	
	public void addMiddleSum(int score) {
		middleSum += score;
	}

	public void addBigSum(int score) {
		bigSum += score;
	}
	
	public void addScoreSum(int score) {
		scoreSum += score;
	}
	
	public void addMissileSum(int score) {
		missileSum += score;
	}
	
	public void addBossSum(int score) {
		bossSum += score;
	}
	
	public GameSoundPool getSounds() {
		return sounds;
	}
}
