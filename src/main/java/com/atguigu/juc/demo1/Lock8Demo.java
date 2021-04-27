package com.atguigu.juc.demo1;

import java.util.concurrent.TimeUnit;

class Phone {

    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----Email");
    }

    public synchronized void sendSMS() {
        System.out.println("-----SMS");
    }

    public void hello() {
        System.out.println("-----hello");
    }
}
/**
 * 题目：谈谈你对多线程锁的理解
 * 线程   操作  资源类  8锁
 *
 * 1 标准访问的情况下，请问先打印邮件还是短信                       Email
 * 2 在邮件方法暂停4秒钟，请问先打印邮件还是短信                    Email
 * 3 新增普通的hello方法，请问先打印邮件还是hello                  hello
 * 4 两部手机，请问先打印邮件还是短信                              SMS
 * 5 两个静态同步方法，同一部手机，请问先打印邮件还是短信             Email
 * 6 两个静态同步方法，2部手机，请问先打印邮件还是短信               Email
 * 7 1个静态同步方法,1个普通同步方法,1部手机,请问先打印邮件还是短信    SMS
 * 8 1个静态同步方法,1个普通同步方法,2部手机,请问先打印邮件还是短信    SMS
 *
 * 注意：要关注synchronized的锁对象，对于同步方法而言如果是非静态的则锁对象是this，是对象锁，
 *                              如果是静态的则锁对象是字节码文件，是类锁
 *
 *   1-2
 *   一个对象里面如果有多个synchronized方法，某一个时刻内，只要一个线程去调用其中的一个synchronized方法了，
 *  其它的线程都只能等待，换句话说，某一个时刻内，只能有唯一的一个线程去访问这些synchronized方法
 *  锁的是当前对象this，被锁定后，其它的线程都不能进入到当前对象的其它的synchronized方法
 *
 *  3-4
 *  加个普通方法后发现和同步锁无关
 *  换成两个对象后，不是同一把锁了，情况立刻变化。
 *
 *  5-6
 *  都换成静态同步方法后，情况又变化
 *  若是普通同步方法，new     this,   具体的一部部手机,所有的普通同步方法用的都是同一把锁——实例对象本身，
 *  若是静态同步方法，static  Class ，唯一的一个模板
 *
 *  7-8
 *  当一个线程试图访问同步代码时它首先必须得到锁，退出或抛出异常时必须释放锁。
 * 所有的普通同步方法用的都是同一把锁——实例对象本身，就是new出来的具体实例对象本身,本类this
 *  也就是说如果一个实例对象的普通同步方法获取锁后，该实例对象的其他普通同步方法必须等待获取锁的方法释放锁后才能获取锁。
 *  *
 *  所有的静态同步方法用的也是同一把锁——类对象本身，就是我们说过的唯一模板Class
 *  具体实例对象this和唯一模板Class，这两把锁是两个不同的对象，所以静态同步方法与普通同步方法之间是不会有竞态条件的
 *  但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁。
 */
public class Lock8Demo {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> {
            phone.sendEmail();
        }, "t1").start();

        Thread.sleep(100);

        new Thread(() -> {
            phone.sendSMS();
            //phone.hello();
            //phone2.sendSMS();
        },"t2").start();
    }
}
