package com.example.od;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Scanner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void arrayMerged() {
        System.out.println("数组合并");
        Scanner scanner = new Scanner(System.in);
        System.out.println("收到了一个输入="+scanner.nextInt());
    }
}