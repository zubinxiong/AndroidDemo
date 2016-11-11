package me.ewriter.chapter12.decorator;

/**
 * Created by Zubin on 2016/11/11.
 * 新增了一个 number 用于统计次数
 */

public class QuackCounter implements Quackable {

    Quackable duck;
    static int numberOfQuack;

    public QuackCounter(Quackable duck) {
        this.duck = duck;
    }

    @Override
    public void quack() {
        duck.quack();
        numberOfQuack++;
    }

    public static int getQuacks() {
        return numberOfQuack;
    }

    @Override
    public String toString() {
        return duck.toString();
    }
}
