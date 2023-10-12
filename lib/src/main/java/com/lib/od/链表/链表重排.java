package com.lib.od.链表;

import java.util.Deque;
import java.util.LinkedList;

public class 链表重排 {

    public void reorderList(ListNode head) {
        Deque<ListNode> deque = new LinkedList<>();
        ListNode p = head;
        while (p!=null){
            deque.offerFirst(p);
            p = p.next;
            p.next = null;
        }

        ListNode h=null;
        while (!deque.isEmpty()){
            ListNode node1 = deque.pollLast();
            if (h != null){
                h.next = node1;
            }
            if (!deque.isEmpty()){
                ListNode node = deque.pollFirst();
                //node.next = null;
                node1.next = node;
            }

            h = node1.next;

        }

    }

}
