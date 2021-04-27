package com.atguigu.juc.demo3;

import java.util.concurrent.CountDownLatch;

/**
 * 题目：要求如下
 *   多个线程协同干活，要求到最后一个线程为零后，才能启动主线程。
 *   做一个减少一个，清零后才能启动主线程，逐个做减法。
 *
 * CountDownLatch主要有两个方法，当一个或多个线程调用await方法时，这些线程会阻塞。
 * 其它线程调用countDown方法会将计数器减1(调用countDown方法的线程不会阻塞)，
 * 当计数器的值变为0时，因await方法阻塞的线程会被唤醒，继续执行。
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for(int i = 1; i <= 5; i++){
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + ":\t倒计时：" + (finalI + 1));
                } finally {
                    countDownLatch.countDown();
                }
            },String.valueOf(i)).start();
        }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + ":\t火箭发射");
    }
}
