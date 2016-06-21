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
}
