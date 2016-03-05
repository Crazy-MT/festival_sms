package com.example.newslist;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

public class ImageLoader {
	private ImageView mImageView;
	private String mUrl;
	private LruCache<String,Bitmap> mCache;
	private ListView mListView;
	private Set<NewsAsyncTask> mTask;
	
	public ImageLoader(ListView listview) {
		mListView = listview;
		mTask = new HashSet<NewsAsyncTask >();
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 4;
		mCache = new LruCache<String, Bitmap>(cacheSize){
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			};
		};
	}
	public void addBitmapToCache(String url, Bitmap bitmap)
	{
		if (getBitmapFromCache(url) == null)
		{
			mCache.put(url, bitmap);
		}
	}

	public Bitmap getBitmapFromCache(String url)
	{
		return mCache.get(url);
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(mImageView.getTag().equals(mUrl));
			{
				mImageView.setImageBitmap((Bitmap) msg.obj);
			}
		};
	};
	
	public void showImageByThread(ImageView imageView,final String url)
	{
		mImageView = imageView;
		mUrl = url;
		new Thread()
		{
			public void run()
			{
				super.run();
				Bitmap bitmap = getBitmapFromUrl(url);
				Message message = Message.obtain();
				message.obj = bitmap;
				handler.sendMessage(message);
			}
		}.start();
	}
	
	public Bitmap getBitmapFromUrl(String urlString)
	{
		Bitmap bitmap;
		InputStream is = null;
		try {
			URL url = new URL(urlString);
			try {
				HttpURLConnection connection=  (HttpURLConnection) url.openConnection();
				is = new BufferedInputStream(connection.getInputStream());
				bitmap = BitmapFactory.decodeStream(is);
				connection.disconnect();
				return bitmap;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void showImageByAsyncTask(ImageView imageView,String url)
	{

		Bitmap bitmap = getBitmapFromCache(url);

		if (bitmap == null)
		{
			//new NewsAsyncTask(url).execute(url);
			imageView.setImageResource(R.drawable.ic_launcher);
			
		}else
		{
			imageView.setImageBitmap(bitmap);
		}
	}
	
	public void cancelAllTast()
	{
		if (mTask != null)
		{
			for (NewsAsyncTask task : mTask)
			{
				task.cancel(false);
				
			}
		}
	}

	public void loadImages(int start,int end)
	{
		for ( int i = start; i < end; i ++)
		{
			String url =NewsAdapter.URLS[i];
			Bitmap bitmap = getBitmapFromCache(url);
			if (bitmap == null)
			{
				NewsAsyncTask task = new NewsAsyncTask(url); 
				task.execute(url);
				mTask.add(task);
			}else
			{
				ImageView imageView = (ImageView) mListView.findViewWithTag(url);
				imageView.setImageBitmap(bitmap);
			}	
		}
	}
	
	private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap>
	{
//		private ImageView mImageView;
		private String mUrl;
		public NewsAsyncTask(String url) {
//			mImageView = imageView;
			mUrl = url;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			String url = params[0];

			Bitmap bitmap = getBitmapFromUrl(url);
			if (bitmap != null)
			{
				addBitmapToCache(url, bitmap);
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
			if(imageView != null && bitmap != null)
			{
				imageView.setImageBitmap(bitmap);
			}	
			mTask.remove(this);
		}
	}
}
