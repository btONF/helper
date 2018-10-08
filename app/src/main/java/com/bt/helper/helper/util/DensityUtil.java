package com.bt.helper.helper.util;

import android.util.TypedValue;

/**
 * Created by 18030693 on 2018/8/10.
 */

public class DensityUtil {
    private DensityUtil() {}
    private static DensityUtil densityUtil;
    public static DensityUtil getInstance(){
        if(densityUtil == null){
            synchronized(DensityUtil.class) {
                if(densityUtil == null) {
                    densityUtil = new DensityUtil();
                }
            }
        }
        return densityUtil;
    }

    public float dp2px(int data){
        return getTransformData(TypedValue.COMPLEX_UNIT_DIP, data);
    }

    public float sp2px(int data){
        return getTransformData(TypedValue.COMPLEX_UNIT_SP, data);
    }

    public float pt2px(int data){
        return getTransformData(TypedValue.COMPLEX_UNIT_PT, data);
    }

    private float getTransformData(int type, int data){
        return TypedValue.applyDimension(type, data,
                AppUtil.getInstance().getResources().getDisplayMetrics());
    }
}
