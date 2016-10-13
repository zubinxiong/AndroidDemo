package me.ewriter.chapter3.starbuzz;

/**
 * Created by Zubin on 2016/10/13.
 * 这个就是一个装饰者包装的组件, 和普通的类一样是继承自 Beverage 的
 */

public abstract class CondimentDecorator extends Beverage{

    public abstract String getDescription();

}
