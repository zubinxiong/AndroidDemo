package me.ewriter.chapter2.weather;

/**
 * Created by Zubin on 2016/10/12.
 * 所有的气象组件都要实现这样一个接口，因为在通知观察者时，有了一个共同的接口
 */

public interface Observer {
    public void update(float temp, float humidity, float pressure);
}

