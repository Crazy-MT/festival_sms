package com.example.beatplane;

import java.util.HashMap;

import com.example.beatplane.R;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
/*��Ч������*/
public class GameSoundPool {
	private MainActivity mainActivity;
	private SoundPool soundPool;
	private HashMap<Integer,Integer>map;
	private boolean isOpen;
	public GameSoundPool(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		soundPool = new SoundPool(8,AudioManager.STREAM_MUSIC,0);
		map = new HashMap<Integer,Integer>();
	}
	//��ʼ��ϵͳ��Ч
	public void initSysSound(){
		if(isOpen){
			map.put(1, soundPool.load(mainActivity, R.raw.button, 1));
		}
	}
	//��ʼ��ϵͳ��Ч
	public void initSound(){
		if(isOpen){
			map.put(1, soundPool.load(mainActivity, R.raw.shoot, 1));
			map.put(2, soundPool.load(mainActivity, R.raw.explosion, 1));
			map.put(3, soundPool.load(mainActivity, R.raw.explosion2, 1));
			map.put(4, soundPool.load(mainActivity, R.raw.explosion3, 1));
			map.put(5, soundPool.load(mainActivity, R.raw.bigexplosion, 1));
			map.put(6, soundPool.load(mainActivity, R.raw.get_goods, 1));
		}
	}
	//������Ч
	public void playSound(int sound,int loop){
		if(isOpen){
			AudioManager am = (AudioManager)mainActivity.getSystemService(Context.AUDIO_SERVICE);
			float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			float stramMaxVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			float volume = stramVolumeCurrent/stramMaxVolumeCurrent;
			soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);
		}	
	}
	//�ͷ���Դ
	public void release(){
		if(soundPool != null)
			soundPool.release();
	}
	//ֹͣ������Ч
	public void stopSound(int streamID){
		if(isOpen){
			soundPool.pause(streamID);
		}
	}	
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
}
