package com.example.newslist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.ListView;

/**
 * Created by Mao on 2016/2/23.
 */
public class MyListViewTwo extends ListView {
    private final Transformation mTransformation;

    public MyListViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs);

            setStaticTransformationsEnabled(false);
            mTransformation = new Transformation();
            mTransformation.setTransformationType(Transformation.TYPE_MATRIX);

    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        mTransformation.getMatrix().reset();
        final int childTop = Math.max(0, child.getTop());
        final int parentHeight = getHeight();
        final float scale = (float) (parentHeight - (childTop / 2)) / getHeight();
        Log.i("scale", scale + "");
        final float px = child.getLeft() + (child.getWidth()) / 2;
        final float py = child.getTop() + (child.getHeight()) / 2;
        mTransformation.getMatrix().postScale(scale, scale, px, py);
        t.compose(mTransformation);
        return true;
    }
}