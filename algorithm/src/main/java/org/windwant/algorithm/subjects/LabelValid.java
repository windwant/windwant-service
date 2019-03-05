package org.windwant.algorithm.subjects;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。

 * 有效字符串需满足：

 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 *
 * Created by Administrator on 19-3-5.
 */
public class LabelValid {
    //括号映射
    public static Map<String, String> maps = new HashMap(){{
        put("}", "{");
        put("]", "[");
        put(")", "(");
        put("{", "}");
        put("[", "]");
        put("(", ")");
    }};
    public static boolean isValid(String s) {
        if(s == null || s.length() == 1) return false;
        if(s.length() == 0 ) return true;
        int index = 1;
        //当前字符
        char focus = s.charAt(0);
        //存储非相邻匹配的符号
        List<String> unmap = new LinkedList();
        while (index < s.length())
            //相邻对比
            if(String.valueOf(s.charAt(index)).equals(maps.get(String.valueOf(focus)))){
                //处理存储的非相邻匹配的符号匹配
                int lIndex = unmap.size() - 1;
                if(lIndex > 0) {
                    unmap.remove(lIndex);
                }
                while (lIndex > 0){
                    index++;
                    lIndex--;
                    //倒序遍历匹配
                    if(unmap.get(lIndex).equals(maps.get(String.valueOf(s.charAt(index))))){
                        //匹配上则删除list中元素
                        unmap.remove(lIndex);
                        continue;
                    }else {
                        //匹配不上则加入到list中
                        unmap.add(String.valueOf(s.charAt(index)));
                        index--;
                        break;
                    }
                }
                index++;
                if(index >= s.length()) return true;
                focus = s.charAt(index);
                index++;
            }else {
                //相邻不匹配则加入到 list中以供后续使用
                if(s.substring(index).contains(String.valueOf(maps.get(String.valueOf(focus)))) && (index + 1 < s.length()) &&s.substring(index + 1).contains(String.valueOf(maps.get(String.valueOf(s.charAt(index)))))){
                    //第一次非相邻的时候添加头一个元素
                    if(unmap.size() == 0) {
                        unmap.add(String.valueOf(focus));
                    }
                    unmap.add(String.valueOf(s.charAt(index)));
                    focus = s.charAt(index);
                    index++;

                }else{
                    return false;
                }
            }

        return false;
    }

    public static void main(String[] args) {
        String label = "{[]}";
        System.out.println(isValid(label));
        label = "()[]{}";
        System.out.println(isValid(label));
        label = "(]";
        System.out.println(isValid(label));
        label = "[()](())";
        System.out.println(isValid(label));
        label = "";
        System.out.println(isValid(label));
        label = "[{()}]";
        System.out.println(isValid(label));
        label = "(([]){})";
        System.out.println(isValid(label));
    }
}
