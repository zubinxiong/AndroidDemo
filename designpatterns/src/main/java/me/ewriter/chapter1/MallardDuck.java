package me.ewriter.chapter1;

/**
 * Created by zubin on 16/8/4.
 */
public class MallardDuck extends Duck {

    public MallardDuck() {
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }

    @Override
    public void display() {

    }
}
