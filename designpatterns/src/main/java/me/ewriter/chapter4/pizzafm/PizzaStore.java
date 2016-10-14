package me.ewriter.chapter4.pizzafm;

/**
 * Created by Zubin on 2016/10/14.
 *
 * 现在 PizzaStore 是抽象的，让制作局限于pizzaStore 同时各个加盟店能自己增加风味处理
 */

public abstract class PizzaStore {

    // 现在实例化的方法移动到一个方法中，这个就是一个工厂
    abstract Pizza createPizza(String item);

    // PizzaStore 的子类在 createPizza 方法中处理对象的实例化
    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);

        System.out.println("--- Making a " + pizza.getName() + " ---");

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
}
