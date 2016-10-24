package me.ewriter.chapter7.iterenum;

import java.util.*;

/***
 * 将枚举适配到迭代器
 */
public class EnumerationIteratorTestDrive {
	public static void main (String args[]) {
		Vector v = new Vector(Arrays.asList(args));
		Iterator iterator = new EnumerationIterator(v.elements());
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
}
