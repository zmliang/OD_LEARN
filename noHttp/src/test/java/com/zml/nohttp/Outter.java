package com.zml.nohttp;

public class Outter {

    public Outter(){

    }

    public void callInner(){
        new Test()._test(new Inner());
    }

    private String name = "zmliang";

    public final class Inner{

        public Inner(){

        }

        public String name(){
            return name;
        }
    }
}
