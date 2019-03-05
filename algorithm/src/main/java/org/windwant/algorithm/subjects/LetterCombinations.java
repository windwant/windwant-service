package org.windwant.algorithm.subjects;

import java.util.*;

/**
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。

 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。

 * Created by Administrator on 19-3-4.
 */
public class LetterCombinations {

    public static Map<Integer, String> maps = new HashMap(){{
        put(2, "abc"); put(3, "def"); put(4, "ghi"); put(5, "jkl"); put(6, "mno"); put(7, "pqrs"); put(8, "tuv"); put(9, "wxyz");
    }};
    public static List<String> letterCombinations(String digits) {
        int size = digits.length();
        if(digits == null || digits.length() == 0) return new ArrayList<>();

        Set<String> coms = new HashSet();

        if(size == 1){
            String ele = maps.get(Integer.parseInt(digits));
            for (int i = 0; i < ele.length(); i++) {
                coms.add(String.valueOf(ele.charAt(i)));
            }
        }else if(size == 2){
            String left = maps.get(Integer.parseInt(String.valueOf(digits.charAt(0))));
            String right = maps.get(Integer.parseInt(String.valueOf(digits.charAt(1))));
            for (int k = 0; k < left.length(); k++) {
                for (int l = 0; l < right.length(); l++) {
                    coms.add(String.valueOf(left.charAt(k) + String.valueOf(right.charAt(l))));
                }
            }
        }else {
            String left = maps.get(Integer.parseInt(String.valueOf(digits.charAt(0))));
            List<String> subs = letterCombinations(digits.substring(1, digits.length()));
            subs.stream().forEach(item->{
                for (int l = 0; l < left.length(); l++) {
                    coms.add(String.valueOf(left.charAt(l)) + item);
                }
            });
        }

        return new ArrayList<>(coms);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(letterCombinations("").toArray()));
        System.out.println(Arrays.toString(letterCombinations("2").toArray()));
        System.out.println(Arrays.toString(letterCombinations("23").toArray()));
        System.out.println(Arrays.toString(letterCombinations("234").toArray()));
        System.out.println(Arrays.toString(letterCombinations("5678").toArray()));
        System.out.println(Arrays.toString(letterCombinations("56789").toArray()));
    }
}
