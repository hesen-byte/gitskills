package com.hesen.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{
    public static String getCurrentDateStr()
    {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(now);
    }

    public static String formatDate(Date date, String format)
    {
        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (date != null)
        {
            return result = sdf.format(date);
        }
        return result;
    }


}
