// IOnNewBookArrivedListener.aidl
package me.ewriter.art_chapter2.aidl;

// Declare any non-default types here with import statements
import me.ewriter.art_chapter2.aidl.Book;

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book newBook);
}
