package com.app.startup;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SortUtil {

    public static void sort(List<AbsTask> taskList){
        if (taskList.size()<=1){
            return;
        }
        Collections.sort(taskList, new Comparator<AbsTask>() {
            @Override
            public int compare(AbsTask a, AbsTask b) {
                return a.priority() - b.priority();
            }
        });
    }

}
