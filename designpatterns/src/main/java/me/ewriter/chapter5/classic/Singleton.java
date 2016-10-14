package me.ewriter.chapter5.classic;

/**
 * Created by Zubin on 2016/10/14.
 *
 * 这个是最经典的单例模式实现
 */

public class Singleton {

    private static Singleton uniqueInstance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Singleton();
        }

        return uniqueInstance;
    }
}
