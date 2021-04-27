package com.atguigu.jvm;

public class JVMDemo {
    public static void main(String[] args) {
//        init();
        //java.lang.StackOverflowError:堆栈溢出错误
//        m1();

    }

    public static void m1(){
        m1();
    }

    private static void init() {
        Object object = new Object();
        System.out.println("classLoader = " + object.getClass().getClassLoader());

        JVMDemo jvmDemo = new JVMDemo();
        System.out.println("classLoader = " + jvmDemo.getClass().getClassLoader().getParent().getParent());
        System.out.println("classLoader = " + jvmDemo.getClass().getClassLoader().getParent());
        System.out.println("classLoader = " + jvmDemo.getClass().getClassLoader());
    }
}
