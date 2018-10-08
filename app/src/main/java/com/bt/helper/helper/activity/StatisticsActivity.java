package com.bt.helper.helper.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bt.helper.helper.Model.PunchCardData;
import com.bt.helper.helper.Model.PunchCardData_;
import com.bt.helper.helper.R;
import com.bt.helper.helper.util.AppUtil;
import com.bt.helper.helper.util.DateUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.objectbox.Box;

/**
 * Created by 18030693 on 2018/8/24.
 */

public class StatisticsActivity extends Activity implements View.OnClickListener{
    private LineChart mLineChart;
    private Box<PunchCardData> punchBox;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mLineChart = findViewById(R.id.line_chart);
    }

    private void initData() {
        punchBox = AppUtil.getBoxStore().boxFor(PunchCardData.class);
        mLineChart.setDrawBorders(false);
        mLineChart.setNoDataText("无数据");
        List<PunchCardData> list = punchBox.query().equal(PunchCardData_.year, 2018)
                .equal(PunchCardData_.month, 10).build().find();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, 10 - 1);
        calendar.set(Calendar.DATE, 1);
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (list == null || list.isEmpty()){
            mLineChart.setData(null);
        } else {
            List<Entry> entries = new ArrayList<>();
            for (int i = 0; i < day; i++) {
                entries.add(new Entry(i, 0));
            }
            for (int i = 0; i < list.size(); i++) {
                entries.set(list.get(i).getDay(), new Entry(list.get(i).getDay(),
                        DateUtils.getInstance().getHourFloatByLongTime(list.get(i).getDay(), list.get(i).getOnDutyTimeLong())));
            }
            //一个LineDataSet就是一条线
            LineDataSet lineDataSet = new LineDataSet(entries, "上班时间");
            //线颜色
            lineDataSet.setColor(Color.parseColor("#F15A4A"));
            //线宽度
            lineDataSet.setLineWidth(2f);
            //不显示圆点
            lineDataSet.setDrawCircles(true);
            //线条平滑
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            LineData data = new LineData(lineDataSet);
            //折线图不显示数值
            data.setDrawValues(true);
            //得到X轴
            XAxis xAxis = mLineChart.getXAxis();
            //设置X轴的位置（默认在上方)
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            //设置X轴坐标之间的最小间隔
            xAxis.setGranularity(1f);
            //设置X轴的刻度数量，第二个参数为true,将会画出明确数量（带有小数点），但是可能值导致不均匀，默认（6，false）
            xAxis.setLabelCount(day /5, true);
            //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
            xAxis.setAxisMinimum(1);
            xAxis.setAxisMaximum(day);
            //不显示网格线
            xAxis.setDrawGridLines(false);
            // 标签倾斜
            //xAxis.setLabelRotationAngle(45);
            //设置X轴值为字符串
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return  (int)value + "日";
                }
            });

            //得到Y轴
            YAxis yAxis = mLineChart.getAxisLeft();
            YAxis rightYAxis = mLineChart.getAxisRight();
            //设置Y轴是否显示
            rightYAxis.setEnabled(false); //右侧Y轴不显示
            //设置y轴坐标之间的最小间隔
            //+2：最大值n就有n+1个刻度，在加上y轴多一个单位长度，为了好看，so+2
            yAxis.setLabelCount(12, false);
            //不显示网格线
            yAxis.setDrawGridLines(false);
            //设置Y轴坐标之间的最小间隔
            yAxis.setGranularity(1);
            //设置y轴的刻度数量
            //设置从Y轴值
            yAxis.setAxisMinimum(0);
            yAxis.setAxisMaximum(36);
            yAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                if ((int)value < 25){
                    return (int)value + "点";
                } else {
                    int newValue = (int)value - 24;
                    return "次日" + newValue + "点";

                }


                }
            });
            mLineChart.setData(data);
        }

    }

    private void initListener() {

    }

    @Override
    public void onClick(View view) {

    }
}
