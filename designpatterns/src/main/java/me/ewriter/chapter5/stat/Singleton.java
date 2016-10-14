package me.ewriter.chapter5.stat;

/**
 * Created by Zubin on 2016/10/14.
 */

public class Singleton {

    //  这种做法，依赖 JVM 在加载这个类的时候创建唯一的单例
    // JVM 保证了访问之前一定先创建了此实例
    // 但是这种只适用于构造函数不是很复杂的情况
    private static Singleton instance = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return instance;
    }
}
