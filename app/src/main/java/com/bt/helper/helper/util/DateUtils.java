package com.bt.helper.helper.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 18030693 on 2018/8/17.
 */

public class DateUtils {
    private DateUtils() {}
    private static DateUtils dateUtils;
    public static DateUtils getInstance(){
        if(dateUtils == null){
            synchronized(DateUtils.class) {
                if(dateUtils == null) {
                    dateUtils = new DateUtils();
                }
            }
        }
        return dateUtils;
    }
    /**
     * 获取当前时分秒时间
     * @return
     */
    public String getCurTime(){
        Date date = new Date(System.currentTimeMillis());
        return getTimeStrByDate(date);
    }



    /**
     * 获取时分秒时间
     * @return
     */
    public String getTimeStrByDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH : mm : ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取时分秒对应的calendar
     * @param time
     * @return
     */
    public Calendar getCalendarByTime(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(time);
        return calendar;

    }

    /**
     * @return
     */
    public long getTimeLong(int year, int month, int day, int hour, int minute, int second){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year,month -1 ,day,hour,minute,second);

        return calendar.getTimeInMillis();
    }

    public String getTimeStrByLongTime(int day, long time){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(time);
        Date date = new Date(time);
        SimpleDateFormat dateFormat;
        if (day == calendar.get(Calendar.DAY_OF_MONTH)){
            dateFormat = new SimpleDateFormat("HH : mm : ss");
        } else {
            dateFormat= new SimpleDateFormat("dd日 HH : mm : ss");
        }
        return dateFormat.format(date);
    }

    public float getHourFloatByLongTime(int day, long time){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(time);
        int dayOffset = calendar.get(Calendar.DATE) - day;
        float hour = dayOffset * 24 + calendar.get(Calendar.HOUR_OF_DAY) + calendar.get(Calendar.MINUTE) / 60.0f;
        return hour;
    }
}
