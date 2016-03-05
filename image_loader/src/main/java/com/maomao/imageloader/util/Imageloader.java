package com.maomao.imageloader.util;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.LogRecord;

/**
 * 图片加载类
 * Created by Mao on 2015/9/22.
 */
public class Imageloader {

    //单例模式
    private static Imageloader mInstance;

    /**
     * 图片缓存核心对象
     */
    private LruCache<String , Bitmap> mLruCache ;

    /**
     * 线程池
     */
    private ExecutorService mThreadPool ;

    /**
     * 线程池默认线程数
     */
    private static  final  int DEAFULT_THREAD_COUNT = 1 ;

    /**
     * 队列的调度方式
     */
    private Type mType = Type.LIFO;

    /**
     * 任务队列
     * LinkedList与ArrayList对比：
     * LinkedList有从头部和尾部选取的方法 ， ArrayList只能挨个获取
     * LinkedList内部存储是链表存储，ArrayList是连续存储。
     */
    private LinkedList<Runnable> mTaskQueue;

    /**
     * 后台轮询线程
     */
    private Thread mPoolThread ;
    private Handler mPoolThreadHandler ;

    /**
     * UI线程中的Handler
     */
    private Handler mUIHandler ;

    private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;

    /**
     * 加载策略
     */
    public enum Type{
        FIFO,LIFO;
    }

    //构造方法私有化 ， 外界无法使用new
    private Imageloader(int threadCount , Type type) {
        init(threadCount,type);
    }

