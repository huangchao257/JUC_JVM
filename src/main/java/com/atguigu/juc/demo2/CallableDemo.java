package com.atguigu.juc.demo2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        System.out.println("======come in");
        return "hello world";
    }
}

/**
 * 创建线程的第三种方法
 * 实现Callable接口
 * 优点：提供了Object的返回值
 * 缺点：调用麻烦
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());

        Thread thread = new Thread(futureTask, "A");

        thread.start();

        System.out.println(futureTask.get());//一般放在最后一行，会阻塞主线程

        System.out.println(Thread.currentThread().getName()+"\t"+"---end");

    }
}
