package com.example.helper;


import com.example.document.Teacher;

public class RedisHelper {
    public static String generateKey(String teacher,String functionName) {
        return teacher+"::"+functionName;
    }
}