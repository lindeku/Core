package com.example.administrator.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/31 0031.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DateUtil {
    private String weekHead=null;
    private String weekTail=null;
    public DateUtil(){

    }
    public  Date getSky(Date date,int sky){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, sky);
        return calendar.getTime();
    }
    public Date getMonth(Date date,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public String getWeekHead() {
        return weekHead;
    }

    public String getWeekTail() {
        return weekTail;
    }

    public  Date  getWeek(Date date, int i){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayOfWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, 7*i);
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
        weekHead = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 6);

        if(calendar.getTime().after( Calendar.getInstance().getTime())){
            weekTail=sdf.format(Calendar.getInstance().getTime());
            return Calendar.getInstance().getTime();
        }else {
            weekTail = sdf.format(calendar.getTime());
            return  calendar.getTime();
        }


    }
}
