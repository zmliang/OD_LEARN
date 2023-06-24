package com.lib.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class 优秀学员统计 extends BaseTest{

    public int getResult(int[][] dayIds){

        Map<Integer,Employee> map = new HashMap<>();
        for (int i=0;i< dayIds.length;i++){
            for (int j=0;j<dayIds[i].length;j++){
                Employee employee = map.getOrDefault(dayIds[i][j],null);
                if (employee == null){
                    employee.firstDay = i;
                    employee.count = 1;
                    employee.id = dayIds[i][j];
                }else {
                    employee.count++;
                }
            }
        }
        List<Employee> list = map.values().stream().collect(Collectors.toList());
        list.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee a, Employee b) {

                return 0;
            }
        });

        return 0;
    }

    @Override
    protected void officialSolution() {
        int maxId = scanner.nextInt();
        int[] dayEmploy = new int[30];
        for (int i=0;i<30;i++){
            dayEmploy[i] = scanner.nextInt();
        }
        int[][] dayId = new int[30][];
        for (int i=0;i<30;i++){
            int count = dayEmploy[i];
            int[] ids = new int[count];
            for (int j=0;j<count;j++){
                dayId[i][j] = scanner.nextInt();
            }
        }



    }

    @Override
    protected void mySolution() {

    }


    public static class Employee{
        public int id;
        public int count;
        public int firstDay;
        public Employee(int i,int c,int f){
            this.id = i;
            this.count = c;
            this.firstDay = f;
        }
    }
}
