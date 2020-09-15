package com.hesen.utils;

public class StringUtils
{
    public static String formatLike(String str)
    {
        if (isNotEmpty(str)) return "%" + str + "%";
        else return null;
    }

    public static boolean isNotEmpty(String str)
    {
        if (str != null && !"".equals(str.trim())) return true;
        return false;
    }

    public static boolean isEmpty(String str)
    {
        return str == null || "".equals(str);
    }
}
