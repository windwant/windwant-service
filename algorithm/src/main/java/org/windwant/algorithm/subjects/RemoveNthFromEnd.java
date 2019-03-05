package org.windwant.algorithm.subjects;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。

 * 示例：

 * 给定一个链表: 1->2->3->4->5, 和 n = 2.

 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * 说明：

 * 给定的 n 保证是有效的。
 * Created by Administrator on 19-3-4.
 */
public class RemoveNthFromEnd {
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head;
        //subIndex 第n个元素的索引
        int index = 0, subIndex = 0;

        List<ListNode> list = new ArrayList();

        while (first != null){
            list.add(first);

            if(index - subIndex > n){
                subIndex++;
            }
            index++;
            first = first.next;
        }

        if(list.size() < n) return null;
        if(list.size() == 1) return null;
        if(list.size() == n) return list.get(1);

        //处理移除
        list.get(subIndex).next = list.get(subIndex).next.next;
        return list.get(0);
    }

    public static void main(String[] args) {
        //构造测试链表
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        l1.next = l2;
        ListNode l3 = new ListNode(3);
        l2.next = l3;
        ListNode l4 = new ListNode(4);
        l3.next = l4;
        ListNode l5 = new ListNode(5);
        l4.next = l5;

        ListNode result = removeNthFromEnd(l1, 5);
        while (result != null){
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}

