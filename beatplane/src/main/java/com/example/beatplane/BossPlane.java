package com.example.beatplane;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*BOOS飞机的类*/
public class BossPlane extends GameObject{
	private Bitmap boosPlane;
	private Bitmap boosPlaneBomb;
	private int blood; 				// 对象的当前血量
	private int bloodVolume; 		 // 对象的血量
	private int direction;			//移动的方向
	private int DIR_LEFT = 1;
	private int DIR_RIGHT = 2;
	private int interval;			//发射子弹的间隔
	private float leftBorder;		
	private float rightBorder;
	private boolean isFire;			//是否允许射击
	private boolean isCrazy;		//是否为疯狂状态
	private List<GameObject> bullets;//子弹类
	private MyPlane myplane;		//玩家的飞机类
	BossPlane(Resources resources,MainActivity mainActivity) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();
		this.score = 10000;
		interval = 1;
		bullets = new ArrayList<GameObject>();
		for(int i = 0;i < 2;i++){
			BossBullet bullet = new BossBullet(resources);
			bullets.add(bullet);
		}
	}
	//初始化数据
	@Override
	public void setScreenWH(float screen_width,float screen_height){
		super.setScreenWH(screen_width, screen_height);
		for(GameObject obj:bullets){	
			obj.setScreenWH(screen_width, screen_height);
		}
		leftBorder = -object_width/2;
		rightBorder = screen_width - object_width/2;
	}
	//设置对象
	public void setPlane(MyPlane myplane){
		this.myplane = myplane;
	}
	//初始化数据
	@Override
	public void initial(int arg0,float arg1,float arg2,int arg3){
		super.initial(arg0,arg1,arg2,arg3);
		isCrazy = false;
		isFire = false;
		bloodVolume = 500;	
		blood = bloodVolume;
		object_x = screen_width/2 - object_width/2;
		direction = DIR_LEFT;
		currentFrame = 0;
		this.speed = 6;	
	}
	//初始化图片
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		boosPlane = BitmapFactory.decodeResource(resources, R.drawable.bossplane);
		boosPlaneBomb = BitmapFactory.decodeResource(resources, R.drawable.bossplanebomb);
		object_width = boosPlane.getWidth();		//获得每一帧位图的宽
		object_height = boosPlane.getHeight()/3;		//获得每一帧位图的高	
	}
	//初始化子弹对象
	public void initButtle(){
		if(isFire){
			if(interval == 1){
				for(GameObject obj:bullets){	
					if(!obj.isAlive()){
						obj.initial(0,object_x + object_width/2,object_height,0);
						break;
					}
				}
			}
			interval++;
			if(interval >= 8){
				interval = 1;
			}
		}
	}
	//绘图函数
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			if(!isExplosion){
				int y = (int) (currentFrame * object_height); // 获得当前帧相对于位图的Y坐标
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(boosPlane, object_x, object_y - y,paint);
				canvas.restore();
				logic();
				currentFrame++;
				if(currentFrame >= 3){
					currentFrame = 0;
				}
				shoot(canvas);		//射击
			}
			else{
				int y = (int) (currentFrame * object_height); // 获得当前帧相对于位图的Y坐标
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(boosPlaneBomb, object_x, object_y - y,paint);
				canvas.restore();
				currentFrame++;
				if(currentFrame >= 5){
					currentFrame = 0;
					isExplosion = false;
					isAlive = false;
					myplane = null;
				}
			}	
		}	
	}
	//发射子弹
	public boolean shoot(Canvas canvas){
		if(isFire){
			//遍历子弹的对象
			for(GameObject obj:bullets){	
				if(obj.isAlive()){
					obj.drawSelf(canvas);//绘制子弹
					if(obj.isCollide(myplane)){
						myplane.setAlive(false);
						return true;
					}
				}
			}
		}
		return false;
	}
	//释放资源
	@Override
	public void release() {
		// TODO Auto-generated method stub
		for(GameObject obj:bullets){	
			obj.release();
		}
		if(!boosPlane.isRecycled()){
			boosPlane.recycle();
		}
		if(!boosPlaneBomb.isRecycled()){
			boosPlaneBomb.recycle();
		}
	}
	// 检测碰撞
	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}
	//对象的逻辑函数
	@Override
	public void logic(){
		if (object_y < 0) {
			object_y += speed;
		}
		else{
			if(!isFire){
				isFire = true;
			}
			if(blood < 150){
				if(!isCrazy){
					isCrazy = true;
					speed = 20;
				}
			}
			if(object_x > leftBorder && direction == DIR_LEFT){
				object_x -= speed;
				if(object_x <= leftBorder){
					direction = DIR_RIGHT;
				}
			}
			if(object_x < rightBorder && direction == DIR_RIGHT){
				object_x += speed;
				if(object_x >= rightBorder){
					direction = DIR_LEFT;
				}
			}
		}
	}
	//被攻击的逻辑函数
	@Override
	public void attacked(int harm){
		blood -= harm;
		if (blood <= 0) {
			isExplosion = true;
			currentFrame = 0;
		}
	}
}

