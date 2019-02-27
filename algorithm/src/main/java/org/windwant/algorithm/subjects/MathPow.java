package org.windwant.algorithm.subjects;

/**
 * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
 * -100.0 < x < 100.0
 * n 是 32 位有符号整数，其数值范围是 [−2^31, 2^31 − 1] 。
 * Created by Administrator on 19-2-27.
 */
public class MathPow {

    public static double power(double x, int n) {
        if(!(x > -100 && x < 100)) return 0;
        if(!(n <= Integer.MAX_VALUE && n >= Integer.MIN_VALUE)) return 0;

        if(x == 0) return 0;
        if(x == 1) return 1;
        if(n == 0) return 1;
        if(n == 1) return x;
        if(n == -1) return 1/x;

        if(n > 1 || n < -1){
            double nextValue = power(x, n / 2);
            return (n % 2 == 0 ? 1 : (n > 0 ? x : 1/x)) * nextValue * nextValue;
        }
        return x;
    }

    public static void main(String[] args) {
        MathPow mathPow = new MathPow();
        System.out.println(mathPow.power(2, 10));
    }
}
