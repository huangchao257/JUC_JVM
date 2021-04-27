package com.atguigu.juc.demo3;

import java.util.concurrent.*;

/**
 * 实战中使用自己定义的线程池不用jdk提供的
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 5, 2L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 1; i <= 8; i++) {
                final int tmpI = i;
                threadPool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 处理业务" + "\t 顾客: " + tmpI);
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }


    }

    private static void cacheThreadPool() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 20; i++) {
                int finalI = i;
                threadPool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t" + "给" + finalI + "号顾客办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //threadPool.shutdownNow();//结束当前所有正在运行的线程 返会一个正在运行的线程名list集合 关闭线程池
            threadPool.shutdown();//等待当前正在运行的线程结束后关闭线程池
        }
    }

    public static void singleThreadExecutor() {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();//一池1线程

        try {
            //模拟20个顾客来银行办理业务，目前窗口有1个工作人员
            for (int i = 1; i <= 20; i++) {
                int finalI = i;
                threadPool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t" + "给" + finalI + "号顾客办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public static void fixedThreadPool() {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);//固定数量的线程池

        try {
            //模拟20个顾客来银行办理业务，目前窗口有5个工作人员
            for (int i = 1; i <= 20; i++) {
                int finalI = i;
                threadPool.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t" + "给" + finalI + "号顾客办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
