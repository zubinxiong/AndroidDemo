package me.ewriter.chapter2.weatherobservable;

import java.util.Observable;

/**
 * Created by Zubin on 2016/10/12.
 * 这个是使用 Java 自带的 Observable 实现的事件源，但是这个就需要使用继承而不是接口
 */

public class WeatherData extends Observable{

    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    private void measurementsChanged() {
        setChanged();
        notifyObservers();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
}
