package me.ewriter.chapter9.dinermerger;

/**
 * Created by Zubin on 2016/10/24.
 * 自己新建Iterator 的方式
 */

public class MenuTestDrive {

    public static void main(String[] args) {
        PancakeHouseMenu pancakeHouseMenu = new PancakeHouseMenu();
        DinerMenu dinerMenu = new DinerMenu();

        Waitress waitress = new Waitress(pancakeHouseMenu, dinerMenu);

        waitress.printMenu();
    }
}
