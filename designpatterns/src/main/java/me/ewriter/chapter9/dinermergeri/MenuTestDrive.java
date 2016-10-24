package me.ewriter.chapter9.dinermergeri;

import java.util.*;

/**
 * 这个相比dinermerger 包下面的方法相比较的话，区别在于这里
 * 使用的是系统的Iterator 方法，其他的并没有明显区别
 *
 * 迭代器模式的好处就是提供了一个方法顺序访问聚合对象中的元素
 * 而又不暴露其内部的表示，即我们不知道他内部到底是数组还是list
 */
public class MenuTestDrive {
	public static void main(String args[]) {
		PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
		DinerMenu dinerMenu = new DinerMenu();
		Waitress waitress = new Waitress(pancakeHouseMenu, dinerMenu);
		waitress.printMenu();
		waitress.printVegetarianMenu();

		System.out.println("\nCustomer asks, is the Hotdog vegetarian?");
		System.out.print("Waitress says: ");
		if (waitress.isItemVegetarian("Hotdog")) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
		System.out.println("\nCustomer asks, are the Waffles vegetarian?");
		System.out.print("Waitress says: ");
		if (waitress.isItemVegetarian("Waffles")) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}

	}
}
