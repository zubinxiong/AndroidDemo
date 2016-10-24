package me.ewriter.chapter9.dinermerger;

/**
 * Created by Zubin on 2016/10/24.
 * 之所以要自己新建Iterator 是为了自己熟悉
 * 实际使用的时候可以直接使用java.util.Iterator这个类
 */

public interface Iterator {
    boolean hasNext();

    Object next();
}
