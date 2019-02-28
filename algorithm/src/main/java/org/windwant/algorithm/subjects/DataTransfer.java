package org.windwant.algorithm.subjects;

/**
 * 进制转换 十进制 =》 62进制
 * Created by Administrator on 19-2-28.
 */
public class DataTransfer {
    public static String BASE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static String transfer(int num) {
        int scale = 62;
        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            sb.append(BASE.charAt(num % scale));
            num = num / scale;
        }

        sb.reverse();

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(transfer(62));
    }
}
