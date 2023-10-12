package com.lib.od;

import java.util.Scanner;

public abstract class BaseTest {
    protected Scanner scanner = new Scanner(System.in);
    protected abstract void officialSolution();

    protected abstract void mySolution();
}
