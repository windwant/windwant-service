package org.windwant.algorithm.subjects;

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
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

    public static void main(String[] args) {
        System.out.println(Palindrome.isPalindrome(12321));
    }
}
