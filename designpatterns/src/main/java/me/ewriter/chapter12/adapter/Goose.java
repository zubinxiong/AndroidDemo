package me.ewriter.chapter12.adapter;

/**
 * Created by Zubin on 2016/11/11.
 * 鹅这个类，也会叫，但是叫声不一样，所以不 implements Quackable，而是使用适配器模式
 */

public class Goose {

    public void honk() {
        System.out.println("Honk");
    }
}
