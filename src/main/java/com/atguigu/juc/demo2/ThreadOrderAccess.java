package com.atguigu.juc.demo2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResources {//资源类
    private int flag = 1;//标志位
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void printNum() throws InterruptedException {
        lock.lock();
        try {
            //1.判断
            while (flag != 1 && "A".equals(Thread.currentThread().getName())) {
                condition1.await();
            }
            while (flag != 2 && "B".equals(Thread.currentThread().getName())) {
                condition2.await();
            }
            while (flag != 3 && "C".equals(Thread.currentThread().getName())) {
                condition3.await();
            }
            //2.干活
            for (int i = 0; i < flag * 5; i++) {
                System.out.println(Thread.currentThread().getName() + ":\t" + (i + 1));
            }
            //修改标志位
            if (flag == 3) {
                flag = 1;
            } else {
                flag++;
            }
            //3.通知
            switch (flag) {
                case 1:
                    condition1.signalAll();
                    break;
                case 2:
                    condition2.signalAll();
                    break;
                case 3:
                    condition3.signalAll();
                default:
                    break;
            }
        } finally {
            lock.unlock();
        }
    }

    public void print5() throws InterruptedException {
        lock.lock();
        try {
            //1 判断
            while (flag != 1) {
                condition1.await();
            }
            //2 干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3 通知
            flag = 2;
            condition2.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void print10() throws InterruptedException {
        lock.lock();
        try {
            //1 判断
            while (flag != 2) {
                condition2.await();
            }
            //2 干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3 通知
            flag = 3;
            condition3.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void print15() throws InterruptedException {
        lock.lock();
        try {
            //1 判断
            while (flag != 3) {
                condition3.await();
            }
            //2 干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //3 通知
            flag = 1;
            condition1.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 * <p>
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * ......三个线程来10轮
 * <p>
 * 精确通知，按照顺序走
 * <p>
 * 1 线程 操作 资源类
 * 2 判断、干活、通知
 * 3 while
 * 4 精确通知的标志位修改
 */
public class ThreadOrderAccess {
    public static void main(String[] args) {

        ShareResources resources = new ShareResources();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    resources.printNum();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    resources.printNum();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    resources.printNum();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
    }
}
