package com.lib.od.链表;

public class ListNode {
    public int value;
    public ListNode next;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        ListNode node = this;
        while (node!=null){
            stringBuilder.append(""+node.value);
            node = node.next;
            if (node!=null){
                stringBuilder.append("-->");
            }
        }
        return stringBuilder.toString();
    }
}
