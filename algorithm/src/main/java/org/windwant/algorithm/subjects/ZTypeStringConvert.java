package org.windwant.algorithm.subjects;

import java.util.LinkedList;
import java.util.List;

/**
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：

 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 *
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
 * 请你实现这个将字符串进行指定行数变换的函数：

 * string convert(string s, int numRows);
 * 示例 1:

 * 输入: s = "LEETCODEISHIRING", numRows = 3
 * 输出: "LCIRETOESIIGEDHN"
 * 示例 2:

 * 输入: s = "LEETCODEISHIRING", numRows = 4
 * 输出: "LDREOEIIECIHNTSG"
 * 解释:

 * L     D     R
 * E   O E   I I
 * E C   I H   N
 * T     S     G
 * Created by Administrator on 19-2-25.
 */
public class ZTypeStringConvert {
    /**
     * 定义目标行数个链表，如示例，每次中间间隔的 numRows - 2 个列表逐个填充一个值
     * 便利给定的字符串，依次处理，直到末尾
     * @param s
     * @param numRows
     * @return
     */
    public static String convert(String s, int numRows) {
        if(numRows == 1){
            return s;
        }
        String result = "";
        if(numRows == 2){
            result = "";
            for (int i = 0; i < s.length(); i = i + 2) {
                result += s.charAt(i);
            }
            for (int i = 1; i < s.length(); i = i + 2) {
                result += s.charAt(i);
            }
            return result;
        }
        int middleCount = numRows - 2;
        List[] all = new LinkedList[numRows];
        for (int i = 0; i < numRows; i++) {
            all[i] = new LinkedList<>();
        }
        int sIndex = 0;
        int step = 0;
        while (sIndex < s.length()){
            for (int i = 0; i < numRows; i++) {
                if(sIndex == s.length()) break;
                all[i].add(s.charAt(sIndex));
                sIndex++;
            }
            for (int j = numRows - 2; j > 0 ; j--) {
                if(sIndex == s.length()) break;
                all[j].add(s.charAt(sIndex));
                sIndex++;
            }
            step = step + middleCount;
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < all[i].size(); j++) {
                result += all[i].get(j);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "LEETCODEISHIRING";
        int numRows = 4;
        System.out.println(ZTypeStringConvert.convert(s, numRows));
    }
}
