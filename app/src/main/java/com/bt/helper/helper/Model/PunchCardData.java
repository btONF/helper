package com.bt.helper.helper.Model;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haibin.calendarview.Calendar;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.converter.PropertyConverter;

/**
 * Created by 18030693 on 2018/8/16.
 */

@Entity
public class PunchCardData {

    @Id
    private long id;
    @Index
    private int year;
    @Index
    private int month;

    @Index
    private int day;


    private long onDutyTimeLong;
    private long offDutyTimeLong;

    @Convert(converter = CalendarConverter.class, dbType = String.class)
    private Calendar calendar;

    public PunchCardData() {
        calendar = new Calendar();
    }
    public PunchCardData(int year, int month, int day, long onDutyTimeLong, long offDutyTimeLong, Calendar calendar) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.onDutyTimeLong = onDutyTimeLong;
        this.offDutyTimeLong = offDutyTimeLong;
        this.calendar = calendar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public long getOnDutyTimeLong() {
        return onDutyTimeLong;
    }

    public void setOnDutyTimeLong(long onDutyTimeLong) {
        this.onDutyTimeLong = onDutyTimeLong;
    }

    public long getOffDutyTimeLong() {
        return offDutyTimeLong;
    }

    public void setOffDutyTimeLong(long offDutyTimeLong) {
        this.offDutyTimeLong = offDutyTimeLong;
    }
    public static class CalendarConverter implements PropertyConverter<Calendar, String> {
        @Override
        public Calendar convertToEntityProperty(String databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            Gson gson = new Gson();
            Calendar calendar = gson.fromJson(databaseValue, new TypeToken<Calendar>() {}.getType());
            return calendar;
        }
        @Override
        public String convertToDatabaseValue(Calendar entityProperty) {
            Gson gson = new Gson();
            return entityProperty == null ? null : gson.toJson(entityProperty);
        }
    }

}
