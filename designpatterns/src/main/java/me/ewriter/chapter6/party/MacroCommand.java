package me.ewriter.chapter6.party;

/**
 * 新建一种Command，当调用open 的时候同时打开多种电气
 * */
public class MacroCommand implements Command {
	Command[] commands;
 
	public MacroCommand(Command[] commands) {
		this.commands = commands;
	}
 
	public void execute() {
		for (int i = 0; i < commands.length; i++) {
			commands[i].execute();
		}
	}
 
    /**
     * NOTE:  these commands have to be done backwards to ensure proper undo functionality
     */
	public void undo() {
		for (int i = commands.length -1; i >= 0; i--) {
			commands[i].undo();
		}
	}
}

