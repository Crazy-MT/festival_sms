package com.example.beatplane;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/**/
public class GameGoods extends GameObject{
	private Bitmap bmp;					
	private int whichGoods;				
	//��Ʒ�ķ�����
	private int DIR_LEFT_UP = 1;
	private int DIR_RIGHT_UP = 2;
	private int DIR_LEFT_DOWN = 3;
	private int DIR_RIGHT_DOWN = 4;
	private int direction;			//��Ʒ�ķ���
	GameGoods(Resources resources,int whichGoods) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.whichGoods = whichGoods;
		this.speed = 12;
		Random ran = new Random();
		direction = ran.nextInt(2) + 3;
		initBitmap();
	}
	// ������Ļ���
	@Override
	public void setScreenWH(float screen_width, float screen_height) {
		super.setScreenWH(screen_width, screen_height);
	}
	// ��ʼ�����
	@Override
	public void initial(int arg0,float arg1,float arg2,int arg3){
		super.initial(arg0,arg1,arg2,arg3);
		object_x = screen_width/2 - object_width/2;
	}
	// ��ʼ��ͼƬ
	@Override
	public void initBitmap() {
		if(whichGoods == 1){
			//������Ʒ
			bmp = BitmapFactory.decodeResource(resources, R.drawable.missile_goods);
		}
		else if(whichGoods == 2){
			//�ӵ���Ʒ
			bmp = BitmapFactory.decodeResource(resources, R.drawable.bullet_goods);
		}
		 object_width = bmp.getWidth();			
		 object_height = bmp.getHeight();		
	}
	// ��ͼ����
	@Override
	public void drawSelf(Canvas canvas) {
		if(isAlive){
			canvas.save();
			canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
			canvas.drawBitmap(bmp, object_x, object_y,paint);
			canvas.restore();
			logic();
		}
	}
	// �ͷ���Դ
	@Override
	public void release(){
		if(!bmp.isRecycled()){
			bmp.recycle();
		}
	}
	// ������߼�����
	@Override
	public void logic() {
		Random ran = new Random();
		if(direction == DIR_LEFT_UP){
			object_x -= ran.nextInt(3) + speed;
			object_y -= ran.nextInt(3) + speed;	
			if(object_x <= 0 || object_y <= 0){
				if(object_x <= 0)
					object_x = 0;
				else
					object_y = 0;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
			}
		}
		else if(direction == DIR_RIGHT_UP){
			object_x += ran.nextInt(3) + speed;
			object_y -= ran.nextInt(3) + speed;	
			if(object_x >= screen_width - object_width || object_y <= 0){
				if(object_x >= screen_width - object_width)
					object_x = screen_width - object_width;
				else
					object_y = 0;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
			}
		}
		else if(direction == DIR_LEFT_DOWN){
			object_x -= ran.nextInt(3) + speed;
			object_y += ran.nextInt(3) + speed;	
			if(object_x <= 0 || object_y >= screen_height - object_height){
				if(object_x <= 0)
					object_x = 0;
				else
					object_y = screen_height - object_height;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
			}
		}
		else if(direction == DIR_RIGHT_DOWN){
			object_x += ran.nextInt(3) + speed;
			object_y += ran.nextInt(3) + speed;	
			if(object_x >= screen_width - object_width || object_y >= screen_height - object_height){
				if(object_x >= screen_width - object_width)
					object_x = screen_width - object_width;
				else
					object_y = screen_height - object_height;
				int dir = 0;
				do{
					dir = ran.nextInt(4)+1;
				}
				while(dir == direction);
				direction = dir;
			}
		}
	}
	// �����ײ
	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}
}
