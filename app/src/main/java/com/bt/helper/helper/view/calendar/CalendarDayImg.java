package com.bt.helper.helper.view.calendar;

import com.bt.helper.helper.R;

/**
 * Created by 18030693 on 2018/8/15.
 */

public enum CalendarDayImg {
    DAY_1(R.drawable.day_1, 1), DAY_2(R.drawable.day_2, 2), DAY_3(R.drawable.day_3, 3),
    DAY_4(R.drawable.day_4, 4), DAY_5(R.drawable.day_5, 5), DAY_6(R.drawable.day_6, 6),
    DAY_7(R.drawable.day_7, 7), DAY_8(R.drawable.day_8, 8), DAY_9(R.drawable.day_9, 9),
    DAY_10(R.drawable.day_10, 10), DAY_11(R.drawable.day_11, 11), DAY_12(R.drawable.day_12, 12),
    DAY_13(R.drawable.day_13, 13), DAY_14(R.drawable.day_14, 14), DAY_15(R.drawable.day_15, 15),
    DAY_16(R.drawable.day_16, 16), DAY_17(R.drawable.day_17, 17), DAY_18(R.drawable.day_18, 18),
    DAY_19(R.drawable.day_19, 19), DAY_20(R.drawable.day_20, 20), DAY_21(R.drawable.day_21, 21),
    DAY_22(R.drawable.day_22, 22), DAY_23(R.drawable.day_23, 23), DAY_24(R.drawable.day_24, 24),
    DAY_25(R.drawable.day_25, 25), DAY_26(R.drawable.day_26, 26), DAY_27(R.drawable.day_27, 27),
    DAY_28(R.drawable.day_28, 28), DAY_29(R.drawable.day_29, 29), DAY_30(R.drawable.day_30, 30),
    DAY_31(R.drawable.day_31, 31);
    /**
     * 图片
     */
    private int drawableRes;
    /**
     * 序号
     */
    private int index;
    CalendarDayImg(int drawableRes, int index) {
        this.drawableRes = drawableRes;
        this.index = index;
    }
    public static int getDrawableRes(int index) {
        for (CalendarDayImg calendarDayImg : CalendarDayImg.values()) {
            if (calendarDayImg.getIndex() == index) {
                return calendarDayImg.drawableRes;
            }
        }
        return 0;
    }
    public int getDrawableRes() {
        return drawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        this.drawableRes = drawableRes;
    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

}
