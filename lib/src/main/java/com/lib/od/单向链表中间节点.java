package com.lib.od;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.util.HashMap;

public class 单向链表中间节点 extends BaseTest{


    @Override
    protected void officialSolution() {
        String headAddress = scanner.next();
        int n = scanner.nextInt();

        HashMap<String,LinkNode> linkNodeHashMap = new HashMap<>();
        for (int i=0;i<n;i++){
            String address = scanner.next();
            String value = scanner.next();
            String nextAddress = scanner.next();
            LinkNode node = new LinkNode();
            node.value = value;
            node.nextAddress = nextAddress;
            linkNodeHashMap.put(address,node);
        }

        LinkNode slow = linkNodeHashMap.get(headAddress);
        LinkNode fast = linkNodeHashMap.get(slow.nextAddress);

        while (fast!=null){
            fast = linkNodeHashMap.get(fast.nextAddress);
            slow = linkNodeHashMap.get(slow.nextAddress);
            if (fast!=null){
                fast = linkNodeHashMap.get(fast.nextAddress);
            }else {
                break;
            }
        }
        System.out.println(slow);
    }

    @Override
    protected void mySolution() {

    }


    class LinkNode{
        String value;
        String nextAddress;
    }
}
