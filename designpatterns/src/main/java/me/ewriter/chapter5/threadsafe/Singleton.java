package me.ewriter.chapter5.threadsafe;

/**
 * Created by Zubin on 2016/10/14.
 */

public class Singleton {

    private static Singleton instance;

    private Singleton() {}

    // 加入 synchronized 关键字，每个线程进入的时候需要等待其他线程离开这个方法
    // 同步会降低性能，同时实际上只有第一次需要同步，但是之后每次都要执行同步方法，造成了性能的损耗
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
