package com.mehboob.hunzarider.utils;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ConvertMilliToDate {


    public static String getMyPrettyDate(long neededTimeMilis) {
        Calendar nowTime = Calendar.getInstance();
        Calendar neededTime = Calendar.getInstance();
        neededTime.setTimeInMillis(neededTimeMilis);

        if ((neededTime.get(Calendar.YEAR) == nowTime.get(Calendar.YEAR))) {

            if ((neededTime.get(Calendar.MONTH) == nowTime.get(Calendar.MONTH))) {

                if (neededTime.get(Calendar.DATE) - nowTime.get(Calendar.DATE) == 1) {
                    //here return like "Tomorrow at 12:00"
                    return "Tomorrow at " + DateFormat.format("HH:mm", neededTime);

                } else if (nowTime.get(Calendar.DATE) == neededTime.get(Calendar.DATE)) {
                    //here return like "Today at 12:00"
                    return "Today at " + DateFormat.format("HH:mm", neededTime);

                } else if (nowTime.get(Calendar.DATE) - neededTime.get(Calendar.DATE) == 1) {
                    //here return like "Yesterday at 12:00"
                    return "Yesterday at " + DateFormat.format("HH:mm", neededTime);

                } else {
                    //here return like "May 31, 12:00"
                    return DateFormat.format("MMMM d, HH:mm", neededTime).toString();
                }

            } else {
                //here return like "May 31, 12:00"
                return DateFormat.format("MMMM d, HH:mm", neededTime).toString();
            }

        } else {
            //here return like "May 31 2010, 12:00" - it's a different year we need to show it
            return DateFormat.format("MMMM dd yyyy, HH:mm", neededTime).toString();
        }
    }
}
