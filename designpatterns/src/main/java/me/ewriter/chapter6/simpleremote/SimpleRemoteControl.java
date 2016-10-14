package me.ewriter.chapter6.simpleremote;

import me.ewriter.chapter6.simpleremote.Command;

/**
 * Created by Zubin on 2016/10/14.
 */

public class SimpleRemoteControl {

    Command slot;

    public SimpleRemoteControl() {

    }

    public void setCommand(Command command) {
        slot = command;
    }

    public void buttonWasPressed() {
        slot.execute();
    }
}
