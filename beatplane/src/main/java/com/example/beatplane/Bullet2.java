package com.example.beatplane;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*˫�ӵ�����*/
public class Bullet2 extends GameObject{
	private Bitmap bullet; 		 // �ӵ���ͼƬ
	private float object_x2;	
	private float object_y2;
	private boolean isAlive2;	
	private boolean attack;		//����ӵ��Ƿ����
	private boolean attack2;
	Bullet2(Resources resources) {
		super(resources);
		initBitmap();
	}
	//��ʼ������
	@Override
	public void initial(int arg0,float arg1,float arg2,int arg3) {
		isAlive = true;
		isAlive2 = true;
		harm = 1;
		Random ran = new Random();
		speed = (ran.nextInt(20) + 100);	
		object_x = arg1 - 2*object_width;
		object_y = arg2 - object_height;
		object_x2 = arg1 + object_width;
		object_y2 = object_y;
	}
	//��ʼ��ͼƬ
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet2);
		object_width = bullet.getWidth();
		object_height = bullet.getHeight();
	}
	// ��ͼ����
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if (isAlive) {
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width,object_y + object_height);
			canvas.drawBitmap(bullet, object_x, object_y, paint);
			canvas.restore();
		}
		if (isAlive2) {
			canvas.save();
			canvas.clipRect(object_x2, object_y2, object_x2 + object_width,object_y2 + object_height);
			canvas.drawBitmap(bullet, object_x2, object_y2, paint);
			canvas.restore();
		}
		logic();
	}
	@Override
	public void release(){
		if(!bullet.isRecycled()){
			bullet.recycle();
		}
	}
	// ������߼�����
	@Override
	public void logic() {
		if (object_y >= 0) {
			object_y -= speed;
		}
		else {
			isAlive = false;
		}
		if (object_y2 >= 0) {
			object_y2 -= speed;
		} 
		else{
			isAlive2 = false;
		}
	}
	// �����ײ
	@Override
	public boolean isCollide(GameObject obj) {
		attack = false;
		attack2 = false;
		//�ж���ߵ��ӵ��Ƿ���
		if(isAlive){
			if(obj instanceof SmallPlane){
				if (object_x <= obj.getObject_x() && object_x + object_width <= obj.getObject_x()) {
					
				}
				// ����1λ�ھ���2���Ҳ�
				else if (obj.getObject_x() <= object_x && obj.getObject_x() + obj.getObject_width() <= object_x) {
					
				}
				// ����1λ�ھ���2���Ϸ�
				else if (object_y <= obj.getObject_y()
						&& object_y + object_height + 30 <= obj.getObject_y()) {
					
				}
				// ����1λ�ھ���2���·�
				else if (obj.getObject_y() <= object_y
						&& obj.getObject_y() + obj.getObject_height() + 30 <= object_y) {
					
				}
				else{
					isAlive = false;
					attack = true;
				}
			}
			else if(obj instanceof MiddlePlane){
				if (object_x <= obj.getObject_x() && object_x + object_width <= obj.getObject_x()) {
					
				}
				// ����1λ�ھ���2���Ҳ�
				else if (obj.getObject_x() <= object_x && obj.getObject_x() + obj.getObject_width() <= object_x) {
					
				}
				// ����1λ�ھ���2���Ϸ�
				else if (object_y <= obj.getObject_y()
						&& object_y + object_height + 20 <= obj.getObject_y()) {
					
				}
				// ����1λ�ھ���2���·�
				else if (obj.getObject_y() <= object_y
						&& obj.getObject_y() + obj.getObject_height() + 20 <= object_y) {
					
				}
				else{
					isAlive = false;
					attack = true;
				}
			}
			else{
				if (object_x <= obj.getObject_x() && object_x + object_width <= obj.getObject_x()) {
					
				}
				// ����1λ�ھ���2���Ҳ�
				else if (obj.getObject_x() <= object_x && obj.getObject_x() + obj.getObject_width() <= object_x) {
					
				}
				// ����1λ�ھ���2���Ϸ�
				else if (object_y <= obj.getObject_y()
						&& object_y + object_height + 10 <= obj.getObject_y()) {
					
				}
				// ����1λ�ھ���2���·�
				else if (obj.getObject_y() <= object_y
						&& obj.getObject_y() + obj.getObject_height() + 10 <= object_y) {
					
				}
				else{
					isAlive = false;
					attack = true;
				}
			}
		}
		if(isAlive2){
			if(obj instanceof SmallPlane){
				if (object_x2 <= obj.getObject_x() && object_x2 + object_width <= obj.getObject_x()) {
					
				}
				// ����1λ�ھ���2���Ҳ�
				else if (obj.getObject_x() <= object_x2 && obj.getObject_x() + obj.getObject_width() <= object_x2) {
					
				}
				// ����1λ�ھ���2���Ϸ�
				else if (object_y2 <= obj.getObject_y()
						&& object_y2 + object_height + 30 <= obj.getObject_y()) {
					
				}
				// ����1λ�ھ���2���·�
				else if (obj.getObject_y() <= object_y2
						&& obj.getObject_y() + obj.getObject_height() + 30 <= object_y2) {
					
				}
				else{
					isAlive2 = false;
					attack2 = true;
				}
			}
			else if(obj instanceof MiddlePlane){
				if (object_x2 <= obj.getObject_x() && object_x2 + object_width <= obj.getObject_x()) {
					
				}
				// ����1λ�ھ���2���Ҳ�
				else if (obj.getObject_x() <= object_x2 && obj.getObject_x() + obj.getObject_width() <= object_x2) {
					
				}
				// ����1λ�ھ���2���Ϸ�
				else if (object_y2 <= obj.getObject_y()
						&& object_y2 + object_height + 20 <= obj.getObject_y()) {
					
				}
				// ����1λ�ھ���2���·�
				else if (obj.getObject_y() <= object_y2
						&& obj.getObject_y() + obj.getObject_height() + 20 <= object_y2) {
					
				}
				else{
					isAlive2 = false;
					attack2 = true;
				}
			}
			else{
				if (object_x2 <= obj.getObject_x() && object_x2 + object_width <= obj.getObject_x()) {
					
				}
				// ����1λ�ھ���2���Ҳ�
				else if (obj.getObject_x() <= object_x2 && obj.getObject_x() + obj.getObject_width() <= object_x2) {
					
				}
				// ����1λ�ھ���2���Ϸ�
				else if (object_y2 <= obj.getObject_y()
						&& object_y2 + object_height + 10 <= obj.getObject_y()) {
					
				}
				// ����1λ�ھ���2���·�
				else if (obj.getObject_y() <= object_y2
						&& obj.getObject_y() + obj.getObject_height() + 10 <= object_y2) {
					
				}
				else{
					isAlive2 = false;
					attack2 = true;
				}
			}
		}
		if(attack && attack2)
			harm = 2;
		return attack || attack2;
	}
	//�ж��ӵ��Ƿ����
	public boolean isAlive() {
		return isAlive || isAlive2;
	}
}
