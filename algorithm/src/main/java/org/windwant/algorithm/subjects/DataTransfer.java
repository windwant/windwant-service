package org.windwant.algorithm.subjects;

/**
 * 进制转换 十进制 =》 62进制
 * 这里所谓62进制是指采用0~9A~Za~z等62个字符进行编码（按ASCII顺序由小到大）。
 * Created by Administrator on 19-2-28.
 */
public class DataTransfer {
    public static String BASE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static String transfer(int num) {
        int scale = 62;
        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            //余数对照进制基本位 BASE 放到相应位置
            sb.append(BASE.charAt(num % scale));
            //除处理下一进位
            num = num / scale;
        }

        sb.reverse();

        return sb.toString();
    }

    /**
     * 10进制转16进制
     */
    public static String BASE_H = "0123456789ABCDEF";
    public static int SCALE_H = 16;
    public static String transferDTH(int num) {
        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            //余数对照进制基本位 BASE 放到相应位置
            sb.append(BASE_H.charAt(num % SCALE_H));
            //除处理下一进位
            num = num / SCALE_H;
        }

        sb.reverse();

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(transfer(62));
        System.out.println(transferDTH(62));
    }
}
