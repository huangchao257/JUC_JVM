package com.atguigu.juc.demo3;

/**
 * 死锁案例
 *    排查死锁  1、在Terminal窗口输入Java命令  jps -l
 *             2、输入jstack <pid>
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        Object lockA = new Object();
        Object lockB = new Object();
        new Thread(() -> {
            synchronized (lockA){
                System.out.println(Thread.currentThread().getName() + ":\t持有A锁，希望获取B锁");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockB){
                    System.out.println(Thread.currentThread().getName() + ":\t成功获取B锁");
                }
            }
        },"A").start();

        new Thread(() -> {
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + ":\t持有B锁，希望获取A锁");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockA){
                    System.out.println(Thread.currentThread().getName() + ":\t成功获取A锁");
                }
            }
        },"B").start();

    }
}
