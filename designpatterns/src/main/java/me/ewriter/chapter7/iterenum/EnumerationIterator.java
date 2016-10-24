package me.ewriter.chapter7.iterenum;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by Zubin on 2016/10/24.
 *
 */

public class EnumerationIterator implements Iterator {

    Enumeration enumeration;

    public EnumerationIterator(Enumeration enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    @Override
    public Object next() {
        return enumeration.nextElement();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