    /**
     * 初始化
     * @param threadCount
     * @param type
     */
    private void init(int threadCount, Type type) {
        //后台轮询线程
        mPoolThread = new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                mPoolThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        //线程池取出一个任务进行执行
                        mThreadPool.execute(getTask());
                        try {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e) {
                        }
                    }


                };
                //释放信号量
                mSemaphorePoolThreadHandler.release();
                Looper.loop();
            };
        };

        mPoolThread.start();

        //获取应用的最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8 ;

        mLruCache = new LruCache<String , Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
        //创建线程池
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;

        mSemaphoreThreadPool = new Semaphore(threadCount);
    }

    /**
     * 从任务队列取出一个方法
     * @return
     */
    private Runnable getTask() {
        if (mType == Type.FIFO){
            return mTaskQueue.removeFirst();
        }else if(mType == Type.LIFO){
            return mTaskQueue.removeLast();
        }
        return null;
    }

    //单例 ， 外界没有实例 ， 使用类名调用
    public static Imageloader getInstance(int threadCount , Type type) {
        //懒加载

        //第一重判断 ， 过滤掉大部分代码 ， 防止两个线程同时到达 ， 需要同步的线程比较少 ， 提升效率
        if (mInstance == null) {
            synchronized (Imageloader.class) {
                //
                if (mInstance == null) {
                    mInstance = new Imageloader(threadCount,type);
                }
            }
        }
        return mInstance;
    }

    /**
     * 感觉path为imageview设置图片
     * @param path
     * @param imageView
     */
    public void loadImage(final String path , final ImageView imageView){
        imageView.setTag(path); //防止调用多次

        if (mUIHandler == null) {
            mUIHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    //获取得到的图片，为imageView回调设置图片
                    ImageBeanHolder holder = (ImageBeanHolder) msg.obj;
                    Bitmap bm = holder.bitmap;
                    ImageView imageView = holder.imageView;
                    String path = holder.path;


                    if (imageView.getTag().toString().equals(path)) {
                        imageView.setImageBitmap(bm);
                    }
                }
            };
        }
        //根据path在缓存中获取bitmap
        Bitmap bm = getBitmapFromLruCache(path);

        if (bm != null) {
            refreshBitmap(bm, path, imageView);
        }else
        {
            addTasks(new Runnable(){
                @Override
                public void run() {
                    //加载图片
                    //图片压缩
                    //1、获得图片需要现实的大小
                    ImageSize imageSize = getImageViewSize(imageView);
                    //2、压缩图片
                    Bitmap bm =  decodeSampledBitmapFromPath(path,imageSize.width , imageSize.height);
                    //3、把图片加入到缓存
                    addBitmapToLruCache(path , bm);
                    //4、回调
                    refreshBitmap(bm, path, imageView);
                    mSemaphoreThreadPool.release();
                }
            });
        }
    }

    private void refreshBitmap(Bitmap bm, String path, ImageView imageView) {
        Message message = Message.obtain();
        ImageBeanHolder holder = new ImageBeanHolder() ;
        holder.bitmap = bm;
        holder.path = path;
        holder.imageView = imageView;
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    /**
     * 将图片加入缓存
     * @param path
     * @param bm
     */
    private void addBitmapToLruCache(String path, Bitmap bm) {
        if (getBitmapFromLruCache(path) == null){
            if (bm != null){
                mLruCache.put(path , bm);
            }
        }
    }

    /**
     * 根据图片需要现实的宽和高对图片进行压缩
     * @param path
     * @param width
     * @param height
     */
    private Bitmap decodeSampledBitmapFromPath(String path , int width, int height) {
        //获取图片的宽和高， 并不把图片加载到内存中
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true  ;
        BitmapFactory.decodeFile(path,options);

        options.inSampleSize = caculateInSampleSize(options,width,height);

        //使用获取到的InSampleSize再次解析图片
        options.inJustDecodeBounds = false  ;
        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        return bitmap ;
    }

    /**
     * 根据需求的宽和高 和 图片实际的宽和高 计算SampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1 ;
        if (width > reqWidth || height > reqHeight){
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int hidthRadio = Math.round(height*1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio,hidthRadio);
        }
        return inSampleSize;
    }

    /**
     * 根据ImageView获取适当的压缩的宽和高
     * @param imageView
     * @return
     */
    protected ImageSize getImageViewSize(ImageView imageView) {
        ImageSize imageSize = new ImageSize() ;

        DisplayMetrics  metrics=imageView.getContext().getResources().getDisplayMetrics();

        ViewGroup.LayoutParams lp = imageView.getLayoutParams();

        int width =  imageView.getWidth(); //获取实际宽度
        if (width <= 0) {
            width = lp.width ; //获取imageview在layout中生命的宽度
        }
        if (width <= 0 ){
            //width = imageView.getMaxWidth(); //检查最大值
            width = getImageViewFieldValue(imageView,"mMaxWidth");
        }

        if(width <= 0 ){
            width = metrics.widthPixels ; //屏幕宽度
        }

        int height =  imageView.getHeight(); //获取实际宽度
        if (height <= 0) {
            height = lp.height ; //获取imageview在layout中生命的宽度
        }
        if (height <= 0 ){
            //height = imageView.getMaxHeight(); //检查最大值
            height = getImageViewFieldValue(imageView,"mMaxHeight");
        }

        if(height <= 0 ){
            height = metrics.heightPixels; //屏幕宽度
        }

        imageSize.width = width;
        imageSize.height = height;

        return imageSize;
    }

    /**
     * 通过反射获取imageView的某个属性值
     * @param object
     * @param fieldName
     * @return
     */
    private static int getImageViewFieldValue(Object object , String fieldName){
        int value = 0 ;

        Field field = null;
        try {
            field = ImageView.class.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {

        }
        field.setAccessible(true);

        try {
            int fieldValue = field.getInt(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE){
                value = fieldValue;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return value;
    }


    private void addTasks(Runnable runnable) {
        mTaskQueue.add(runnable);

        //if(mPoolThreadHandler == null) wait()
        try {
            if (mPoolThreadHandler == null)
                mSemaphorePoolThreadHandler.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    /**
     * 根据path在缓存中获取bitmap
     * @param key
     * @return
     */
    private Bitmap getBitmapFromLruCache(String key) {
        return mLruCache.get(key);
    }

    private  class  ImageBeanHolder{
        Bitmap bitmap ;
        ImageView imageView ;
        String path ;

    }

    private class ImageSize{
        int width ;
        int height ;
    }
}
