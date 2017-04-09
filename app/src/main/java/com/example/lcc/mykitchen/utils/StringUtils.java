package com.example.lcc.mykitchen.utils;

import android.graphics.BitmapFactory;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.example.lcc.mykitchen.utils.sharemultiphoto.DisplayUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static SpannableString getEmotionContent(final Context context, final TextView tv, String source) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();

        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);
        final String currentLanguage = LanguageUtils.getCurrentLanguage();
        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = EmotionUtils.getImgByName(key);
            if (imgRes != -1) {
                // 压缩表情图片
                //   int size = (int) tv.getTextSize()+20 ;
                int size = DisplayUtils.dp2px(context, 20);
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                Bitmap scaleBitmap;
                if ("[ht]".equals(key)) {
                    //热门的id值
                    //判断中英文
                    if (currentLanguage.equals("zh_CN")) {
                        //热门评论
                        int width = DisplayUtils.dp2px(context, 56);
                        int height = DisplayUtils.dp2px(context, 18);
                        scaleBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    } else {
                        //hot
                        int width = DisplayUtils.dp2px(context, 34);
                        int height = DisplayUtils.dp2px(context, 18);
                        scaleBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    }
                } else {
                    //表情
                    scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
                }

                ImageSpan span = new ImageSpan(context, scaleBitmap, ImageSpan.ALIGN_BOTTOM);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return spannableString;
    }

    public static SpannableString getEmotionContent(final Context context, final TextView tv, SpannableString source) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();

        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);
        final String currentLanguage = LanguageUtils.getCurrentLanguage();
        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = EmotionUtils.getImgByName(key);
            if (imgRes != -1) {
                // 压缩表情图片
                //  int size = (int) tv.getTextSize();
                int size = DisplayUtils.dp2px(context, 18);
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                Bitmap scaleBitmap;
                if ("[出品人]".equals(key)) {
                    //热门的id值
                    //判断中英文
                    if (currentLanguage.equals("zh_CN")) {
                        //出品人
                        int width = DisplayUtils.dp2px(context, 36);
                        int height = DisplayUtils.dp2px(context, 14);
                        scaleBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    } else {
                        //chef
                        int width = DisplayUtils.dp2px(context, 29);
                        int height = DisplayUtils.dp2px(context, 14);
                        scaleBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    }
                } else {
                    //表情
                    scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
                }

                ImageSpan span = new ImageSpan(context, scaleBitmap, ImageSpan.ALIGN_BOTTOM);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }


    /**
     * @param context 上下文
     * @param tv      TextView
     * @param source  SpannableString资源
     * @param type    回复的类型  例如  出品人   题问人  楼主
     * @return 组装好的SpannableString
     */
    public static SpannableString getMultityEmotionContent(final Context context, final TextView tv, SpannableString source, String type) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();

        String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);
        final String currentLanguage = LanguageUtils.getCurrentLanguage();
        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = EmotionUtils.getImgByName(key);
            if (imgRes != -1) {
                // 压缩表情图片
                //  int size = (int) tv.getTextSize();
                int size = DisplayUtils.dp2px(context, 18);
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
                Bitmap scaleBitmap;
                String keyString = "[" + type + "]";
                if (keyString.equals(key)) {
                    //热门的id值
                    //判断中英文
                    if (currentLanguage.equals("zh_CN")) {
                        //出品人
                        int width = DisplayUtils.dp2px(context, 36);
                        int height = DisplayUtils.dp2px(context, 14);
                        scaleBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    } else {
                        //chef
                        int width = DisplayUtils.dp2px(context, 29);
                        int height = DisplayUtils.dp2px(context, 14);
                        scaleBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                    }
                } else {
                    //表情
                    scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
                }

                ImageSpan span = new ImageSpan(context, scaleBitmap, ImageSpan.ALIGN_BOTTOM);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannableString;
    }


}


