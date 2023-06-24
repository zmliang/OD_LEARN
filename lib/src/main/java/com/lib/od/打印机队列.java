package com.lib.od;

import java.security.PublicKey;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class 打印机队列 extends BaseTest{


    @Override
    protected void officialSolution() {
        Map<Integer, TreeSet<PrintFile>> maches = new HashMap<>();
        for (int i=0;i<5;i++){
            TreeSet<PrintFile> value = new TreeSet<>(new Comparator<PrintFile>() {
                @Override
                public int compare(PrintFile printFile, PrintFile t1) {
                    return 0;
                }
            });
            maches.put(i,value);
        }
        int count = scanner.nextInt();

        int index = 0;
        for (int i=0;i<count;i++){
            String line = scanner.nextLine();
            String[] infors = line.split(" ");
            if (infors[0].equals("IN")){
                index++;
                PrintFile file = new PrintFile();
                file.id = index;
                file.p = Integer.parseInt(infors[1]);
                file.proprity = Integer.parseInt(infors[2]);
                maches.get(file.p).add(file);
            }else {
                int p = Integer.parseInt(infors[1]);
                TreeSet<PrintFile> set = maches.get(p);
                PrintFile file = set.pollFirst();
                System.out.println(file.proprity);
            }
        }
    }

    @Override
    protected void mySolution() {

    }


    public static class PrintFile{
        public int id;
        public int p;
        public int proprity;
    }

}
