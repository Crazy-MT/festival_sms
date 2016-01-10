package com.example.beatplane;

import java.util.ArrayList;
import java.util.List;

import com.example.beatplane.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*��ҷɻ�����*/
public class MyPlane extends GameObject {
	private Bitmap myplane;
	private Bitmap myplane2;
	private float middle_x;
	private float  middle_y;
	private List<GameObject> bullets;	//�ӵ���
	private MainView mainView;
	MyPlane(MainView mainView,Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();
		this.speed = 8;
		this.mainView = mainView;
		bullets = new ArrayList<GameObject>();
		for(int i = 0;i < 4;i++){
			Bullet bullet = new Bullet(resources);
			bullets.add(bullet);
		}
	}

	// ��ʼ������
	@Override
	public void setScreenWH(float screen_width, float screen_height) {
		super.setScreenWH(screen_width, screen_height);
		object_x = screen_width/2 - object_width/2;
		object_y = screen_height - object_height;
		middle_x = object_x + object_width/2;
		middle_y = object_y + object_height/2;
	}
	
	// ��ʼ��ͼƬ
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		myplane = BitmapFactory.decodeResource(resources, R.drawable.myplane);
		myplane2 = BitmapFactory.decodeResource(resources, R.drawable.myplaneexplosion);
		object_width = myplane.getWidth() / 2; // ���ÿһ֡λͼ�Ŀ�
		object_height = myplane.getHeight(); // ���ÿһ֡λͼ�ĸ�
	}

	// ��ͼ����
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			int x = (int) (currentFrame * object_width); // ��õ�ǰ֡�����λͼ��Y����
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
			canvas.drawBitmap(myplane, object_x - x, object_y, paint);
			canvas.restore();
			currentFrame++;
			if (currentFrame >= 2) {
				currentFrame = 0;
			}
		}
		else{
			int x = (int) (currentFrame * object_width); // ��õ�ǰ֡�����λͼ��Y����
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y
					+ object_height);
			canvas.drawBitmap(myplane2, object_x - x, object_y, paint);
			canvas.restore();
			currentFrame++;
			if (currentFrame >= 2) {
				currentFrame = 1;
			}
		}
	}
	//�����ӵ�
	public void changeButtle(){
		bullets.clear();
		for(int i = 0;i < 4;i++){
			Bullet2 bullet = new Bullet2(resources);
			bullets.add(bullet);
		}
	}
	//��ʼ���ӵ�����
	public void initButtle(){
		for(GameObject obj:bullets){	
			if(!obj.isAlive()){
				obj.initial(0,middle_x, middle_y,0);
				break;
			}
		}
	}
	//�����ӵ�
	public void shoot(Canvas canvas,List<GameObject> planes){
		//�����ӵ��Ķ���
		for(GameObject obj:bullets){	
			if(obj.isAlive()){
				for(GameObject pobj:planes){
					if(pobj.isAlive() && !pobj.isExplosion()){
						if(obj.isCollide(pobj)){			//�����ײ
							pobj.attacked(obj.harm);
							if(pobj.isExplosion()){	
								//���ӻ���
								mainView.addBigSum(pobj.getScore());
								mainView.addMiddleSum(pobj.getScore());
								mainView.addScoreSum(pobj.getScore());
								mainView.addMissileSum(pobj.getScore());
								mainView.addBossSum(pobj.getScore());
								if(pobj instanceof SmallPlane){
									mainView.getSounds().playSound(2, 0);		//�ɻ�ը�ٵ���Ч
								}
								else if(pobj instanceof MiddlePlane){
									mainView.getSounds().playSound(3, 0);		//�ɻ�ը�ٵ���Ч
								}
								else{
									mainView.getSounds().playSound(4, 0);		//�ɻ�ը�ٵ���Ч
								}			
							}
							break;
						}
					}
				}
				obj.drawSelf(canvas);					//�����ӵ�
			}
		}
	}
	// �ͷ���Դ
	@Override
	public void release() {
		// TODO Auto-generated method stub
		for(GameObject obj:bullets){	
			obj.release();
		}
		if(!myplane.isRecycled()){
			myplane.recycle();
		}
		if(!myplane2.isRecycled()){
			myplane2.recycle();
		}
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public float getMiddle_x() {
		return middle_x;
	}

	public void setMiddle_x(float middle_x) {
		this.middle_x = middle_x;
		this.object_x = middle_x - object_width/2;
	}

	public float getMiddle_y() {
		return middle_y;
	}

	public void setMiddle_y(float middle_y) {
		this.middle_y = middle_y;
		this.object_y = middle_y - object_height/2;
	}
	
	public void setAlive(boolean isAlive){
		this.isAlive = isAlive;
	}
}
