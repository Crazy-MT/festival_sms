package com.example.beatplane;

import java.util.Random;
import com.example.beatplane.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*���ӵ�����*/
public class Bullet extends GameObject{
	private Bitmap bullet; 		 // �ӵ���ͼƬ
	Bullet(Resources resources) {
		super(resources);
		initBitmap();
	}

	// ��ʼ�����
	@Override
	public void initial(int arg0,float arg1,float arg2,int arg3) {
		isAlive = true;
		harm = 1;
		Random ran = new Random();
		speed = (ran.nextInt(20) + 100);	
		object_x = arg1 - object_width / 2;
		object_y = arg2 - object_height;
	}
	//��ʼ��ͼƬ
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);
		object_width = bullet.getWidth();
		object_height = bullet.getHeight();
	}
	// ��ͼ����
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if (isAlive) {
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width,
					object_y + object_height);
			canvas.drawBitmap(bullet, object_x, object_y, paint);
			canvas.restore();
			logic();
		}
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
		} else {
			isAlive = false;
		}
	}
	// �����ײ
	@Override
	public boolean isCollide(GameObject obj) {
		if(obj instanceof SmallPlane){
			if (object_x <= obj.getObject_x() && object_x + object_width <= obj.getObject_x()) {
				return false;
			}
			// ����1λ�ھ���2���Ҳ�
			else if (obj.getObject_x() <= object_x && obj.getObject_x() + obj.getObject_width() <= object_x) {
				return false;
			}
			// ����1λ�ھ���2���Ϸ�
			else if (object_y <= obj.getObject_y()
					&& object_y + object_height + 30 <= obj.getObject_y()) {
				return false;
			}
			// ����1λ�ھ���2���·�
			else if (obj.getObject_y() <= object_y
					&& obj.getObject_y() + obj.getObject_height() + 30 <= object_y) {
				return false;
			}
		}
		else if(obj instanceof MiddlePlane){
			if (object_x <= obj.getObject_x() && object_x + object_width <= obj.getObject_x()) {
				return false;
			}
			// ����1λ�ھ���2���Ҳ�
			else if (obj.getObject_x() <= object_x && obj.getObject_x() + obj.getObject_width() <= object_x) {
				return false;
			}
			// ����1λ�ھ���2���Ϸ�
			else if (object_y <= obj.getObject_y()
					&& object_y + object_height + 20 <= obj.getObject_y()) {
				return false;
			}
			// ����1λ�ھ���2���·�
			else if (obj.getObject_y() <= object_y
					&& obj.getObject_y() + obj.getObject_height() + 20 <= object_y) {
				return false;
			}
		}
		else{
			if (object_x <= obj.getObject_x() && object_x + object_width <= obj.getObject_x()) {
				return false;
			}
			// ����1λ�ھ���2���Ҳ�
			else if (obj.getObject_x() <= object_x && obj.getObject_x() + obj.getObject_width() <= object_x) {
				return false;
			}
			// ����1λ�ھ���2���Ϸ�
			else if (object_y <= obj.getObject_y()
					&& object_y + object_height + 10 <= obj.getObject_y()) {
				return false;
			}
			// ����1λ�ھ���2���·�
			else if (obj.getObject_y() <= object_y
					&& obj.getObject_y() + obj.getObject_height() + 10 <= object_y) {
				return false;
			}
		}
		isAlive = false;
		return true;
	}
}
