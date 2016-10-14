package me.ewriter.chapter5.dcl;

/**
 * Created by Zubin on 2016/10/14.
 */

public class Singleton {

    // volatile 可以被看做轻量级的 synchronized， 不具备原子性
    // http://www.ibm.com/developerworks/cn/java/j-jtp06197.html
    // volatile 确保党instance变量被初始化后，多个线程能正确处理instance变量
    private volatile static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            // 如果不存在就进入同步区
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
