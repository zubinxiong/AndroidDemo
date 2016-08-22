package me.ewriter.databindingsample;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Zubin on 2016/8/22.
 * 可以看到这里还是需要手动发出通知，另外一种更具体的绑定方式，参考 User2
 */
public class User extends BaseObservable {

    public String userName;
    public String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(me.ewriter.databindingsample.BR.userName);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(me.ewriter.databindingsample.BR.password);
    }
}
