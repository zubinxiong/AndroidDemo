package me.ewriter.chapter7.ducks;

public class MallardDuck implements Duck {
	// 绿头鸭,鸭子接口的具体实现
	public void quack() {
		System.out.println("Quack");
	}
 
	public void fly() {
		System.out.println("I'm flying");
	}
}
