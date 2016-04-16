package com.fr.design.data;

/**
 * Created by tony on 2016/4/15.
 */
public class MyTest {
    public static void main(String[] args) {
        B b=new B();
        b.f();
    }
}


class A{
    public void f(){
        System.out.println("a");
    }
}

class B extends A{
    public void f(){
        System.out.println("b");
    }
}