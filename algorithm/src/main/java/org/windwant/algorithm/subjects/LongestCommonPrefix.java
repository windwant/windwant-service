package org.windwant.algorithm.subjects;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * 如果不存在公共前缀，返回空字符串 ""。
 * Created by Administrator on 19-2-25.
 */
public class LongestCommonPrefix {

    public static String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0) return "";
        String shortStr = strs[0];
        for (String str : strs) {
            if(str.length() < shortStr.length()){
                shortStr = str;
            }
        }

        if("".equals(shortStr)) return "";

        String publicPrefix = "";

        for (int i = 0; i < shortStr.length(); i++) {
            char tmp = shortStr.charAt(i);
            for (String str : strs) {
                if(str.charAt(i) != tmp){
                    return publicPrefix;
                }
            }
            publicPrefix += tmp;
        }
        return publicPrefix;
    }

    public String longestCommonPrefix2(String[] strs) {
        if(strs == null || strs.length == 0) return "";

        String shortStr = strs[0];
        int index = 0;
        for (int i = 0; i < shortStr.length(); i++) {
            for (String str : strs) {
                if("".equals(str)) return shortStr.substring(0, index);;
                if(index > str.length() - 1) return shortStr.substring(0, index);
                if(shortStr.charAt(index) != str.charAt(index)){
                    return shortStr.substring(0, index);
                }
            }
            index++;
        }

        return shortStr.substring(0, index);
    }

    public static void main(String[] args) {
        String[] strs = {"abab","aba", ""};
        System.out.println(LongestCommonPrefix.longestCommonPrefix(strs));
    }
}
