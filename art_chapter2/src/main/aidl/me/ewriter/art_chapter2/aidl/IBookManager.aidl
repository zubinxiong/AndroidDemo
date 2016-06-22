// IBookManager.aidl
package me.ewriter.art_chapter2.aidl;

// Declare any non-default types here with import statements
import me.ewriter.art_chapter2.aidl.Book;
import me.ewriter.art_chapter2.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
