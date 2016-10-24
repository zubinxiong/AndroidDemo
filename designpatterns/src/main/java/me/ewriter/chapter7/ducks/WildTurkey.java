package me.ewriter.chapter7.ducks;

public class WildTurkey implements Turkey {
	// 火鸡的具体实现
	public void gobble() {
		System.out.println("Gobble gobble");
	}
 
	public void fly() {
		System.out.println("I'm flying a short distance");
	}
}
