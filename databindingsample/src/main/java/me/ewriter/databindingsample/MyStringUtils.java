package me.ewriter.databindingsample;

/**
 * Created by Zubin on 2016/8/22.
 * DataBing 使用类方法，具体查看 activity_main 中如何使用
 * 现在 data 中 import ，剩下的和普通java 中使用一样
 */
public class MyStringUtils {

    /**把第一个字符转化为大写*/
    public static String capitalize(String word) {
        if (word.length() > 1) {
            return String.valueOf(word.charAt(0)).toUpperCase() + word.substring(1);
        }
        return word;
    }
}
