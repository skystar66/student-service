package com.tengyue360.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 工具类
 */
public class CommonTools {

    /**
     * 创建指定数量的随机字符串
     * 
     * @param numberFlag
     *            是否是数字
     * @param length
     *            长度
     * @return
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    /**
    * 验证非零的正整数
    * 
    * @param str
    * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
    */
    public static boolean IsIntNumber(String str) {
        String regex = "^\\+?[1-9][0-9]*$";
        return match(regex, str);
    }

    /**
    * 验证数字输入
    * 
    * @param str
    * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
    */
    public static boolean IsNumber(String str) {
        String regex = "^[0-9]*$";
        return match(regex, str);
    }

    /**
    * @param regex
    * 正则表达式字符串
    * @param str
    * 要匹配的字符串
    * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
    */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(match("^([1-9]\\d*|0)(\\.\\d{1,2})?$", "123565.22"));
    }
}
