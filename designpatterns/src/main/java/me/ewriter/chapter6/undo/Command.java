package me.ewriter.chapter6.undo;

/**
 * Created by Zubin on 2016/10/24.
 */

public interface Command {

    public void execute();
    // 加上撤销功能
    public void undo();
}
