package com.atguigu.juc.demo3;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(3);

       /*
       //抛出异常
        blockingQueue.add("A");
        blockingQueue.add("B");
        blockingQueue.add("C");

        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();*/


        /*
        //返回特殊值
        System.out.println(blockingQueue.offer("a"));
        blockingQueue.offer("b");
        blockingQueue.offer("c");
        System.out.println(blockingQueue.offer("d"));

        System.out.println(blockingQueue.poll());
        blockingQueue.poll();
        blockingQueue.poll();
        blockingQueue.poll();
        System.out.println(blockingQueue.poll());*/

        /*//阻塞线程
        blockingQueue.put("a");
        blockingQueue.put("a");
        blockingQueue.put("a");

        System.out.println(blockingQueue.take());
        blockingQueue.put("a");

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());

        System.out.println(blockingQueue);*/

        //超时退出
        System.out.println(blockingQueue.offer("a", 1L, TimeUnit.SECONDS));
        blockingQueue.offer("a", 1L, TimeUnit.SECONDS);
        blockingQueue.offer("a", 1L, TimeUnit.SECONDS);
        System.out.println(blockingQueue.offer("a", 1L, TimeUnit.SECONDS));


        System.out.println(blockingQueue.poll(1L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(1L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(1L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(1L, TimeUnit.SECONDS));

    }
}
