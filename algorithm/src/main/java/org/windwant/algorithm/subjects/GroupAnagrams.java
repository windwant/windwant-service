package org.windwant.algorithm.subjects;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
 * 所有输入均为小写字母。
 * 不考虑答案输出的顺序。
 * Created by Administrator on 19-2-27.
 */
public class GroupAnagrams {

    /**
     * 双指针处理
     * @param strs
     * @return
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList();
        if(strs == null ||strs.length == 0) return result;
        if(strs.length == 1){
            result.add(Arrays.asList(strs[0]));
            return result;
        }
        List<String> middle = new ArrayList();
        int i = 0, i1 = strs.length - 1;
        List<String> newUse = new ArrayList();
        while (i < i1){
            if(checkEqual(strs[i], strs[i1])){
                middle.add(strs[i1]);
            }else{
                newUse.add(strs[i1]);
            }
            i1--;
            if(i == i1){
                middle.add(strs[i]);
                result.add(new ArrayList(middle));
                middle = new ArrayList();
                strs = newUse.toArray(new String[0]);
                if(strs.length == 1){
                    result.add(Arrays.asList(strs[0]));
                    break;
                }
                newUse = new ArrayList();
                i = 0;
                i1 = strs.length - 1;

            }
        }
        return result;
    }

    /**
     * 字符串比较 是否包含相同的字符
     * @param s
     * @param s1
     * @return
     */
    public static boolean checkEqual(String s, String s1){
        if(s == null || s1 == null) return false;
        if(s.length() != s1.length()) return false;
        if(s == "" && s1 == "") return true;
        int s1lgn = s1.length();

        int sIndex = 0;
        while (s.length() > 0 && sIndex < s1lgn){
            String tmp = String.valueOf(s1.charAt(sIndex));
            if(!s.contains(tmp)){
                return false;
            }
            sIndex++;
            s = s.replaceFirst(tmp, "");
        }
        if(sIndex < s1lgn - 1) return false;
        return true;
    }

    public static boolean checkEqual2(String s, String s1){
        if(s == null || s1 == null) return false;
        if(s.length() != s1.length()) return false;
        if(s == "" && s1 == "") return true;
        int slgn = s.length(), s1lgn = s1.length();

        List<String> list = new ArrayList();
        for (int i = 0; i < slgn; i++) {
            list.add(String.valueOf(s.charAt(i)));
        }
        int sIndex = 0;
        while (list.size() > 0 && sIndex < s1lgn){
            if(!list.contains(String.valueOf(s1.charAt(sIndex)))){
                return false;
            }
            list.remove(String.valueOf(s1.charAt(sIndex)));
            sIndex++;
        }
        if(sIndex < s1lgn - 1) return false;
        return true;
    }

    public static String sortStr(String s){
        List<Character> list = new LinkedList();

        if(s == null || s.length() == 1) return s;

        for (int i = 0; i < s.length(); i++) {
            list.add(s.charAt(i));
        }
        for (int i = 1; i < s.length(); i++) {
            char curr = s.charAt(i);
            for (int j = i - 1; j > -1; j--) {
                if(curr < list.get(j)){
                    list.set(j + 1, list.get(j));
                    list.set(j, s.charAt(i));
                }else {
                    list.set(j + 1, curr);
                    break;
                }
            }
        }
        String result = "";
        for (Character character : list) {
            result += character;
        }
        return result;
    }

    public static List<List<String>> groupAnagrams2(String[] strs) {
        List<List<String>> result = new ArrayList();
        Set<String> middle = new HashSet<>();
        for (int i = 0,i1 = strs.length - 1; i < i1;) {
            char[] left = strs[i].toCharArray();
            char[] right = strs[i1].toCharArray();
            Arrays.sort(left);
            Arrays.sort(right);
            if(Arrays.equals(left, right)){
                middle.add(strs[i]);
                middle.add(strs[i1]);
            }
            i1--;
            if(i == i1){
                i++;
                if(!middle.isEmpty()) {
                    result.add(new ArrayList(middle));
                }
                middle = new HashSet();
                i1 = strs.length - 1;
            }
        }
        return result;
    }

    /**
     * map
     * 使用数组排序
     * @param strs
     * @return
     */
    public static List<List<String>> groupAnagrams3(String[] strs) {
        List<List<String>> result = new ArrayList();
        if(strs == null ||strs.length == 0) return result;
        if(strs.length == 1){
            result.add(Arrays.asList(strs[0]));
            return result;
        }

        Map<String, List<String>> maps = new HashMap();
        for (String str : strs) {
            char[] arr = str.toCharArray();
            Arrays.sort(arr);
            String sorted = Arrays.toString(arr);
            if(maps.get(sorted) != null){
                maps.get(sorted).add(str);
            }else {
                maps.put(sorted, new ArrayList(){{add(str);}});
            }
        }
        maps.remove(null);
        return maps.values().stream().collect(Collectors.toList());
    }

    /**
     * map
     * 使用自定义字符串排序
     * @param strs
     * @return
     */
    public static List<List<String>> groupAnagrams4(String[] strs) {
        List<List<String>> result = new ArrayList();
        if(strs == null ||strs.length == 0) return result;
        if(strs.length == 1){
            result.add(Arrays.asList(strs[0]));
            return result;
        }

        Map<String, List<String>> maps = new HashMap();
        for (String str : strs) {
            String sorted = sortStr(str);
            if(maps.get(sorted) != null){
                maps.get(sorted).add(str);
            }else {
                maps.put(sorted, new ArrayList(){{add(str);}});
            }
        }
        return maps.values().stream().collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(checkEqual("dad", "day"));
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
//        String[] strs = {""};
//        String[] strs = {"",""};
//        String[] strs = {"","b"};
//        String[] strs = {"paw","dad","bog","day","day","mig","len","rat"};
        long t = System.nanoTime();
        List<List<String>> result = groupAnagrams(strs);
        System.out.println("1: " + (System.nanoTime() - t));
        for (List<String> list : result) {
            System.out.println(Arrays.toString(list.toArray()));
        }
        t = System.nanoTime();
        result = groupAnagrams2(strs);
        System.out.println("2: " + (System.nanoTime() - t));
        for (List<String> list : result) {
            System.out.println(Arrays.toString(list.toArray()));
        }
        t = System.nanoTime();
        result = groupAnagrams3(strs);
        System.out.println("3: " + (System.nanoTime() - t));
        for (List<String> list : result) {
            System.out.println(Arrays.toString(list.toArray()));
        }
        t = System.nanoTime();
        result = groupAnagrams4(strs);
        System.out.println("4: " + (System.nanoTime() - t));
        for (List<String> list : result) {
            System.out.println(Arrays.toString(list.toArray()));
        }
    }
}
