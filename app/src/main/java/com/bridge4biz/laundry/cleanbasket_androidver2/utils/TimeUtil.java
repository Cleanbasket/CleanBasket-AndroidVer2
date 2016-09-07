package com.bridge4biz.laundry.cleanbasket_androidver2.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gingeraebi on 2016. 7. 1..
 */
public class TimeUtil {

    /*
        Date : 날짜 (xx월 xx일)
        month : 월 (xx월)
     */

    private SimpleDateFormat titleFormat;
    private SimpleDateFormat dbFormat;
    private SimpleDateFormat prettyFormat;
    private SimpleDateFormat dateFormat;

    public TimeUtil() {
        titleFormat = new SimpleDateFormat("MM월dd일(E)");
        dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:00.0");
        prettyFormat = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public Calendar getTodayCalendar() {
        return Calendar.getInstance();
    }

    public Date dbFormedStringToPrettyFormedDate(String dbFormatString) {
        try {
            Date date = dbFormat.parse(dbFormatString);
            date.setYear(getYearFromDate(date) - 1900);

            return date;
        } catch (ParseException e) {
            Log.e("데이트 파싱 안됨 에러", "" + e);
            return null;
        }
    }

    public String dbFormatStringToPrettyFormatString(String dbFormatString) {
        Date date = dbFormedStringToPrettyFormedDate(dbFormatString);
        return prettyFormat.format(date);
    }

    public Calendar getTwoAfterDayFromCalenarFromToday() {
        Calendar calender = getTodayCalendar();
        calender.add(Calendar.DATE, 2);
        return calender;
    }

    public String getTwoAfterDayFromTodayFormedTitle() {
        Calendar calendar = getTwoAfterDayFromCalenarFromToday();
        Date date = convertCalendarToDate(calendar);
        return titleFormat.format(date);
    }

    public String getTwoAfterDayFormedTitle(String stringDate) {
        Calendar calendar = getTwoDayAfterCalender(stringDate);

        Date resultDate = calendar.getTime();
        return titleFormat.format(resultDate);

    }

    public Calendar getTwoDayAfterCalender(String stringDate) {
        Date date = dbFormedDateToDate(stringDate);
        Calendar calendar = getTodayCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 2);
        return calendar;
    }

    private Date dbFormedDateToDate(String stringDate) {
        try {
            Date date = dbFormat.parse(stringDate);
            date.setYear(getYearFromDate(date) - 1900);
            return date;
        } catch (Exception e) {
            Log.e("파싱에러", e.getMessage());
            return null;
        }
    }

    public Calendar getThreeAfterDayCalendarFromToday() {
        Calendar calender = getTodayCalendar();
        calender.add(Calendar.DATE, 3);
        return calender;
    }

    public Calendar getThreeAfterDayCalendarFromDate(Date date) {
        Calendar calender = getTodayCalendar();
        calender.setTime(date);
        calender.add(Calendar.DATE, 3);
        return calender;
    }

    public Date convertCalendarToDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        return new Date(year - 1900, month, day, hour, 0);
    }

    //오늘 날짜 정보
    public String getTodayString() {
        Calendar calendar = getTodayCalendar();
        Date date = convertCalendarToDate(calendar);
        return titleFormat.format(date);
    }

    //들어온 날 이후 6일 합쳐서 1주일 날짜 정보
    public ArrayList<String> getWeekAsStringStartAt(Calendar calendar) {
        ArrayList<String> weekDates = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DATE, 1);
            if(calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                continue;
            }
            Date date = convertCalendarToDate(calendar);
            weekDates.add(titleFormat.format(date));
        }
        return weekDates;
    }

    public Date titleFormedStringToDate(String titleFormedDate) {
        try {
            Date date = titleFormat.parse(titleFormedDate);
            date.setYear(getYearFromDate(date) - 1900);
            return date;
        } catch (ParseException e) {
            Log.e("데이트 파싱 안됨 에러", "" + e);
            return null;
        }
    }

    private int getYearFromDate(Date dateFormedDate) {
        Calendar calendar = getTodayCalendar();
        if (dateFormedDate.getMonth() == 1 && calendar.get(Calendar.MONTH) == 11) {
            return calendar.get(Calendar.YEAR) + 1;
        } else {
            return calendar.get(Calendar.YEAR);
        }
    }

    public String titleFormedStringToDateFormedDateString(String titleFormedString) {
        Date date = titleFormedStringToDate(titleFormedString);
        return dateFormat.format(date);
    }

    public String getThreeAfterStringFromTitleFormedString(String titleFormedString) {
        Date date = titleFormedStringToDate(titleFormedString);
        Calendar calendar = getThreeAfterDayCalendarFromDate(date);
        Date resultDate = convertCalendarToDate(calendar);
        return dateFormat.format(resultDate);
    }

    public String getDbFormedDefaultPickupTime() {
        Date date = convertCalendarToDate(getDefaultPickUpCal());
        int hour = date.getHours();
        return dbFormat.format(date);
    }

    public String getDbFormedDefaultDropOffTime() {
        Calendar calendar = getDefaultPickUpCal();
        calendar.add(Calendar.DATE, 3);
        Date date = convertCalendarToDate(calendar);
        return dbFormat.format(date);
    }

    private Calendar getDefaultPickUpCal() {
        Calendar calendar = getTodayCalendar();
        calendar.add(Calendar.HOUR_OF_DAY, 3);

        //2시간 후가 23시 이후일 경우 다음날 10시로 설정
        if (calendar.get(Calendar.HOUR_OF_DAY) > 23) {
            calendar.add(Calendar.DATE, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 10);
        }

        //2시간 후가 10시 이전일 경우 10시로 설정
        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
            calendar.set(Calendar.HOUR_OF_DAY, 10);
        }

        return calendar;
    }

}
