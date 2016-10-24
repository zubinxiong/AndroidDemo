package me.ewriter.chapter7.ducks;

/**
 * Created by Zubin on 2016/10/24.
 */

public class TurkeyTestDrive {

    public static void main(String[] args) {
        MallardDuck duck = new MallardDuck();
        Turkey duckAdapter = new DuckAdapter(duck);

        for(int i=0;i<10;i++) {
            System.out.println("The DuckAdapter says...");
            duckAdapter.gobble();
            duckAdapter.fly();
        }
    }
}
