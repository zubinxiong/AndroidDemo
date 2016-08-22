package me.ewriter.chapter1;

/**
 * Created by zubin on 16/8/4.
 */
public abstract class Duck {
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    public Duck() {

    }

    public abstract void display();

    // 下面这两个是委托给行为类
    public void performFly() {
        flyBehavior.fly();
    }

    public void performQuack() {
        quackBehavior.quack();
    }

    public void swim() {
        System.out.println("All duck float, even decoys!");
    }
}
