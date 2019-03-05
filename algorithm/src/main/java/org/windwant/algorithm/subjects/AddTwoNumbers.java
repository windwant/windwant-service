package org.windwant.algorithm.subjects;

/**
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * Created by Administrator on 19-2-25.
 */
public class AddTwoNumbers {
    /**
     * 链表相应位置依次相加，最后处理进位
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode curr = null;
        while (l1 != null || l2 != null){
            if(l1 != null && l2 != null){
                ListNode node = new ListNode(l1.val + l2.val);
                if(curr == null && head == null) head = node;
                curr = initNode(curr, node);
            }else{
                curr = initNode(curr, new ListNode(l1 != null?l1.val:l2.val));
            }
            l1 = l1 != null?l1.next:null;
            l2 = l2 != null?l2.next:null;
        }
        curr = head;
        while (curr != null){
            if(curr.val >= 10){
                curr.val -= 10;
                if(curr.next == null){
                    curr.next = new ListNode(1);
                }else {
                    curr.next.val += 1;
                }
            }
            curr = curr.next;

        }
        curr = null;
        return head;
    }

    public static ListNode initNode(ListNode curr, ListNode newNode){
        if(curr != null){
            curr.next = newNode;
        }
        curr = newNode;
        return curr;
    }

    public static void main(String[] args) {
        //构造测试链表
        ListNode l1 = new ListNode(2);
        ListNode l12 = new ListNode(4);
        l1.next = l12;
        ListNode l13 = new ListNode(3);
        l12.next = l13;

        ListNode l2 = new ListNode(5);
        ListNode l22 = new ListNode(6);
        l2.next = l22;
        ListNode l23 = new ListNode(4);
        l22.next = l23;

        ListNode result =addTwoNumbers(l1, l2);
        while (result != null){
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}