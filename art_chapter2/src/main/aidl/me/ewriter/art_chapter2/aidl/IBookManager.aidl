// IBookManager.aidl
package me.ewriter.art_chapter2.aidl;

// Declare any non-default types here with import statements
import me.ewriter.art_chapter2.aidl.Book;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
