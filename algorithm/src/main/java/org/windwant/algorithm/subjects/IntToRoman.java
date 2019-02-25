package org.windwant.algorithm.subjects;

/**
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。

 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。

 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：

 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个整数，将其转为罗马数字。输入确保在 1 到 3999 的范围内。
 * Created by Administrator on 19-2-25.
 */
public class IntToRoman {

    public String intToRoman(int num) {
        if(num > 3999) return "";
        if(num/1000 > 0){
            return dealQianWei(num);
        }else if(num/100 > 0){
            return dealBaiWei(num);
        }else if(num/10 > 0){
            return dealShiWei(num);
        }else {
            return dealGeWei(num);
        }
    }

    public String dealQianWei(int num){
        return countStr(num/1000, "M") + dealBaiWei(num%1000);
    }

    public String dealBaiWei(int num){
        int bc = num/100;
        if(bc == 9) return "CM" + dealShiWei(num % 100);
        if(bc == 4) return "CD" + dealShiWei(num % 100);
        int fbc = num/500;
        num = num%500;
        return countStr(fbc, "D") + countStr(num/100, "C") + dealShiWei(num%100);
    }

    public String dealShiWei(int num){
        int tens = num/10;
        if(tens == 9) return "XC" + dealGeWei(num % 10);
        if(tens == 4) return "XL" + dealGeWei(num % 10);
        int ftens = num/50;
        num = num%50;
        return countStr(ftens, "L") + countStr(num/10, "X") + dealGeWei(num%10);
    }

    public String dealGeWei(int num){
        if(num == 9) return "IX";
        if(num == 4) return "IV";
        if(num >= 5) return "V" + dealGeWei(num % 5);
        return countStr(num, "I");
    }

    public String countStr(int count, String num){
        if(count == 0) return "";

        String result = "";
        for (int i = 0; i < count; i++) {
            result += num;
        }
        return result;
    }

    public static void main(String[] args) {
        IntToRoman intToRoman = new IntToRoman();
        System.out.println(intToRoman.intToRoman(3));
        System.out.println(intToRoman.intToRoman(4));
        System.out.println(intToRoman.intToRoman(9));
        System.out.println(intToRoman.intToRoman(58));
        System.out.println(intToRoman.intToRoman(1994));
        System.out.println(intToRoman.intToRoman(3994));
        System.out.println(intToRoman.intToRoman(205));
        System.out.println(intToRoman.intToRoman(20));
    }
}
