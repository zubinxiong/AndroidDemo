package me.ewriter.chapter2.weather;

/**
 * Created by Zubin on 2016/10/12.
 *  这个是使用自己实现的Subejct 和 Observer 来实现观察者模式
 */

public class WeatherStation {

    public static void main(String[] args) {
        // 事件源
        WeatherData weatherData = new WeatherData();


        // 订阅者
        CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(82, 70, 29.2f);
        weatherData.setMeasurements(78, 90, 29.2f);
    }
}
