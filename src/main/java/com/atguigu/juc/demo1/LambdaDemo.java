package com.atguigu.juc.demo1;

//函数式接口：只有一个抽象方法的接口
//    显式：接口上加了@FunctionalInterface 隐式：只有一个抽象方法且未添加@FunctionalInterface注解
@FunctionalInterface
interface Foo{
    int add(int x, int y);

    //jdk1.8之后Java开始支持在接口中写方法体 （static或default修饰的方法）
    default int sub(int x, int y) {
        return x - y;
    }

}

/**
 * lambda Express
 *  前提：函数式接口(内部只有一个方法) ---> lambda Express
 *  1 拷贝小括号，写死右箭头，落地大括号
 */
public class LambdaDemo {
    public static void main(String[] args) {
        Foo foo = (x,y) -> {return x + y;};
        System.out.println(foo.add(10, 5));

        System.out.println(foo.sub(10, 5));
    }
}
