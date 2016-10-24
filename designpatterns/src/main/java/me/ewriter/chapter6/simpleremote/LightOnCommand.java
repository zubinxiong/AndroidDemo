package me.ewriter.chapter6.simpleremote;

/**
 * Created by Zubin on 2016/10/24.
 */

public class LightOnCommand implements Command{

    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }
}
