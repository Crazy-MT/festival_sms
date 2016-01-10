package com.example.beatplane;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
/*��Ϸ����Ļ���*/
public class GameObject {
	protected int currentFrame; 	// ��ǰ����֡
	protected int speed; 			// ��ǰ�ٶ�
	protected int score;			// ��ֵ
	protected int harm;				// �˺�
	protected float object_x; 		// ����ĺ�����
	protected float object_y;		// �����������
	protected float object_width; 	// ����Ŀ��
	protected float object_height; 	// ����ĸ߶�
	protected float screen_width; 	// ��Ļ�Ŀ��
	protected float screen_height;  // ��Ļ�ĸ߶�
	protected boolean isAlive;		// �ж��Ƿ���
	protected boolean isExplosion;  // �ж��Ƿ�ը
	protected Paint paint; 			// ����
	protected Resources resources;  // ��Դ
	
	GameObject(Resources resources) {
		this.resources = resources;
		paint = new Paint();
	}
	// ������Ļ����
	public void setScreenWH(float screen_width, float screen_height) {
		this.screen_width = screen_width;
		this.screen_height = screen_height;
	}
	// ��ʼ������
	public void initial(int arg0,float arg1,float arg2,int arg3){
		isAlive = true;
		object_y = -object_height * (arg0*2 + 1);
	}
	// ��ʼ��ͼƬ
	public void initBitmap() {
		
	}
	// ��ͼ����
	public void drawSelf(Canvas canvas) {
		
	}
	// �ͷ���Դ
	public void release(){
		
	}
	// ������߼�����
	public void logic() {
		
	}
	
	// ���������߼�����
	public void attacked(int harm) {
		
	}
	// �����ײ
	public boolean isCollide(GameObject obj) {
		if (object_x <= obj.getObject_x()
				&& object_x + object_width <= obj.getObject_x() + 20) {
			return false;
		}
		// ����1λ�ھ���2���Ҳ�
		else if (obj.getObject_x() <= object_x
				&& obj.getObject_x() + obj.getObject_width() <= object_x  + 20) {
			return false;
		}
		// ����1λ�ھ���2���Ϸ�
		else if (object_y <= obj.getObject_y()
				&& object_y + object_height <= obj.getObject_y() + 50 ) {
			return false;
		}
		// ����1λ�ھ���2���·�
		else if (obj.getObject_y() <= object_y
				&& obj.getObject_y() + obj.getObject_height() <= object_y + 10) {
			return false;
		}
		return true;
	}
	// geter��setter����
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
