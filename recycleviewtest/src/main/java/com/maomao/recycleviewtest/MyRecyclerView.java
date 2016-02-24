package com.maomao.recycleviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by MaoTong on 2016/2/24.
 */
public class MyRecyclerView extends LinearLayoutManager {

    private int mFirstVisiblePosition = 0;
    private int mLastVisiblePosition = 0;

    public int getFirstVisiblePosition() {
        return mFirstVisiblePosition;
    }

    public void setFirstVisiblePosition(int mFirstVisiblePosition) {
        this.mFirstVisiblePosition = mFirstVisiblePosition;
    }

    public int getmLastVisiblePosition() {
        return mLastVisiblePosition;
    }

    public void setmLastVisiblePosition(int mLastVisiblePosition) {
        this.mLastVisiblePosition = mLastVisiblePosition;
    }

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
       // Log.e("MT", "");

        View view = recycler.getViewForPosition(0);

        Canvas canvas = new Canvas();
        Paint paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        paint.setColor(Color.WHITE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        canvas.drawRect(0 , 0 , 100 , 100 , paint);
        view.draw(canvas);
        view.setBackgroundColor(Color.RED);
        addView(view);

/*        View view = recycler.getViewForPosition(0);
        view.measure(100 , 100);
        addView(view);*/
    }
}
