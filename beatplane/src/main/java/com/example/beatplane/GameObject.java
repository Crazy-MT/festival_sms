package com.example.beatplane;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
/*游戏对象的基类*/
public class GameObject {
	protected int currentFrame; 	// 当前动画帧
	protected int speed; 			// 当前速度
	protected int score;			// 分值
	protected int harm;				// 伤害
	protected float object_x; 		// 对象的横坐标
	protected float object_y;		// 对象的纵坐标
	protected float object_width; 	// 对象的宽度
	protected float object_height; 	// 对象的高度
	protected float screen_width; 	// 屏幕的宽度
	protected float screen_height;  // 屏幕的高度
	protected boolean isAlive;		// 判断是否存活
	protected boolean isExplosion;  // 判断是否爆炸
	protected Paint paint; 			// 画笔
	protected Resources resources;  // 资源
	
	GameObject(Resources resources) {
		this.resources = resources;
		paint = new Paint();
	}
	// 设置屏幕数据
	public void setScreenWH(float screen_width, float screen_height) {
		this.screen_width = screen_width;
		this.screen_height = screen_height;
	}
	// 初始化数据
	public void initial(int arg0,float arg1,float arg2,int arg3){
		isAlive = true;
		object_y = -object_height * (arg0*2 + 1);
	}
	// 初始化图片
	public void initBitmap() {
		
	}
	// 绘图函数
	public void drawSelf(Canvas canvas) {
		
	}
	// 释放资源
	public void release(){
		
	}
	// 对象的逻辑函数
	public void logic() {
		
	}
	
	// 被攻击的逻辑函数
	public void attacked(int harm) {
		
	}
	// 检测碰撞
	public boolean isCollide(GameObject obj) {
		if (object_x <= obj.getObject_x()
				&& object_x + object_width <= obj.getObject_x() + 20) {
			return false;
		}
		// 矩形1位于矩形2的右侧
		else if (obj.getObject_x() <= object_x
				&& obj.getObject_x() + obj.getObject_width() <= object_x  + 20) {
			return false;
		}
		// 矩形1位于矩形2的上方
		else if (object_y <= obj.getObject_y()
				&& object_y + object_height <= obj.getObject_y() + 50 ) {
			return false;
		}
		// 矩形1位于矩形2的下方
		else if (obj.getObject_y() <= object_y
				&& obj.getObject_y() + obj.getObject_height() <= object_y + 10) {
			return false;
		}
		return true;
	}
	// geter和setter函数
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setExplosion(boolean isExplosion) {
		this.isExplosion = isExplosion;
	}

	public boolean isExplosion() {
		return isExplosion;
	}

	public float getObject_x() {
		return object_x;
	}

	public float getObject_y() {
		return object_y;
	}

	public float getObject_width() {
		return object_width;
	}

	public float getObject_height() {
		return object_height;
	}
	
	public int getScore() {
		return score;
	}
}
