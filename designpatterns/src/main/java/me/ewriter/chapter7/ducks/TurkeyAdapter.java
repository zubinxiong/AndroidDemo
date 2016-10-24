package me.ewriter.chapter7.ducks;

/**
 * Created by Zubin on 2016/10/24.
 * 用火鸡来冒充鸭子,
 */

public class TurkeyAdapter implements Duck {
    Turkey turkey;

    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
    }

    @Override
    public void fly() {
        // 因为火鸡只能飞一段距离，要模拟鸭子的飞行要5次调用
        for (int i = 0; i < 5; i++) {
            turkey.fly();
        }
    }
}
