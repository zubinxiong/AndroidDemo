package me.ewriter.chapter2.weatherobservable;

import java.util.*;

/**
 * Created by Zubin on 2016/10/12.
 * 这里的观察者也是使用Java 自带的观察者，而不是自己实现的Observer
 */

public class CurrentConditionsDisplay implements java.util.Observer, me.ewriter.chapter2.weather.DisplayElement {

    Observable observable;
    private float temperature;
    private float humidity;

    public CurrentConditionsDisplay(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Current conditions: " + temperature
                + "F degrees and " + humidity + "% humidity");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) o;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            display();
        }
    }
}
