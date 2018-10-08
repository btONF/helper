package com.bt.helper.helper.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bt.helper.helper.Model.PunchCardData;
import com.bt.helper.helper.Model.PunchCardData_;
import com.bt.helper.helper.R;
import com.bt.helper.helper.util.AppUtil;
import com.bt.helper.helper.util.DateUtils;
import com.bt.helper.helper.view.calendar.CalendarDayImg;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.objectbox.Box;

/**
 * Created by 18030693 on 2018/8/14.
 */

public class PunchCardDataActivity extends Activity implements View.OnClickListener{
    private static final String TAG = PunchCardDataActivity.class.getSimpleName();
    private static final int TODAY =1;
    private static final int DAY_BEFORE_TODAY = 2;
    private static final int DAY_AFTER_TODAY = 3;

    private static final int ON_TIME = 1;
    private static final int LATE = 2;
    private static final int EARLY = 3;
    private static final int NO_DATA = 4;

    /**
     * 日历视图区域
     */
    private CalendarLayout mCalendarLayout;
    private CalendarView mCalendarView;

    /**
     * 顶部视图区域
     */
    private TextView mYearTv;
    private TextView mMonthTv;
    private TextView mDayTv;
    private ImageView mTodayIv;

    /**
     * 本地存储当前展示日期
     */
    public int curShowYear;
    public int curShowMonth;
    public int curShowDay;

    /**
     * 数据库操作
     */
    private Box<PunchCardData> punchBox;

    /**
     * 打卡区域
     */

    private TextView mOnDutyTitleTv;
    private TextView mOnDutyTv;
    private TextView mOnDutyStatusTv;
    private TextView mOffDutyTitleTv;
    private TextView mOffDutyTv;
    private TextView mOffDutyStatusTv;
    private TextView mDeletePunchDataTv;

