package me.ewriter.chapter9.dinermerger;

/**
 * Created by Zubin on 2016/10/24.
 * 实现餐厅的具体迭代器，里面是数组
 */

public class DinerMenuIterator implements Iterator {
    MenuItem[] items;
    int position = 0;

    public DinerMenuIterator(MenuItem[] items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        if (position >= items.length || items[position] == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object next() {
        if (items.length > position) {
            MenuItem menuItem = items[position];
            position = position + 1;
            return menuItem;
        } else {
            return null;
        }
    }
}
