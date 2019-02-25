package org.windwant.algorithm.subjects;

/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 * Created by Administrator on 19-2-25.
 */
public class ReverseInt {

    /**
     * 数据范围判断
     * @param x
     * @return
     */
    public int reverse(int x) {
        double result = 0;
        while (x != 0){
            result = result * 10 + x%10;
            if (result > Integer.MAX_VALUE) return 0;
            if (result < Integer.MIN_VALUE) return 0;

            x = x/10;
        }
        return (int) result;
    }

    public static void main(String[] args) {
        System.out.println(new ReverseInt().reverse(321));
    }
}