    private PunchCardData punchCardData;
    private TimePickerView pvTime;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.punch_card_data_activity);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mCalendarLayout = findViewById(R.id.calendar_layout);
        mCalendarView = findViewById(R.id.calendar_view);

        mYearTv = findViewById(R.id.year);
        mMonthTv = findViewById(R.id.month);
        mDayTv = findViewById(R.id.day);
        mTodayIv = findViewById(R.id.today_img);


        mOnDutyTitleTv = findViewById(R.id.on_duty_title);
        mOnDutyTv = findViewById(R.id.on_duty_date);
        mOnDutyStatusTv = findViewById(R.id.on_duty_status);
        mOffDutyTitleTv = findViewById(R.id.off_duty_title);
        mOffDutyTv = findViewById(R.id.off_duty_date);
        mOffDutyStatusTv = findViewById(R.id.off_duty_status);

        mDeletePunchDataTv = findViewById(R.id.delete_punch_data);

    }

    private void initData() {
        punchBox = AppUtil.getBoxStore().boxFor(PunchCardData.class);
        setCalendarTopView(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), mCalendarView.getCurDay());
        showSchema();
        mTodayIv.setImageResource(CalendarDayImg.getDrawableRes(mCalendarView.getCurDay()));

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v){
                Calendar calendar = mCalendarView.getSelectedCalendar();
                if (v.getId() == R.id.on_duty_date){
                    setPunchData(calendar.getYear(), calendar.getMonth(), calendar.getDay(),
                            date.getTime(),0, calendar);
                } else if (v.getId() == R.id.off_duty_date){
                    setPunchData(calendar.getYear(), calendar.getMonth(), calendar.getDay(),
                            0, date.getTime(), calendar);
                }

            }
        }).setType(new boolean[]{false, false, true, true, true, true})
                .setCancelText("取消")
                .setSubmitText("确认")
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("打卡时间")
                .setOutSideCancelable(true)
                .isCyclic(false)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(0xFFFFFFFF)
                .setBgColor(0xFFFFFFFF)
                .setLineSpacingMultiplier(2.0f)
                .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false)
                .isDialog(false)
                .build();
    }

    private void initListener() {
        mCalendarView.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Calendar calendar, boolean isClick) {
                setCalendarTopView(calendar.getYear(), calendar.getMonth(), calendar.getDay());
                updateData();
            }
        });
        mCalendarView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                // 非当月不展示'日'
                setCalendarTopView(year, month,
                        mCalendarView.getSelectedCalendar().getMonth() == month ? mCalendarView.getSelectedCalendar().getDay() : 0);
                showSchema();
            }
        });
        mCalendarView.setOnYearChangeListener(new CalendarView.OnYearChangeListener() {
            @Override
            public void onYearChange(int year) {
                // 年份切换时仅改变文案,不对数据进行存储
                // 若选择了月份将会触发月份改变监听,若直接返回将显示之前保存数据
                mYearTv.setText(String.valueOf(year));
            }
        });
        mTodayIv.setOnClickListener(this);
        mYearTv.setOnClickListener(this);
        mMonthTv.setOnClickListener(this);
        mDayTv.setOnClickListener(this);
        mOnDutyTv.setOnClickListener(this);
        mOffDutyTv.setOnClickListener(this);
        mDeletePunchDataTv.setOnClickListener(this);
    }

    public void setCalendarTopView(int year, int month, int day) {
        setCalendarTopView(year, month, day, false);
    }
    public void setCalendarTopView(int year, int month, int day, boolean isSelectYear){
        mYearTv.setText(year == 0 ? "" : String.valueOf(year));
        mMonthTv.setText(month == 0 ? "" : getDateString(month));
        mDayTv.setText(day == 0 ? "" : getDateString(day));
        if (!isSelectYear) {
            saveDate(year, month, day);
        }
    }

    public String getDateString(int date){
        if (date < 10){
            return "0" + String.valueOf(date);
        } else {
            return String.valueOf(date);
        }
    }

    public void saveDate(int year, int month, int day){
        curShowYear = year;
        curShowMonth = month;
        curShowDay = day;
    }

    @Override
    public void onClick(View view) {
        Calendar selectCalendar = mCalendarView.getSelectedCalendar();

        int id = view.getId();
        switch (id){
            case R.id.today_img:
                mCalendarView.scrollToCurrent(true);
                break;
            case R.id.year:
            case R.id.month:
            case R.id.day:

                if (mCalendarView.isYearSelectLayoutVisible()) {
                    // 年份选择页展开时,点击重新显示'月','日',年份选择缩回
                    setCalendarTopView(curShowYear, curShowMonth, curShowDay);
                    mCalendarView.closeYearSelectLayout();
                } else if (mCalendarLayout.isExpand()){
                    // 月份选择展开时,点击隐藏'月','日',年份选择展开(此时只改变UI不改变本地数据)
                    mCalendarView.showYearSelectLayout(curShowYear);
                    setCalendarTopView(curShowYear, 0, 0, true);
                } else {
                    // 月份选择展开
                    mCalendarLayout.expand();
                }

                break;
            case R.id.on_duty_date:
                showDataPicker(mOnDutyTv);
                break;
            case R.id.off_duty_date:
                showDataPicker(mOffDutyTv);
                break;
            case R.id.delete_punch_data:
                deleteData(selectCalendar.getYear(), selectCalendar.getMonth(), selectCalendar.getDay());
                updateData();
                showSchema();
            default:
                break;
        }

    }



    @Override
    public void onBackPressed() {
        // 为优化体验,年份选择展开时,点击回退时会先缩回
        if (mCalendarView.isYearSelectLayoutVisible()) {
            setCalendarTopView(curShowYear, curShowMonth, curShowDay);
            mCalendarView.closeYearSelectLayout();
            return;
        }
        super.onBackPressed();
    }


    public void updateData(){
        Calendar calendar = mCalendarView.getSelectedCalendar();
        punchCardData =  queryByDate(calendar.getYear(), calendar.getMonth(), calendar.getDay());
        showByPunchData(punchCardData);
        setStyleByData(punchCardData, calendar);
    }

    public void setStyleByData(PunchCardData data, Calendar calendar){
        switch (dayStatus(calendar.getYear(), calendar.getMonth(), calendar.getDay())){
            case TODAY:
                if (data != null) {
                    if (data.getOnDutyTimeLong() > 0) {
                        mOnDutyTitleTv.setTextColor(getResources().getColor(R.color.green));
                    } else {
                        mOnDutyTitleTv.setTextColor(getResources().getColor(R.color.gray));
                    }
                    if (data.getOffDutyTimeLong() > 0) {
                        mOffDutyTitleTv.setTextColor(getResources().getColor(R.color.green));
                    } else {
                        mOffDutyTitleTv.setTextColor(getResources().getColor(R.color.gray));
                    }
                } else {
                    mOffDutyTitleTv.setTextColor(getResources().getColor(R.color.gray));
                    mOnDutyTitleTv.setTextColor(getResources().getColor(R.color.gray));
                }
                mOnDutyTv.setEnabled(true);
                mOffDutyTv.setEnabled(true);

                break;
            case DAY_AFTER_TODAY:
                mOnDutyTv.setEnabled(false);
                mOffDutyTv.setEnabled(false);
                mOnDutyTitleTv.setTextColor(getResources().getColor(R.color.gray));
                mOffDutyTitleTv.setTextColor(getResources().getColor(R.color.gray));
                break;
            case DAY_BEFORE_TODAY:
                if (data != null) {
                    if (data.getOnDutyTimeLong() <= 0) {
                        mOnDutyTitleTv.setTextColor(getResources().getColor(R.color.green));
                    } else {
                        mOnDutyTitleTv.setTextColor(getResources().getColor(R.color.gray));
                    }
                    if (data.getOffDutyTimeLong() <= 0) {
                        mOffDutyTitleTv.setTextColor(getResources().getColor(R.color.green));
                    } else {
                        mOffDutyTitleTv.setTextColor(getResources().getColor(R.color.gray));
                    }
                } else {
                    mOnDutyTitleTv.setTextColor(getResources().getColor(R.color.gray));
                    mOffDutyTitleTv.setTextColor(getResources().getColor(R.color.gray));
                }
                mOnDutyTv.setEnabled(true);
                mOffDutyTv.setEnabled(true);
                break;
            default:
                break;

        }


    }

    /**
     * 根据 上下班时间加载数据
     * @param data
     */
    private void showByPunchData(PunchCardData data){
        if (data != null && data.getOnDutyTimeLong() > 0){
            mOnDutyTv.setText(DateUtils.getInstance().getTimeStrByLongTime(data.getDay(), data.getOnDutyTimeLong()));
            if (data.getOnDutyTimeLong() >
                    DateUtils.getInstance().getTimeLong(data.getYear(),data.getMonth(),data.getDay(),9,0,0)){
                setPunchStatusStyle(mOnDutyStatusTv, LATE);
            } else {
                setPunchStatusStyle(mOnDutyStatusTv, ON_TIME);
            }
        } else {
            mOnDutyTv.setText("00 : 00 : 00");
            setPunchStatusStyle(mOnDutyStatusTv, NO_DATA);
        }

        if (data != null && data.getOffDutyTimeLong() > 0){
            mOffDutyTv.setText(DateUtils.getInstance().getTimeStrByLongTime(data.getDay(), data.getOffDutyTimeLong()));
            if (data.getOffDutyTimeLong() <
                    DateUtils.getInstance().getTimeLong(data.getYear(),data.getMonth(),data.getDay(),20,0,0)){
                setPunchStatusStyle(mOffDutyStatusTv, EARLY);
            } else {
                setPunchStatusStyle(mOffDutyStatusTv, ON_TIME);
            }
        } else {
            setPunchStatusStyle(mOffDutyStatusTv, NO_DATA);
            mOffDutyTv.setText("00 : 00 : 00");
        }

        mDeletePunchDataTv.setVisibility(data == null ? View.GONE : View.VISIBLE);

    }

    /**
     * 查找数据
     * @param year
     * @param month
     * @param day
     * @return
     */
    public PunchCardData queryByDate(int year, int month, int day){
        return punchBox.query().equal(PunchCardData_.year, year)
                .equal(PunchCardData_.month, month)
                .equal(PunchCardData_.day, day).build().findFirst();
    }


    public void deleteData(int year, int month, int day){
         punchBox.query().equal(PunchCardData_.year, year)
                .equal(PunchCardData_.month, month)
                .equal(PunchCardData_.day, day).build().remove();
    }
    /**
     * 存储数据
     * @param year
     * @param month
     * @param day
     * @param onDutyDate
     * @param offDutyDate
     */
    private void setPunchData(int year, int month, int day, long onDutyDate, long offDutyDate, Calendar calendar) {
        PunchCardData data = queryByDate(year, month, day);
        if (data == null){
            data = new PunchCardData(year, month, day, onDutyDate, offDutyDate, calendar);
        } else {
            if (onDutyDate > 0){
                data.setOnDutyTimeLong(onDutyDate);
            }
            if (offDutyDate > 0){
                data.setOffDutyTimeLong(offDutyDate);
            }
        }
        punchBox.put(data);
        showSchema();
        updateData();

    }


    public void showDataPicker(View v){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        Calendar viewCalendar = mCalendarView.getSelectedCalendar();
        if (v.getId() == R.id.on_duty_date){
            if (punchCardData != null && punchCardData.getOnDutyTimeLong() > 0){
                calendar.setTimeInMillis(punchCardData.getOnDutyTimeLong());
            } else {
                calendar.clear();
                calendar.set(viewCalendar.getYear(),viewCalendar.getMonth() - 1,
                        viewCalendar.getDay(), 0, 0, 0);
            }
        } else if (v.getId() == R.id.off_duty_date){
            if (punchCardData != null && punchCardData.getOffDutyTimeLong() > 0){
                calendar.setTimeInMillis(punchCardData.getOffDutyTimeLong());
            } else {
                calendar.clear();
                calendar.set(viewCalendar.getYear(),viewCalendar.getMonth() - 1,
                        viewCalendar.getDay(), 0, 0, 0);
            }
        }
        pvTime.setDate(calendar);

        pvTime.show(v);
    }

    public int dayStatus(int year, int month, int day){
        if (year > mCalendarView.getCurYear()){
            return DAY_AFTER_TODAY;
        } else if (year < mCalendarView.getCurYear()){
            return DAY_BEFORE_TODAY;
        }

        if (month > mCalendarView.getCurMonth()){
            return DAY_AFTER_TODAY;
        } else if (month < mCalendarView.getCurMonth()){
            return DAY_BEFORE_TODAY;
        }

        if (day > mCalendarView.getCurDay()){
            return DAY_AFTER_TODAY;
        } else if (day < mCalendarView.getCurDay()) {
            return DAY_BEFORE_TODAY;
        }

        return TODAY;

    }

    public void showSchema(){

        List<PunchCardData> list = punchBox.query().equal(PunchCardData_.year, curShowYear)
                .equal(PunchCardData_.month, curShowMonth).build().find();
        Map<String, Calendar> map = new HashMap<>();
        for (PunchCardData data : list){
            map.put(data.getCalendar().toString(), data.getCalendar());
        }
        mCalendarView.setSchemeDate(map);
    }

    public void setPunchStatusStyle(TextView v,int status){
        switch (status){
            case ON_TIME:
                v.setBackground(getResources().getDrawable(R.drawable.punch_status_green));
                v.setText("已打卡");
                v.setTextColor(getResources().getColor(R.color.green_light));
                break;
            case LATE:
                v.setBackground(getResources().getDrawable(R.drawable.punch_status_orange));
                v.setText("迟到");
                v.setTextColor(getResources().getColor(R.color.orange_light));
                break;
            case EARLY:
                v.setBackground(getResources().getDrawable(R.drawable.punch_status_orange));
                v.setText("早退");
                v.setTextColor(getResources().getColor(R.color.orange_light));
                break;
            case NO_DATA:
                v.setBackground(getResources().getDrawable(R.drawable.punch_status_gray));
                v.setText("未打卡");
                v.setTextColor(getResources().getColor(R.color.gray_light));
                break;
            default:
                break;

        }
    }

}
