package com.atguigu.juc.demo2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AirCondition {//空调，资源类
    private int m = 0;

    Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();

    public void add() throws InterruptedException {
        lock.lock();
        try{
            //1.判断
            while (m != 0){
                condition.await();
            }
            //2.干活
            m++;
            System.out.println(Thread.currentThread().getName() + ":\t" + m);
            //3.通知
            condition.signal();
        }finally{
            lock.unlock();
        }
    }

    public void sub() throws InterruptedException {
        lock.lock();
        try{
            //1.判断
            while (m == 0){
                condition.await();
            }
            //2.干活
            m--;
            System.out.println(Thread.currentThread().getName() + ":\t" + m);
            //3.通知
            condition.signal();
        }finally{
            lock.unlock();
        }
    }

    /*public synchronized void add() throws InterruptedException {
        //1.判断
        while (m != 0){
            this.wait();
        }
        //2.干活
        m++;
        System.out.println(Thread.currentThread().getName() + ":\t" + m);
        //3.通知
        this.notify();
    }

    public synchronized void sub() throws InterruptedException {
        //1.判断
        while (m == 0){
            this.wait();
        }
        //2.干活
        m--;
        System.out.println(Thread.currentThread().getName() + ":\t" + m);
        //3.通知
        this.notify();
    }*/
}

/**
 * 1.在高内聚、低耦合的情况下：线程 操作 资源类
 * 2.线程间通信：判断、干活、通知
 * 3.避免虚假唤醒 使用while
 */
public class ProdConsumerDemo {
    public static void main(String[] args) {
        AirCondition air = new AirCondition();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                    air.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(200);
                    air.sub();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(300);
                    air.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(400);
                    air.sub();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}
