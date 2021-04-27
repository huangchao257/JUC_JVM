package com.atguigu.juc.demo3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket{
    int num = 30;
    Lock lock = new ReentrantLock();

    public void print() {
        lock.lock();
        try{
            if (num > 0) {
                Thread.sleep(200);
                System.out.println(Thread.currentThread().getName() + "\tmai chu di " + num-- +"ticket");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
}

public class TestDemo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        Ticket ticket = new Ticket();
        try {
            for (int i = 0; i < 3; i++) {
                threadPool.submit(()->{
                    for (int j = 0; j < 30; j++) {
                        ticket.print();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
