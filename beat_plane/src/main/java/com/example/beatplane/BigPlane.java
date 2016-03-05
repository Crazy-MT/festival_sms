package com.example.beatplane;

import java.util.Random;
import com.example.beatplane.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*���ͷɻ����*/
public class BigPlane extends GameObject{
	private Bitmap bigPlane;
	private int blood; 				// ����ĵ�ǰѪ��
	private int bloodVolume; 		 // �����Ѫ��
	BigPlane(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();
		this.score = 3000;
	}
	//��ʼ�����
	@Override
	public void setScreenWH(float screen_width,float screen_height){
		super.setScreenWH(screen_width, screen_height);
	}
	//��ʼ�����
	@Override
	public void initial(int arg0,float arg1,float arg2,int arg3){
		super.initial(arg0,arg1,arg2,arg3);
		bloodVolume = 30;	
		blood = bloodVolume;
		Random ran = new Random();
		object_x = ran.nextInt((int)(screen_width - object_width));
		this.speed = ran.nextInt(2) + 4 * arg3;	
	}
	//��ʼ��ͼƬ
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		bigPlane = BitmapFactory.decodeResource(resources, R.drawable.big);
		object_width = bigPlane.getWidth();		//���ÿһ֡λͼ�Ŀ�
		object_height = bigPlane.getHeight()/5;		//���ÿһ֡λͼ�ĸ�
	}
	//��ͼ����
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			//�ж��Ƿ�ը
			if(!isExplosion){		
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(bigPlane, object_x, object_y,paint);
				canvas.restore();
				logic();
			}
			else{
				int y = (int) (currentFrame * object_height); // ��õ�ǰ֡�����λͼ��Y���
				canvas.save();
				canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
				canvas.drawBitmap(bigPlane, object_x, object_y - y,paint);
				canvas.restore();
				currentFrame++;
				if(currentFrame >= 5){
					currentFrame = 0;
					isExplosion = false;
					isAlive = false;
				}
			}	
		}	
	}
	//�ͷ���Դ
	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(!bigPlane.isRecycled()){
			bigPlane.recycle();
		}
	}
	// �����ײ
	@Override
	public boolean isCollide(GameObject obj) {
		return super.isCollide(obj);
	}
	//������߼�����
	@Override
	public void logic(){
		if (object_y < screen_height) {
			object_y += speed;
		} else {
			isAlive = false;
		}
	}
	//���������߼�����
	@Override
	public void attacked(int harm){
		blood -= harm;
		if (blood <= 0) {
			isExplosion = true;
		}
	}
}
