package com.wts.bharatsamachar.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SupportUtil {

    private static SimpleDateFormat dateFormat;

    public static CharSequence convertDate(String datetime){
        long timeInMillis = 0;
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);//.parse(datetime);
        }
        try {
            Date date = dateFormat.parse(datetime);
            if(date != null) {
                timeInMillis = date.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertTimeStamp(timeInMillis);
    }

    /**
     * @param mileSecond enter time in millis
     * @return Returns a string describing 'time' as a time relative to 'now'.
     * Time spans in the past are formatted like "42 minutes ago". Time spans in the future are formatted like "In 42 minutes".
     * i.e: 5 days ago, or 5 minutes ago.
     */
    public static CharSequence convertTimeStamp(long mileSecond){
        return DateUtils.getRelativeTimeSpanString(mileSecond, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }
}
