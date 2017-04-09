package com.example.lcc.mykitchen.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lcc on 2016/12/17.
 */
public class dataUtils {
    public static String formateData(Long time){
        SimpleDateFormat sdformat=new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        String  dataStr= sdformat.format(new Date(time));
        return dataStr;
    }
}
