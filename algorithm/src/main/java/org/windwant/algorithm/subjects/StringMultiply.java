package org.windwant.algorithm.subjects;


/**
 * 2. 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
 *
 * 说明：
 * num1 和 num2 的长度小于110。
 * num1 和 num2 只包含数字 0-9。
 * num1 和 num2 均不以零开头，除非是数字 0 本身。
 * 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。
 * 在真实的面试中遇到过这道题？
 *
 * 2. 实现 strStr() 函数。
 *
 * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。

 * Created by Administrator on 19-2-28.
 */
public class StringMultiply {

    public static String multiply(String num1, String num2) {
        if(num1 == null || num2 == null || "".equals(num1) || "".equals(num2) || "0".equals(num1) || "0".equals(num2)) return String.valueOf(0);
        int lgn1 = num1.length(), lgn2 = num2.length();
        int[] result = new int[lgn1 + lgn2];

        int resultIndex = result.length - 1;
        for (int i = lgn1 - 1; i > -1 ; i--) {
            int first = Integer.parseInt(String.valueOf(num1.charAt(i)));
            int innerIndex = 0;
            for (int j = lgn2 - 1; j > -1 ; j--) {
                int second = Integer.parseInt(String.valueOf(num2.charAt(j)));
                int plus = first * second;
                result[resultIndex - innerIndex] += plus%10;
                if(plus >= 10) {
                    result[resultIndex - innerIndex - 1] += plus / 10;
                }
                innerIndex++;
            }
            resultIndex--;
        }

        //处理进位
        StringBuilder sb = new StringBuilder();
        for (int i = result.length - 1; i >= 0; i--) {
            if(result[i]>=10) {
                result[i - 1] += result[i]/10;
                result[i] %= 10;
            }
        }
        //提取有效位
        boolean start = false;
        for (int i = 0; i < lgn1 + lgn2 ; i++) {
            if(!start && result[i] != 0){
                start = true;
            }
            if(start){
                sb.append(result[i]);
            }
        }
        return sb.toString();
    }

    public static int strStr(String haystack, String needle) {
        if(haystack == null || needle == null) return -1;
        if(haystack.equals(needle) || "".equals(needle)) return 0;
        int findex = 0;
        int flgn = haystack.length(), slgn = needle.length();
        while (findex + slgn <= flgn){
            if(haystack.charAt(findex) == needle.charAt(0)) {
                int sindex = 0;
                for (; sindex < slgn; sindex++) {
                    if (needle.charAt(sindex) != haystack.charAt(findex + sindex)){
                        findex = findex + sindex - 1;
                        break;
                    }
                }
                if(sindex == slgn){
                    return findex;
                }else {
                    findex = findex - sindex + 1;
                    findex++;
                    continue;
                }
            }else {
                findex++;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        String num1 = "123", num2 = "456";
//        String num1 = "9", num2 = "9";
//        String num1 = "2", num2 = "5";
//        String num1 = "0", num2 = "0";
//        String num1 = "999", num2 = "999";
//        System.out.println(multiply(num1, num2));
        System.out.println(strStr("hello", "ll"));
        System.out.println(strStr("a", ""));
        System.out.println(strStr("mississippi", "issip"));
        System.out.println(strStr("mississippi", "issipi"));
        System.out.println(strStr("mississippi", "pi"));
    }
}
