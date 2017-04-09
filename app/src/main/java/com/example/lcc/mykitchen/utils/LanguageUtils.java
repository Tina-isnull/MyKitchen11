package com.example.lcc.mykitchen.utils;

import java.util.Locale;

/**
 * Created by lcc on 16/6/15.
 */
public class LanguageUtils {
    public static String getCurrentLanguage() {
        Locale l = Locale.getDefault();
        return l.getLanguage() + "_" + l.getCountry();
    }
}
