package com.example.beatplane;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class BossPlane extends GameObject{
	private Bitmap boosPlane;
	private Bitmap boosPlaneBomb;
	private int blood;
	private int bloodVolume;
	private int direction;
	private int DIR_LEFT = 1;
	private int DIR_RIGHT = 2;
	private int interval;
	private float leftBorder;		
	private float rightBorder;
	private boolean isFire;
	private boolean isCrazy;
	private List<GameObject> bullets;
	private MyPlane myplane;
	BossPlane(Resources resources,MainActivity mainActivity) {
		super(resources);
		initBitmap();
		this.score = 10000;
		interval = 1;
		bullets = new ArrayList<GameObject>();
		for(int i = 0;i < 2;i++){
			BossBullet bullet = new BossBullet(resources);
			bullets.add(bullet);
		}
	}

	@Override
	public void setScreenWH(float screen_width,float screen_height){
		super.setScreenWH(screen_width, screen_height);
		for(GameObject obj:bullets){	
			obj.setScreenWH(screen_width, screen_height);
		}
		leftBorder = -object_width/2;
		rightBorder = screen_width - object_width/2;
	}

	public void setPlane(MyPlane myplane){
		this.myplane = myplane;
	}

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

	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		boosPlane = BitmapFactory.decodeResource(resources, R.drawable.bossplane);
		boosPlaneBomb = BitmapFactory.decodeResource(resources, R.drawable.bossplanebomb);
		object_width = boosPlane.getWidth();
		object_height = boosPlane.getHeight()/3;
	}

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

	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			if(!isExplosion){
				int y = (int) (currentFrame * object_height);
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(boosPlane, object_x, object_y - y,paint);
				canvas.restore();
				logic();
				currentFrame++;
				if(currentFrame >= 3){
					currentFrame = 0;
				}
				shoot(canvas);
			}
			else{
				int y = (int) (currentFrame * object_height);
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

	public boolean shoot(Canvas canvas){
		if(isFire){

			for(GameObject obj:bullets){	
				if(obj.isAlive()){
					obj.drawSelf(canvas);
					if(obj.isCollide(myplane)){
						myplane.setAlive(false);
						return true;
					}
				}
			}
		}
		return false;
	}

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

	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}

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

	@Override
	public void attacked(int harm){
		blood -= harm;
		if (blood <= 0) {
			isExplosion = true;
			currentFrame = 0;
		}
	}
}

