package me.ewriter.chapter1;

/**
 * Created by zubin on 16/8/4.
 */
public class FlyNoWay implements FlyBehavior {

    // 飞行行为的实现，给不会飞的鸭子用
    @Override
    public void fly() {
        System.out.println("I can't flying");
    }
}
