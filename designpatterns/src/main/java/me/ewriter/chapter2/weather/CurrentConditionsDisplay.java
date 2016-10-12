package me.ewriter.chapter2.weather;

/**
 * Created by Zubin on 2016/10/12.
 *
 * 观察者，必须实现通知的接口
 */

public class CurrentConditionsDisplay implements Observer, DisplayElement{

    private float temperature;
    private float humidity;
    private Subject weatherData;

    // 这里构造函数保持了对事件源的引用，虽然之后没有使用，但是如果要取消注册就需要，所以保存下来了
    public CurrentConditionsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Current conditions: " + temperature
                + "F degrees and " + humidity + "% humidity");
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        display();
    }
}
