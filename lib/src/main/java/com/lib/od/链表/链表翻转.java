package com.lib.od.链表;

import java.util.LinkedList;

public class 链表翻转 {

    /**
     * 链表翻转
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null){
            return head;
        }
        ListNode p1 = head;
        ListNode h = null;

        while (p1!=null){
            ListNode node = p1;
            p1 = p1.next;
            node.next = h;
            h = node;
        }

        return h;

    }

    /**
     * 链表的中间节点
     * @param head
     * @return
     */
    public ListNode middleNode(ListNode head) {

        if (head == null || head.next == null){
            return head;
        }

        ListNode slow = head;
        ListNode fast =head;

        while (fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast == null){
            return slow.next;
        }
        return slow;

    }

    /**
     * 相交链表
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
        while (p1!=p2){
            p1 = p1 == null ? headA : p1.next;
            p2 = p2 == null ? headB : p2.next;
            if (p1 == p2 && p1 ==null && p2 == null){
                break;
            }
        }
        return p2;
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null){
            return false;
        }

        ListNode slow = head;
        ListNode fast =head.next;

        while (slow!=fast){
            if (fast == null || fast.next == null){
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }


    public ListNode oddEvenList(ListNode head) {
        ListNode p1 = head;
        ListNode p2 = head.next;
        ListNode p = p2;

        ListNode h1 = new ListNode();
        ListNode h2 = new ListNode();


        while (p1!=null && p2!=null){
            h1.next = p1;
            h1 = p1;

            h2.next = p2;
            h2 = p2;

            if (p1!=null && p1.next!=null){
                p1 = p1.next.next;
            }
            if (p2!=null && p2.next!=null){
                p2 = p2.next.next;
            }
        }


        h1.next = p;

        return head;

    }


    public static ListNode createList(){

        int[] array = new int[]{1,2,3,4,5};
        ListNode head = new ListNode();
        ListNode p = head;
        for (int i=0;i<array.length;i++){
            ListNode node = new ListNode();
            node.value = array[i];
            p.next = node;
            p = node;
        }

        return head.next;

    }

    public static void print(ListNode head){
        ListNode p = head;
        System.out.println();
        System.out.println();
        while (p!=null){
            System.out.print(p.value);
            p = p.next;
            if (p!=null){
                System.out.print(" -->");
            }
        }
        System.out.println();
        System.out.println();
    }

}
