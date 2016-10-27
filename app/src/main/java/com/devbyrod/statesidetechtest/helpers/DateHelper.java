package com.devbyrod.statesidetechtest.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Mandrake on 8/17/16.
 */
public class DateHelper {

    public static String getDateWithFormat( String pDate, String format ) {

        Calendar cal = Calendar.getInstance();
//        TimeZone tz = cal.getTimeZone();
        TimeZone tz = TimeZone.getDefault();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
        Date date = new Date();
        String outputDate = date.toString();

        try {
            date = dateFormat.parse(pDate);
            dateFormat = new SimpleDateFormat(format);
            outputDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;
    }
}
