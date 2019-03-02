package org.windwant.algorithm.subjects;

/**
 * 1. 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 *
 * 2. 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。

 * 示例 1：

 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：

 * 输入: "cbbd"
 * 输出: "bb"
 * Created by Administrator on 19-2-25.
 */
public class Palindrome {

    /**
     * 转化为字符串，一次便利，首末对称位置对比
     * @param x
     * @return
     */
    public static boolean isPalindrome(int x) {
        String s = String.valueOf(x);
        int lgn = s.length();
        for (int i = 0,j = lgn -1; i <= j; i++,j--){
            if(s.charAt(i) == s.charAt(j)){
                continue;
            }else {
                return false;
            }
        }
        return true;
    }

    /**
     * 遍历中间字符，判断
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        if(s == null || s.length() == 1 || s.length() == 0) return s;
        if(s.length() == 2) return s.charAt(0) == s.charAt(1)?s:String.valueOf(s.charAt(0));

        String maxP = "";

        //中间索引
        int middle = (s.length()-1)/2;
        //字符串长度奇偶 判别
        boolean dmiddle = s.length()%2 == 0 && s.charAt(middle) == s.charAt(middle + 1);

        //遍历使用临时中间字符索引
        int tmpMiddle = middle;
        //紧邻元素接次判断
        int initJudge = 1;
        //每次判断中间最长回文字符
        String tp = "";
        //最开始 判断是否偶长度
        boolean first = true;
        //中间字符逐次左移判断
        while (tmpMiddle > 0) {
            initJudge = 1;
            //左右指针
            int lindex = tmpMiddle - 1, rindex = tmpMiddle + (first && dmiddle?2:1);
            while (lindex > -1 && rindex < s.length()) {
                if(initJudge == 1) {
                    //左边紧邻接次判断
                    if (s.charAt(lindex) == s.charAt(tmpMiddle)) {
                        lindex--;
                        initJudge++;
                    }
                    //右边紧邻接次判断
                    if (s.charAt(rindex) == s.charAt(tmpMiddle + (first && dmiddle?1:0))) {
                        rindex++;
                        initJudge++;
                    }
                    initJudge = initJudge>1?1:0;
                    first = false;
                    tp = s.substring(lindex + 1, rindex);
                    continue;
                }
                //对称位置判断
                if (s.charAt(lindex) == s.charAt(rindex)) {
                    lindex--;
                    rindex++;
                    tp = s.substring(lindex + 1, rindex);
                    continue;
                }
                tp = s.substring(lindex + 1, rindex);
                break;
            }

            if(tp.length() > maxP.length()){
                maxP = tp;
            }
            tmpMiddle--;
        }

        tmpMiddle = middle;
        tp = "";
        first = false;
        //中间字符逐次右移判断
        while (tmpMiddle < s.length()) {
            initJudge = 1;
            //左右指针
            int lindex = tmpMiddle - 1, rindex = tmpMiddle + (first && dmiddle?2:1);
            while (lindex > -1 && rindex < s.length()) {
                if(initJudge == 1) {
                    //左边紧邻接次判断
                    if (s.charAt(lindex) == s.charAt(tmpMiddle)) {
                        lindex--;
                        initJudge++;
                    }
                    //右边紧邻接次判断
                    if (s.charAt(rindex) == s.charAt(tmpMiddle + (first && dmiddle?1:0))) {
                        rindex++;
                        initJudge++;
                    }
                    initJudge = initJudge>1?1:0;
                    tp = s.substring(lindex + 1, rindex);
                    continue;
                }
                //对称位置判断
                if (s.charAt(lindex) == s.charAt(rindex)) {
                    lindex--;
                    rindex++;
                    tp = s.substring(lindex + 1, rindex);
                    continue;
                }
                tp = s.substring(lindex + 1, rindex);
                break;
            }
            if(tp.length() > maxP.length()){
                maxP = tp;
            }
            tmpMiddle++;
        }
        return maxP;
    }

    public static void main(String[] args) {
//        System.out.println(Palindrome.isPalindrome(12321));
        System.out.println(longestPalindrome("babad"));
        System.out.println(longestPalindrome("abc09245991"));
        System.out.println(longestPalindrome("ccc"));
        System.out.println(longestPalindrome("aaaa"));
        System.out.println(longestPalindrome("aaabaaaa"));
        System.out.println(longestPalindrome("abcdbbfcba"));
        System.out.println(longestPalindrome("aaaabaaa"));
        System.out.println(longestPalindrome("ffffggg"));
        System.out.println(longestPalindrome("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg").length());
//        System.out.println("ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg".length());
        System.out.println("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".length());
    }
}
