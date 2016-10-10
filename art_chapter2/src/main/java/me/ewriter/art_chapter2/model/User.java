package me.ewriter.art_chapter2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import me.ewriter.art_chapter2.aidl.Book;

/**
 * Created by Zubin on 2016/6/21.
 */
public class User implements Serializable, Parcelable {

    private static final long serialVersionUID = 7024146346774051666L;

    public int userId;
    public String userName;
    public boolean isMale;

    public Book book;

    public User() {

    }

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    protected User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readByte() != 0;
        // 因为 book 是另一个 Parcelable 对象，所以需要传递当前线程的上下文加载器，否则会找不到类
        book = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // 如果含有文件描述返回1，几乎所有的都是返回0
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeByte((byte) (isMale ? 1 : 0));
        dest.writeParcelable(book, 0);
    }

    @Override
    public String toString() {
        return String.format(
                "User:{userId:%s, userName:%s, isMale:%s}, with child:{%s}",
                userId, userName, isMale, book);
    }
}
