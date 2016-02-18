package com.maotong.city;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by MaoTong on 2016/2/18.
 */
public class StepComparator implements Comparator<CityBean> {

    @Override
    public int compare(CityBean lhs, CityBean rhs) {

        Log.e("compare" , lhs.getCityName() + "  " + rhs.getCityName());
        if (TextUtils.isEmpty(lhs.getCityTimeZone()) || TextUtils.isEmpty(rhs.getCityTimeZone())){
            Log.e("compare 2" , lhs.getCityName() + "  " + rhs.getCityName());
            return -1 ;
        }else if (lhs.getCityTimeZone().equals(rhs.getCityTimeZone())){
            Log.e("compare 3" , lhs.getCityName() + "  " + rhs.getCityName());
            return 0;
        }else{
            float lhsTimeZone = Float.valueOf(lhs.getCityTimeZone());
            float rhsTimeZone = Float.valueOf(rhs.getCityTimeZone());
            return lhsTimeZone < rhsTimeZone ? 1 : -1;
        }
    }
}