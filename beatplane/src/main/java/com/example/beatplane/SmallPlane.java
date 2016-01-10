package com.example.beatplane;

import java.util.Random;

import com.example.beatplane.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*小型飞机的类*/
public class SmallPlane extends GameObject{
	private Bitmap smallPlane;
	private int blood; 		// 对象的当前血量
	private int bloodVolume;  // 对象的血量
	SmallPlane(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();
		this.score = 100;
	}
	//初始化数据
	@Override
	public void setScreenWH(float screen_width,float screen_height){
		super.setScreenWH(screen_width, screen_height);
	}
	//初始化数据
	@Override
	public void initial(int arg0,float arg1,float arg2,int arg3){
		super.initial(arg0,arg1,arg2,arg3);
		bloodVolume = 1;
		blood = bloodVolume;
		Random ran = new Random();
		object_x = ran.nextInt((int)(screen_width - object_width));
		this.speed = ran.nextInt(8) + 8 * arg3;	
	}
	//初始化图片
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		 smallPlane = BitmapFactory.decodeResource(resources, R.drawable.small);
		 object_width = smallPlane.getWidth();		//获得每一帧位图的宽
		 object_height = smallPlane.getHeight()/3;		//获得每一帧位图的高
	}
	//绘图函数
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			if(!isExplosion){
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(smallPlane, object_x, object_y,paint);
				canvas.restore();
				logic();
			}
			else{
				int y = (int) (currentFrame * object_height); // 获得当前帧相对于位图的Y坐标
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(smallPlane, object_x, object_y - y,paint);
				canvas.restore();
				currentFrame++;
				if(currentFrame >= 3){
					currentFrame = 0;
					isExplosion = false;
					isAlive = false;
				}
			}
		}
	}
	//释放资源
	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
	// 检测碰撞
	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}
	//对象的逻辑函数
	@Override
	public void logic(){
		if (object_y < screen_height) {
			object_y += speed;
		} else {
			isAlive = false;
		}
	}
	//被攻击的逻辑函数
	@Override
	public void attacked(int harm){
		blood -= harm;
		if (blood <= 0) {
			isExplosion = true;
		}
	}
}
