package com.example.libraryapp.util;

import java.util.Calendar;

public class MyDate {
    public static String getDate() {
        Calendar cal = Calendar.getInstance(); //singleton 방식
        int yy = cal.get(Calendar.YEAR);
        int mm = cal.get(Calendar.MONTH) + 1; //0 ~ 11 이므로 +1
        int dd = cal.get(Calendar.DATE);
        int hh = cal.get(Calendar.HOUR_OF_DAY);
        int ms = cal.get(Calendar.MINUTE);
        int ss = cal.get(Calendar.SECOND);

        return String.format("%4d-%02d-%02d %02d:%02d:%02d", yy, mm, dd, hh, ms, ss); //날짜 담기
    }
}
