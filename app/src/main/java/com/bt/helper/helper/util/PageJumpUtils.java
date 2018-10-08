package com.bt.helper.helper.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bt.helper.helper.activity.PunchCardActivity;
import com.bt.helper.helper.activity.PunchCardDataActivity;
import com.bt.helper.helper.activity.StatisticsActivity;

/**
 * Created by 18030693 on 2018/8/14.
 */

public class PageJumpUtils {
    public static final String BUNDLE_KEY = "bundle_key";
    public static final int PUNCH_CARD = 1;
    public static final int PUNCH_CARD_DATA = 2;
    public static final int STATISTICS = 3;


    private PageJumpUtils() {}
    private static PageJumpUtils pageJumpUtils;
    public static PageJumpUtils getInstance(){
        if(pageJumpUtils == null){
            synchronized(PageJumpUtils.class) {
                if(pageJumpUtils == null) {
                    pageJumpUtils = new PageJumpUtils();
                }
            }
        }
        return pageJumpUtils;
    }

    public void JumpToPage(Context context, int type, Bundle bundle){
        Intent intent = null;
        switch (type){
            case PUNCH_CARD:
                intent = new Intent(context, PunchCardActivity.class);
                break;
            case PUNCH_CARD_DATA:
                intent = new Intent(context, PunchCardDataActivity.class);
                break;
            case STATISTICS:
                intent = new Intent(context, StatisticsActivity.class);
                break;
            default:
                break;
        }
        if (intent == null){
            return;
        }
        if (bundle != null) {
            intent.putExtra(BUNDLE_KEY, bundle);
        }
        context.startActivity(intent);
    }
}
