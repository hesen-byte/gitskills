package com.hesen.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Encrypt
{
    public static String md5(String str, String salt)
    {
        return new Md5Hash(str, salt).toString();
    }
}
