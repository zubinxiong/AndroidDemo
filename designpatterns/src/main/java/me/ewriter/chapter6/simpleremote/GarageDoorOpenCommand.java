package me.ewriter.chapter6.simpleremote;

/**
 * Created by Zubin on 2016/10/14.
 */

public class GarageDoorOpenCommand implements Command {

    GarageDoor garageDoor;

    public GarageDoorOpenCommand(GarageDoor garageDoor) {
        this.garageDoor = garageDoor;
    }

    @Override
    public void execute() {
        garageDoor.up();
    }
}
