package me.ewriter.chapter8.barista;

public abstract class CaffeineBeverage {

	//不会让子类覆盖这个方法，所以这里是final
	final void prepareRecipe() {
		boilWater();
		brew();
		pourInCup();
		addCondiments();
	}

	// 茶和咖啡的这两个方法不一样，所以这里是抽象的方法，交由子类去处理
	abstract void brew();
  
	abstract void addCondiments();
 
	void boilWater() {
		System.out.println("Boiling water");
	}
  
	void pourInCup() {
		System.out.println("Pouring into cup");
	}
}
