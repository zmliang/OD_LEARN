package com.lib.od.字符串;

import java.util.ArrayList;
import java.util.List;

public class N字型变换 {

    public String convert(String s, int numRows) {

        char[] chars = s.toCharArray();
        List<StringBuilder> stringBuilders = new ArrayList<>();
        for (int i=0;i<numRows;i++){
            stringBuilders.add(new StringBuilder());
        }
        int index = 0;
        int down = 1;
        int up = -1;
        int step = down;
        for (int i=0;i< chars.length;i++){
            char c = chars[i];
            stringBuilders.get(index).append(c);

            if (index == numRows-1){
                step = up;
            }else if (index == 0){
                step = down;
            }
            index+=step;
        }

        String result = "";
        for (StringBuilder sb:stringBuilders){
            result+=sb.toString();
        }
        return result;

    }

}
