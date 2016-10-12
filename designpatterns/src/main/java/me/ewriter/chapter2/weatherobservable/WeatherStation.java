package me.ewriter.chapter2.weatherobservable;

/**
 * Created by Zubin on 2016/10/12.
 * 这个和上面weather 包下面的区别主要就是这里的观察者模式使用的是java 里面自带的Observable 和 observer
 * 但是这种方式有个缺点就是必须使用extends 继承，使用还是有不方便
 */

public class WeatherStation {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        //其他几个观察者类似，就不再重复写了
        CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
    }
}
