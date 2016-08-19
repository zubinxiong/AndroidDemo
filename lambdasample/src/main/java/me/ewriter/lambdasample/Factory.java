package me.ewriter.lambdasample;

/**
 * Created by Zubin on 2016/8/19.
 */
public interface Factory<T extends Animal> {
    T create(String name, int age);
}
