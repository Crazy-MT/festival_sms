package widget.amberweather.com.news_card;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ImageLoader {
    private ImageView mImageView;
    private String mUrl;
    private LruCache<String, Bitmap> mCache;
    private RecyclerView mRecyclerView;
    private Set<NewsAsyncTask> mTask;

    public ImageLoader(RecyclerView listview) {
        mRecyclerView = listview;
        mTask = new HashSet<NewsAsyncTask>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 1;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }

            ;
        };
    }

    public ImageLoader() {
    }

    public void addBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {
            mCache.put(url, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String url) {
        return mCache.get(url);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mImageView.getTag().equals(mUrl)) ;
            {
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }

        ;
    };

    public void showImageByThread(ImageView imageView, final String url) {
        mImageView = imageView;
        mUrl = url;
        new Thread() {
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromUrl(url);
                Message message = Message.obtain();
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        }.start();
    }

    public Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream inputStream = null;
        InputStream copyiInputStream1 = null;
        InputStream copyiInputStream2 = null;
        try {
            URL url = new URL(urlString);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                inputStream = connection.getInputStream();

                byte[] data = InputStreamTOByte(inputStream);
                try {
                    copyiInputStream1 = byteTOInputStream(data);
                    copyiInputStream2 = byteTOInputStream(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bitmap = decodeSampledBitmapFromInputStream(copyiInputStream1,copyiInputStream2 , 60, 100);

                /*ByteArrayOutputStream out = new ByteArrayOutputStream();
                copy(is,out);
                is2 = new ByteArrayInputStream(out.toByteArray());

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                bitmap = BitmapFactory.decodeStream(is , null , options);*/
                /*// 调用上面定义的方法计算inSampleSize值
                options.inSampleSize = calculateInSampleSize(options, 60, 100);
                // 使用获取到的inSampleSize值再次解析图片
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(is2 , null , options);
               // Log.e("is" , is.toString());
                //bitmap = BitmapFactory.decodeByteArray(bytes , 0 ,bytes.length , options);*/
                connection.disconnect();
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                copyiInputStream1.close();
                copyiInputStream2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Bitmap decodeSampledBitmapFromInputStream(InputStream in, InputStream copyOfin, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(copyOfin, null, options);
    }

    public InputStream byteTOInputStream(byte[] in) throws Exception{

        ByteArrayInputStream is = new ByteArrayInputStream(in);
        return is;
    }
    public byte[] InputStreamTOByte(InputStream in) throws IOException{

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024*16];
        int count = -1;
        while((count = in.read(data,0,1024*16)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return outStream.toByteArray();
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        Log.e("height" , height+"");

        Log.e("reqHeight" , reqHeight+"");
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            //计算缩放比例
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public void showImageByAsyncTask(ImageView imageView, String url) {

        Bitmap bitmap = getBitmapFromCache(url);

        if (bitmap == null) {
            //new NewsAsyncTask(url).execute(url);
            imageView.setImageResource(R.mipmap.ic_launcher);

        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    public void cancelAllTask() {
        if (mTask != null) {
            for (NewsAsyncTask task : mTask) {
                task.cancel(false);

            }
        }
    }

	/*public void loadImages(int start,int end)
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
	}*/

    private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {
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
            if (bitmap != null) {
                addBitmapToCache(url, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mRecyclerView.findViewWithTag(mUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            mTask.remove(this);
        }
    }
}
