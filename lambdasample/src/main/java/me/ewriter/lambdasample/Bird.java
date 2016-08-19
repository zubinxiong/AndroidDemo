package me.ewriter.lambdasample;

/**
 * Created by Zubin on 2016/8/19.
 */
public class Bird extends Animal {

    public Bird(String name, int age) {
        super(name, age);
    }

    @Override
    public void behavoir() {
        System.out.println("fly");
    }
}
