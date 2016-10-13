package me.ewriter.chapter3.starbuzz;

/**
 * Created by Zubin on 2016/10/13.
 * 这种设计，当子类变多即饮料特别多的话就需要有超级多个子类了
 */

public class DarkRoast extends Beverage {

    public DarkRoast() {
        description = "Dark Roast Coffee";
    }

    @Override
    public double cost() {
        return .99;
    }
}
