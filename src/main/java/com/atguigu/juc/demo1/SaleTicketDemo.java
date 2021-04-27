package com.atguigu.juc.demo1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket {//资源类
    private int num = 30;
    Lock lock = new ReentrantLock();//可重入锁，默认非公平锁

    public void sale() {
        lock.lock();
        try {
            if (num > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了：" + (num--) + "，还有：" + num + "票");
            }
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 如何编写企业级需要的工程化代码？
 *  1 在高内聚低耦合的前提下，“线程  操作  资源类”
 *
 */
public class SaleTicketDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {for (int i = 0; i < 31; i++) ticket.sale();}, "A").start();
        new Thread(() -> {for (int i = 0; i < 31; i++) ticket.sale();}, "B").start();
        new Thread(() -> {for (int i = 0; i < 31; i++) ticket.sale();}, "C").start();
    }
}
