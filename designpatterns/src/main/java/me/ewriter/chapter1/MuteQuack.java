package me.ewriter.chapter1;

/**
 * Created by zubin on 16/8/4.
 */
public class MuteQuack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("<<Silence>>");
    }
}
