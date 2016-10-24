package me.ewriter.chapter7.ducks;

import java.util.Random;

/**
 * Created by Zubin on 2016/10/24.
 *
 * 将 Duck 转换为 Turkey
 */

public class DuckAdapter implements Turkey {

    Duck duck;
    Random random;

    public DuckAdapter(Duck duck) {
        this.duck = duck;
        random = new Random();
    }

    @Override
    public void gobble() {
        duck.quack();
    }

    @Override
    public void fly() {
        if (random.nextInt(5) == 0) {
            duck.fly();
        }
    }
}
