package me.ewriter.chapter1;

/**
 * Created by Zubin on 2016/10/12.
 */

public class MiniDuckSimulator1 {

    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.performFly();
        mallard.performQuack();

        Duck model = new ModelDuck();
        model.performQuack();
        model.performFly();
    }
}
