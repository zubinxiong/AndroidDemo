package me.ewriter.chapter3.starbuzz;

/**
 * Created by Zubin on 2016/10/13.
 * Beverage： 饮料
 */

public abstract class Beverage {

    String description = "Unknow Beverage";

    public String getDescription() {
        return description;
    }

    // cost 由每个子类返回价钱
    public abstract double cost();

}
