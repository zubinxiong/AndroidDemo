package me.ewriter.chapter9.dinermerger;

import java.util.ArrayList;

/**
 * Created by Zubin on 2016/10/24.
 */

public class PancakeHouseMenuIterator implements Iterator {

    ArrayList items;
    int position = 0;

    public PancakeHouseMenuIterator(ArrayList items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        if (position >= items.size()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object next() {
        Object object = items.get(position);
        position = position + 1;
        return object;
    }
}
