package me.ewriter.chapter5.chocolate;

/**
 * Created by Zubin on 2016/10/14.
 */

public class ChocolateController {

    public static void main(String[] args) {
        ChocolateBoiler boiler = ChocolateBoiler.getInstance();
        boiler.fill();
        boiler.boil();
        boiler.drain();

        // 这里会返回存在的实例
        ChocolateBoiler boiler2 = ChocolateBoiler.getInstance();
    }
}
