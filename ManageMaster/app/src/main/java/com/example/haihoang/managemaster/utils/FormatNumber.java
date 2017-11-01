package com.example.haihoang.managemaster.utils;

import android.content.Intent;

/**
 * Created by Trần_Tân on 01/11/2017.
 */

public class FormatNumber {
    public static String formatNumber(int number){
        StringBuilder s = new StringBuilder(number+"");
        int length = s.length();
        length -= 3;
        while(length >=2){
            s.insert(length,".");
            length-=3;
        }

        return s.toString();
    }
    public static int getNumber(String number){
        StringBuilder s = new StringBuilder(number);
        while (s.indexOf(".")>0){
            s.deleteCharAt(s.indexOf("."));
        }
        return Integer.parseInt(s.toString());
    }
}
