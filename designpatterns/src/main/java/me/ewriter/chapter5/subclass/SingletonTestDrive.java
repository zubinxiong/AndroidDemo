package me.ewriter.chapter5.subclass;

public class SingletonTestDrive {
	public static void main(String[] args) {
		Singleton foo = CoolerSingleton.getInstance();
		Singleton bar = HotterSingleton.getInstance();

		// 这两个 Singleton 返回的都是同一个对象
		// me.ewriter.chapter5.subclass.Singleton@1540e19d
		System.out.println(foo);
		System.out.println(bar);
 	}
}
