package me.ewriter.chapter4.pizzaaf;

/**
 * Created by Zubin on 2016/10/14.
 *
 *  这个接口负责创建所有的原料，每一个方法对应着一个原料
 */

public interface PizzaIngredientFactory {

    public Dough createDough();
    public Sauce createSauce();
    public Cheese createCheese();
    public Veggies[] createVeggies();
    public Pepperoni createPepperoni();
    public Clams createClam();

}
