package com.atguigu.juc.demo3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Resources {
    Map<String,String> map = new ConcurrentHashMap<>();
    Lock lock = new ReentrantLock();
    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public void write(String key,String value)
    {
        rwl.writeLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---write");
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t"+"write---");
        }finally {
            rwl.writeLock().unlock();
        }
    }

    public void read(String key)
    {
        rwl.readLock().lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+"\t"+"---read");
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t"+"read---"+result);
        }finally {
            rwl.readLock().unlock();
        }
    }

}

/**
 * 读写锁
 *
 * 对于同一个资源，我们涉及多线程的操作，有读，有写，交替进行。
 * 为了保证读写的数据一致性。
 *
 * 读 读 可共享
 * 读 写 不共享
 * 写 写 不共享
 * 读的时候希望高并发同时进行，可以共享，可以多个线程同时操作进行中.....
 * 写的时候为了保证数据一致性，需要独占排它。
 *
 *
 * 题目：10个线程读，10个线程写入，操作同一个资源
 *
 */
public class ReadWriterLockDemo {
    public static void main(String[] args) {
        Resources resources = new Resources();

        for(int i = 0; i < 10; i++){
            int finalI = i;
            new Thread(() -> {
                resources.write(finalI+"",finalI +"");
            },String.valueOf(i)).start();
        }

        for(int i = 0; i < 10; i++){
            int finalI = i;
            new Thread(() -> {
                resources.read(finalI +"");
            },String.valueOf(i)).start();
        }
    }
}
