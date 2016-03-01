package com.maomao.ballfollowfinget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mao on 2016/2/29.
 */
public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /*
        获得此viewGroup上级容器为其推荐的宽高以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        /*
        * 如果是wrap_content设置的宽和高
        * */
        int width = 0;
        int height = 0;
        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        //用于计算左边两个childView的高度
        int lHeight = 0;
        //用于计算右边两个childView的高度，最终高度取两者之间大值
        int rHeight = 0;

        //用于计算上边两个ChileView的宽度
        int tWidth = 0;
        //用于计算下面两个childView的宽度，最终宽度取两者之间大值
        int bWidth = 0;

        /**
         * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要是用于容器是wrap_content时
         * */
        for (int i = 0; i < cCount; i++){
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            //上面两个childView
            if (i==0 || i==1){
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 2 || i == 3){
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 0 || i == 2){
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

            if (i == 1 || i ==3){
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }
        }

        width = Math.max(tWidth , bWidth);
        height = Math.max(lHeight , rHeight);

        /*
        * 如果是wrap_content设置为我们计算的值
        * 否则：直接设置为父容器计算的值
        * */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width,(heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0 ;

        MarginLayoutParams cParams = null ;

        /*
        * 遍历所有childView根据其宽和高，以及margin进行布局
        *
        * */
        for (int i = 0; i < cCount; i++){
            View childView = getChildAt(i);
            cWidth= childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0 , ct = 0, cr = 0 , cb = 0;

            switch (i){
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = cParams.topMargin;
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
            }
            cr = cl + cWidth ;
            cb = cHeight + ct;
            childView.layout(cl,ct,cr,cb);
        }
    }
}
