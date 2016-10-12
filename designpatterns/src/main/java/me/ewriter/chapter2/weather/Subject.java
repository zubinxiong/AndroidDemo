package me.ewriter.chapter2.weather;

/**
 * Created by Zubin on 2016/10/12.
 *
 * 事件源, 类似于 RxJava 里面的 Observable
 */

public interface Subject {

    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();
}
