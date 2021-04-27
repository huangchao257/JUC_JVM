package com.atguigu.juc.demo2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 1.问题现象
 * 多线程下，读写ArraysList集合出错
 * 2.问题原因
 * ArraysList不是线程安全的
 * 3.解决办法
 * 3.1使用Vector代替（不使用）
 * 3.2Collections.synchronizedList(new ArrayList<>())（不使用）
 * 3.3写时复制技术 new CopyOnWriteArrayList()/new CopyOnWriteArraySet<>()/new ConcurrentHashMap<>()
 * 4.优化意见（不犯第二次）
 * 读多写少时候，有用CopyOnWriteArrayList类解决线程安全问题
 */
/**笔记
 * 写时复制
 CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，
 而是先将当前容器Object[]进行Copy，
 复制出一个新的容器Object[] newElements，然后新的容器Object[] newElements里添加元素，添加完元素之后，
 再将原容器的引用指向新的容器 setArray(newElements);。这样做的好处是可以对CopyOnWrite容器进行并发的读，
 而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器
 */
public class NotSafeDemo {
    public static void main(String[] args) {

        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 6));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }

        //notSafeSet();
        //noSafeArrayList();

    }

    private static void notSafeSet() {
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 6));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    private static void safeArrayList() {
        List<String> list = new CopyOnWriteArrayList();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 6));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
