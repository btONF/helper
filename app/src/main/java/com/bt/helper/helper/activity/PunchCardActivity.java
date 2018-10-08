package com.bt.helper.helper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bt.helper.helper.Model.PunchCardData;
import com.bt.helper.helper.Model.PunchCardData_;
import com.bt.helper.helper.R;
import com.bt.helper.helper.util.AppUtil;
import com.haibin.calendarview.CalendarView;

import java.util.Calendar;
import java.util.Date;

import io.objectbox.Box;

/**
 * Created by 18030693 on 2018/8/19.
 */

public class PunchCardActivity extends Activity implements View.OnClickListener{
    private TextView mOnDutyTv;
    private TextView mOffDutyTv;
    private TextView mYesterdayOffDutyTv;


    private Box<PunchCardData> punchBox;
    private CalendarView calendarView;
    private PunchCardData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.punch_card_activity);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mOnDutyTv = findViewById(R.id.punch_on_duty);
        mOffDutyTv = findViewById(R.id.punch_off_duty);
        calendarView = new CalendarView(this);
        mYesterdayOffDutyTv = findViewById(R.id.yesterday_off_duty);
    }

    private void initData() {
        punchBox = AppUtil.getBoxStore().boxFor(PunchCardData.class);
        Calendar calendar = Calendar.getInstance();
        data = queryByDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        setYesterdayStatus();

    }

    private void initListener() {
        mOnDutyTv.setOnClickListener(this);
        mOffDutyTv.setOnClickListener(this);
        mYesterdayOffDutyTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.punch_on_duty:
                if (data == null){
                    com.haibin.calendarview.Calendar calendar = calendarView.getSelectedCalendar();
                    data = new PunchCardData(calendar.getYear(), calendar.getMonth(), calendar.getDay(),
                            new Date().getTime(), 0, calendar);
                    punchBox.put(data);
                    Toast.makeText(this,"签到成功",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"已签到,无需重复签到",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.punch_off_duty:
                if (data == null){
                    Toast.makeText(this,"未签到,请先签到",Toast.LENGTH_SHORT).show();
                } else if (data.getOffDutyTimeLong() <= 0){
                    data.setOffDutyTimeLong(new Date().getTime());
                    punchBox.put(data);
                    Toast.makeText(this,"签退成功",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"已签退,无需重复签退",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.yesterday_off_duty:
                Calendar calendar = Calendar.getInstance();
                calendar.add(calendar.DATE, -1);
                PunchCardData yesterdayData = queryByDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
                yesterdayData.setOffDutyTimeLong(new Date().getTime());
                punchBox.put(yesterdayData);
                mYesterdayOffDutyTv.setVisibility(View.GONE);
                Toast.makeText(this,"昨日签退成功",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

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

    public void setYesterdayStatus(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, -1);
        PunchCardData data = queryByDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) +1, calendar.get(Calendar.DAY_OF_MONTH));
        if (data != null && data.getOnDutyTimeLong() > 0 && data.getOffDutyTimeLong() <= 0){
            mYesterdayOffDutyTv.setVisibility(View.VISIBLE);
        } else {
            mYesterdayOffDutyTv.setVisibility(View.GONE);
        }
    }
}
