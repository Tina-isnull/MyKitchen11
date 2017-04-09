package com.example.lcc.mykitchen.utils;

import com.example.lcc.mykitchen.R;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;


import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;


public class EmotionUtils implements Serializable {

    /**
     * key-表情文字;value-表情图片资源
     */
    public static Map<String, Integer> emojiMap;

    static {
        emojiMap = new LinkedHashMap<String, Integer>();
        emojiMap.put("[微笑]", R.drawable.d_001);
        emojiMap.put("[撇嘴]", R.drawable.d_002);
        emojiMap.put("[色]", R.drawable.d_003);
        emojiMap.put("[发呆]", R.drawable.d_004);
        emojiMap.put("[得意]", R.drawable.d_005);
        emojiMap.put("[流泪]", R.drawable.d_006);
        emojiMap.put("[害羞]", R.drawable.d_007);
        emojiMap.put("[闭嘴]", R.drawable.d_008);
        emojiMap.put("[睡]", R.drawable.d_009);
        emojiMap.put("[大哭]", R.drawable.d_010);
        emojiMap.put("[尴尬]", R.drawable.d_011);
        emojiMap.put("[发怒]", R.drawable.d_012);
        emojiMap.put("[调皮]", R.drawable.d_013);
        emojiMap.put("[呲牙]", R.drawable.d_014);
        emojiMap.put("[惊讶]", R.drawable.d_015);
        emojiMap.put("[难过]", R.drawable.d_016);//===
        emojiMap.put("[酷]", R.drawable.d_017);
        emojiMap.put("[冷汗]", R.drawable.d_018);
        emojiMap.put("[抓狂]", R.drawable.d_019);
        emojiMap.put("[吐]", R.drawable.d_020);
        emojiMap.put("[偷笑]", R.drawable.d_021);
        emojiMap.put("[愉快]", R.drawable.d_022);
        emojiMap.put("[白眼]", R.drawable.d_023);
        emojiMap.put("[傲慢]", R.drawable.d_024);
        emojiMap.put("[饥饿]", R.drawable.d_025);
        emojiMap.put("[困]", R.drawable.d_026);
        emojiMap.put("[惊恐]", R.drawable.d_027);
        emojiMap.put("[流汗]", R.drawable.d_028);
        emojiMap.put("[憨笑]", R.drawable.d_029);
        emojiMap.put("[悠闲]", R.drawable.d_030);
        emojiMap.put("[奋斗]", R.drawable.d_031);
        emojiMap.put("[咒骂]", R.drawable.d_032);
        emojiMap.put("[疑问]", R.drawable.d_033);
        emojiMap.put("[嘘]", R.drawable.d_034);
        emojiMap.put("[晕]", R.drawable.d_035);
        emojiMap.put("[疯了]", R.drawable.d_036);
        emojiMap.put("[衰]", R.drawable.d_037);
        emojiMap.put("[骷髅]", R.drawable.d_038);
        emojiMap.put("[敲打]", R.drawable.d_039);
        emojiMap.put("[再见]", R.drawable.d_040);
        emojiMap.put("[擦汗]", R.drawable.d_041);//====
        emojiMap.put("[抠鼻]", R.drawable.d_042);
        emojiMap.put("[鼓掌]", R.drawable.d_043);
        emojiMap.put("[糗大了]", R.drawable.d_044);
        emojiMap.put("[坏笑]", R.drawable.d_045);
        emojiMap.put("[左哼哼]", R.drawable.d_046);
        emojiMap.put("[右哼哼]", R.drawable.d_047);
        emojiMap.put("[哈欠]", R.drawable.d_048);
        emojiMap.put("[鄙视]", R.drawable.d_049);
        emojiMap.put("[委屈]", R.drawable.d_050);
        emojiMap.put("[快哭了]", R.drawable.d_051);
        emojiMap.put("[阴险]", R.drawable.d_052);
        emojiMap.put("[亲亲]", R.drawable.d_053);
        emojiMap.put("[吓]", R.drawable.d_054);
        emojiMap.put("[可怜]", R.drawable.d_055);
        emojiMap.put("[菜刀]", R.drawable.d_056);
        emojiMap.put("[西瓜]", R.drawable.d_057);
        emojiMap.put("[啤酒]", R.drawable.d_058);
        emojiMap.put("[篮球]", R.drawable.d_059);
        emojiMap.put("[乒乓]", R.drawable.d_060);
        emojiMap.put("[咖啡]", R.drawable.d_061);
        emojiMap.put("[饭]", R.drawable.d_062);
        emojiMap.put("[猪头]", R.drawable.d_063);
        emojiMap.put("[玫瑰]", R.drawable.d_064);
        emojiMap.put("[凋谢]", R.drawable.d_065);
        emojiMap.put("[嘴唇]", R.drawable.d_066);
        emojiMap.put("[爱心]", R.drawable.d_067);
        emojiMap.put("[心碎]", R.drawable.d_068);
        emojiMap.put("[蛋糕]", R.drawable.d_069);
        emojiMap.put("[闪电]", R.drawable.d_070);
        emojiMap.put("[炸弹]", R.drawable.d_071);
        emojiMap.put("[刀]", R.drawable.d_072);
        emojiMap.put("[足球]", R.drawable.d_073);
        emojiMap.put("[瓢虫]", R.drawable.d_074);
        emojiMap.put("[便便]", R.drawable.d_075);
        emojiMap.put("[月亮]", R.drawable.d_076);
        emojiMap.put("[太阳]", R.drawable.d_077);
        emojiMap.put("[礼物]", R.drawable.d_078);
        emojiMap.put("[拥抱]", R.drawable.d_079);
        emojiMap.put("[强]", R.drawable.d_080);
        emojiMap.put("[弱]", R.drawable.d_081);
        emojiMap.put("[握手]", R.drawable.d_082);//==
        emojiMap.put("[胜利]", R.drawable.d_083);
        emojiMap.put("[抱拳]", R.drawable.d_084);
        emojiMap.put("[勾引]", R.drawable.d_085);
        emojiMap.put("[拳头]", R.drawable.d_086);
        emojiMap.put("[差劲]", R.drawable.d_087);
        emojiMap.put("[爱你]", R.drawable.d_088);
        emojiMap.put("[NO]", R.drawable.d_089);
        emojiMap.put("[OK]", R.drawable.d_090);
//        emojiMap.put("[爱情]", R.drawable.d_091);
//        emojiMap.put("[飞吻]", R.drawable.d_092);
//        emojiMap.put("[跳跳]", R.drawable.d_093);
//        emojiMap.put("[发抖]", R.drawable.d_094);
//        emojiMap.put("[恼火]", R.drawable.d_095);
//        emojiMap.put("[转圈]", R.drawable.d_096);
//        emojiMap.put("[磕头]", R.drawable.d_097);
//        emojiMap.put("[回头]", R.drawable.d_098);
//        emojiMap.put("[跳绳]", R.drawable.d_099);
//        emojiMap.put("[投降]", R.drawable.d_100);
        //  =======================================
//        emojiMap.put("[liuhan]", R.drawable.d_107);

        emojiMap.put("[笑脸]", R.drawable.dd_1);
        emojiMap.put("[生病]", R.drawable.dd_2);
        emojiMap.put("[破涕为笑]", R.drawable.dd_3);
        emojiMap.put("[吐舌]", R.drawable.dd_4);
        emojiMap.put("[emoji晕]", R.drawable.dd_5);
        emojiMap.put("[脸红]", R.drawable.dd_6);
        emojiMap.put("[恐惧]", R.drawable.dd_7);
        emojiMap.put("[失望]", R.drawable.dd_8);
        emojiMap.put("[眨眼]", R.drawable.dd_9);
        emojiMap.put("[满意]", R.drawable.dd_10);
        emojiMap.put("[无语]", R.drawable.dd_11);
        emojiMap.put("[恶魔]", R.drawable.dd_12);
        emojiMap.put("[鬼魂]", R.drawable.dd_13);
        emojiMap.put("[礼盒]", R.drawable.dd_14);
        emojiMap.put("[合十]", R.drawable.dd_15);
        emojiMap.put("[强壮]", R.drawable.dd_16);//===
        emojiMap.put("[钱]", R.drawable.dd_17);
        // emojiMap.put("[蛋糕]", R.drawable.dd_18);
        emojiMap.put("[气球]", R.drawable.dd_19);
        //    emojiMap.put("[礼物]", R.drawable.dd_20);
    }

    public static int getImgByName(String imgName) {
        Integer integer = emojiMap.get(imgName);
        return integer == null ? -1 : integer;
    }
}


