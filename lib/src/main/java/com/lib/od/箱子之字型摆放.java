package com.lib.od;

import java.util.ArrayList;
import java.util.List;

public class 箱子之字型摆放 {

    public void put(String str,int n){

        List<StringBuilder> list = new ArrayList<>(n);
        for (int i=0;i<n;i++){
            list.add(new StringBuilder());
        }
        boolean back = false;
        for (int i=0;i<str.length();i++){
            char c = str.charAt(i);
            int pos = i%n;

            if (i!=0 && pos == 0){
                back = !back;
            }
            if (back){
                pos = n-pos-1;
            }

            StringBuilder sb =  list.get(pos);
            if (sb == null){
                sb = new StringBuilder();
            }
            sb.append(c);
        }
        System.out.println(list.toString());


    }

}
