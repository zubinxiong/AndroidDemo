package me.ewriter.chapter7.ducks;

/**
 * Created by Zubin on 2016/10/24.
 * 测试TurkeyAdapter 类，将Turkey 转换成 Duck
 */

public class DuckTestDrive {

    public static void main(String[] args) {
        MallardDuck duck = new MallardDuck();

        // 伪装成鸭子的火鸡
        WildTurkey turkey = new WildTurkey();
        Duck turkeyAdapter = new TurkeyAdapter(turkey);

        System.out.println("The Turkey says...");
        turkey.gobble();
        turkey.fly();

        System.out.println("\nThe Duck says...");
        testDuck(duck);

        System.out.println("\nThe TurkeyAdapter says...");
        testDuck(turkeyAdapter);
    }

    private static void testDuck(Duck duck) {
        duck.quack();
        duck.fly();
    }
}
