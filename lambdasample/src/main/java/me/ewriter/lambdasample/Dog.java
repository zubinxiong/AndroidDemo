package me.ewriter.lambdasample;

/**
 * Created by Zubin on 2016/8/19.
 */
public class Dog extends Animal {

    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public void behavoir() {
        System.out.println("run");
    }
}
