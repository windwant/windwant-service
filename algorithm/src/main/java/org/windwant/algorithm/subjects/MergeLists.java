package org.windwant.algorithm.subjects;

import java.util.*;

/**
 * 1. 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * 2. 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。2
 * Created by Administrator on 19-3-5.
 */
public class MergeLists {

    /**
     * 合并两个有序链表
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null) return null;

        List<ListNode> list1 = new ArrayList();
        while (l1!= null){
            list1.add(l1);
            l1 = l1.next;
        }

        List<ListNode> list2 = new ArrayList();
        while (l2!= null){
            list2.add(l2);
            l2 = l2.next;
        }
        List<ListNode> result = mergeArrays(list1, list2);
        for (int i = 0; i < result.size() - 1; i++) {
            result.get(i).next = result.get(i + 1);
        }

        return result.get(0);
    }

    public static List<ListNode> mergeArrays(List<ListNode> nums1, List<ListNode> nums2) {
        List<ListNode> indexes = new LinkedList();
        int lgn1 = nums1.size();
        int lgn2 = nums2.size();
        int allLgn = lgn1 + lgn2;


        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < allLgn; i++) {
            if(index1 < lgn1 && index2 < lgn2) {
                if (nums1.get(index1).val > nums2.get(index2).val) {
                    indexes.add(nums2.get(index2));
                    index2++;
                } else {
                    indexes.add(nums1.get(index1));
                    index1++;
                }
            }else if(index1 < lgn1){
                indexes.add(nums1.get(index1));
                index1++;
            }else if(index2 < lgn2){
                indexes.add(nums2.get(index2));
                index2++;
            }
        }
       return indexes;
    }


    /**
     * 合并k个有序链表
     * @param lists
     * @return
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        if(lists == null || lists.length == 0) return null;
        if(lists.length == 1) return lists[0];
        int middle = (lists.length + 1)/2;
        ListNode left = mergeKLists(Arrays.copyOfRange(lists, 0, middle));
        ListNode right = mergeKLists(Arrays.copyOfRange(lists, middle, lists.length));
        List<ListNode> list1 = new ArrayList();
        while (left!= null){
            list1.add(left);
            left = left.next;
        }
        List<ListNode> list2 = new ArrayList();
        while (right!= null){
            list2.add(right);
            right = right.next;
        }
        List<ListNode> result = mergeArrays(list1, list2);
        for (int i = 0; i < result.size() - 1; i++) {
            result.get(i).next = result.get(i + 1);
        }

        return result.size() > 0?result.get(0):null;
    }


    public static void main(String[] args) {
        //构造测试链表
//        ListNode l1 = new ListNode(1);
//        ListNode l12 = new ListNode(2);
//        l1.next = l12;
//        ListNode l13 = new ListNode(4);
//        l12.next = l13;
//
//        ListNode l2 = new ListNode(1);
//        ListNode l22 = new ListNode(3);
//        l2.next = l22;
//        ListNode l23 = new ListNode(4);
//        l22.next = l23;
//
//        ListNode result = mergeTwoLists(l1, l2);
//        while (result != null){
//            System.out.print(result.val + " ");
//            result = result.next;
//        }
//
//        //构造测试链表
//        ListNode l1x = new ListNode(2);
//
//        ListNode l2x = new ListNode(1);
//
//        ListNode resultx = mergeTwoLists(l1x, l2x);
//        while (resultx != null){
//            System.out.print(resultx.val + " ");
//            resultx = resultx.next;
//        }

//        ListNode l1a = new ListNode(1);
//        ListNode l12a = new ListNode(4);
//        l1a.next = l12a;
//        ListNode l13a = new ListNode(5);
//        l12a.next = l13a;
//
//        ListNode l2a = new ListNode(1);
//        ListNode l22a = new ListNode(3);
//        l2a.next = l22a;
//        ListNode l23a = new ListNode(4);
//        l22a.next = l23a;
//
//        ListNode l3a = new ListNode(6);
//
//        ListNode resulta = mergeKLists(new ListNode[]{l1a,l2a,l3a});
//        while (resulta != null){
//            System.out.print(resulta.val + " ");
//            resulta = resulta.next;
//        }
        mergeKLists(new ListNode[]{null, null});
    }

}
