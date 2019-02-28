package org.windwant.algorithm.subjects;

/**
 * 报数序列是一个整数序列，按照其中的整数的顺序进行报数，得到下一个数。其前五项如下：

 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * 1 被读作  "one 1"  ("一个一") , 即 11。
 * 11 被读作 "two 1s" ("两个一"）, 即 21。
 * 21 被读作 "one 2",  "one 1" （"一个二" ,  "一个一") , 即 1211。

 * 给定一个正整数 n（1 ≤ n ≤ 30），输出报数序列的第 n 项。

 * 注意：整数顺序将表示为一个字符串。
 * Created by Administrator on 19-2-28.
 */
public class CountAndSay {

    public static String countAndSay(int n) {
        if(n < 1 || n > 30) return "";
        int start = 1;
        String report = "1";
        String result = "";
        while (start < n ){
            result = "";
            for (int i = 0; i < report.length();) {
                int c = i;
                int count = 1;
                while (c + 1 < report.length()){
                    if(report.charAt(c) != report.charAt(c + 1)){
                        break;
                    }
                    count++;
                    c++;
                }
                result += String.valueOf(count) + String.valueOf(report.charAt(i));
                i = i + count;
            }
            report = result;
            start++;
        }

        return report;
    }

    public static void main(String[] args) {
        int num = 30;
        System.out.println(countAndSay(num));
    }
}
