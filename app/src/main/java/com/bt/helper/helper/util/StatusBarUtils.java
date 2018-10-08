package com.bt.helper.helper.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by 18030693 on 2018/8/10.
 */

public class StatusBarUtils {
    //默认留出状态栏高度
    private static final int DEFAULT_STATUS_BAR_HEIGHT_DP = 50;

    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    public static void setTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        transparentStatusBar(activity);
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static void transparentStatusBar(Activity activity) {
        //API大于等于21可以设置自定义状态栏颜色
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //自定义颜色和状态栏透明无法共存
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //设置主视图位于状态栏下方
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //设置状态栏颜色
        activity.getWindow().setStatusBarColor(0x33000000);
    }

    /**
     * 设置顶部UI
     *
     * 顶部UI需要额外增加高度,并增加相同高度的上边距;传入的ViewGroup高度必须为固定值
     *
     * @param topView 需要设置的view
     */
    public static void setTopView(Context context, ViewGroup topView) {
        //测量宽高
        topView.measure(0,0);
        int width = topView.getMeasuredWidth();
        int height = topView.getMeasuredHeight();
        ViewGroup.MarginLayoutParams params;
        if (topView.getLayoutParams()!=null){
            params = (ViewGroup.MarginLayoutParams) topView.getLayoutParams();
        }else {
            params = new ViewGroup.MarginLayoutParams(width,height);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //API低于19将视图上移默认高度
                params.topMargin = 0 - (int) DensityUtil.getInstance().dp2px(DEFAULT_STATUS_BAR_HEIGHT_DP);
        }else {
            //获取status_bar_height资源的ID
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取相应的尺寸
                int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
                params.topMargin =  statusBarHeight -  (int) DensityUtil.getInstance().dp2px(DEFAULT_STATUS_BAR_HEIGHT_DP);
            }
        }
        topView.setLayoutParams(params);
    }

}
