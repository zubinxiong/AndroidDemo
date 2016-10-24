package me.ewriter.chapter6.simpleremote;

/**
 * Created by Zubin on 2016/10/14.
 */

public class RemoteControlTest {

    public static void main(String[] args) {
        SimpleRemoteControl remote = new SimpleRemoteControl();

        GarageDoor garageDoor = new GarageDoor();
        GarageDoorOpenCommand garageOpen = new GarageDoorOpenCommand(garageDoor);

        Light light = new Light();
        LightOnCommand lightOnCommand = new LightOnCommand(light);

        remote.setCommand(lightOnCommand);
        remote.buttonWasPressed();

        remote.setCommand(garageOpen);
        remote.buttonWasPressed();
    }
}
