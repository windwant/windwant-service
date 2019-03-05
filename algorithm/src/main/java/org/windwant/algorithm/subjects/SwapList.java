package org.windwant.algorithm.subjects;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。

 * 示例:

 * 给定 1->2->3->4, 你应该返回 2->1->4->3.
 * Created by Administrator on 19-3-5.
 */
public class SwapList {
    public static ListNode swapPairs(ListNode head) {
        if(head == null) return null;
        ListNode first = head.next == null?head:head.next;
        ListNode curr = head;
        ListNode pre = null;
        while (head != null){
            if(head.next != null){
                //保存需要交换位置的节点之后的节点
                curr = head.next.next;
                //将上一个节点和交换后的节点相连
                if(pre != null){
                    pre.next = head.next;
                }

                //保存下一次需要交换位置节点的上一个节点
                pre = head;
                //交换节点
                head.next.next = head;
                //将交换位置后的第二个节点和后面的节点相连
                head.next = curr;
            }
            //继续遍历
            head = head.next;
        }
        return first;
    }

    public static ListNode[] swapList(ListNode head) {
        if(head == null) return null;
        List<ListNode> list = new LinkedList();
        while (head != null){
            list.add(head);
            head = head.next;
        }
        if(list.size() == 1) return new ListNode[]{head, head};

        Collections.reverse(list);
        for (int i = 0; i < list.size() - 1; i++) {
            list.get(i).next = list.get(i + 1);
        }
        list.get(list.size() - 1).next = null;
        return new ListNode[]{list.get(0), list.get(list.size() - 1)};
    }

    /**
     * k个一组翻转链表
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        if(head == null) return null;
        if(k == 1) return head;
        if(head.next == null) return head;
        List<ListNode> list = new LinkedList();
        List<ListNode> listCopy = new LinkedList();
        List<ListNode> tmpList = new LinkedList();

        int index = 0;
        while (head != null){
            list.add(head);
            head = head.next;
            index++;
            if(index%k == 0){
                tmpList = list.subList(index - k, index);
                Collections.reverse(tmpList);
                listCopy.addAll(tmpList);
            }
        }
        if(index%k != 0){
            tmpList = list.subList(index > k?(index - index%k):0, index);
            listCopy.addAll(tmpList);
        }

        for (int i = 0; i < listCopy.size() - 1; i++) {
            listCopy.get(i).next = listCopy.get(i + 1);
        }
        listCopy.get(listCopy.size() - 1).next = null;
        return listCopy.get(0);
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l12 = new ListNode(2);
        l1.next = l12;
        ListNode l13 = new ListNode(3);
        l12.next = l13;
        ListNode l14 = new ListNode(4);
        l13.next = l14;
        ListNode l15 = new ListNode(5);
        l14.next = l15;
//        ListNode resulta = swapPairs(l1);
//        while (resulta != null){
//            System.out.print(resulta.val + " ");
//            resulta = resulta.next;
//        }

        ListNode result = reverseKGroup(l1, 3);
        while (result!= null){
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
