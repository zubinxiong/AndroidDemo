package me.ewriter.chapter4.pizzaaf;

public class CheesePizza extends Pizza {
	PizzaIngredientFactory ingredientFactory;

	// Pizza 接收原料作为构造函数
	public CheesePizza(PizzaIngredientFactory ingredientFactory) {
		this.ingredientFactory = ingredientFactory;
	}
 
	void prepare() {
		System.out.println("Preparing " + name);
		dough = ingredientFactory.createDough();
		sauce = ingredientFactory.createSauce();
		cheese = ingredientFactory.createCheese();
	}
}
