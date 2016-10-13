package me.ewriter.chapter3.starbuzz;

/**
 * Created by Zubin on 2016/10/13.
 * 这里面类似的还有 Mocha，Soy，Whip
 * 这个就是调用装饰者，除了 cost 之外，还实现 getDescription， 因为不单单想描述饮料还想完整的描述调料
 */

public class Milk extends CondimentDecorator {

    Beverage beverage;

    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    @Override
    public double cost() {
        // milk 的价钱 加上 原始饮料的价钱
        return .10 + beverage.cost();
    }
}
