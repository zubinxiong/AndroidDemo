package me.ewriter.chapter4.pizzas;

/**
 * Created by Zubin on 2016/10/14.
 */

public class PizzaStore {

    SimplePizzaFactory factory;

    public PizzaStore(SimplePizzaFactory factory) {
        this.factory = factory;
    }

    public Pizza orderPizza(String type) {
//        Pizza pizza = null;
//
//        // 这部分内容会随着类别的增加而增加，所以我们把这部分内容抽象成一个工厂
//        if (type.equals("cheese")) {
//            pizza = new CheesePizza();
//        } else if (type.equals("pepperoni")) {
//            pizza = new PepperoniPizza();
//        } else if (type.equals("clam")) {
//            pizza = new ClamPizza();
//        } else if (type.equals("veggie")) {
//            pizza = new VeggiePizza();
//        }
        Pizza pizza = factory.createPizza(type);

        // 这部分内容不会随着 pizza 的变化改变
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
}
