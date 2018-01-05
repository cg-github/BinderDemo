// IBookManager.aidl
package com.cheng.binderdemo.aidls.aidlbinder;

// Declare any non-default types here with import statements
import com.cheng.binderdemo.aidls.aidlbinder.Book;
import com.cheng.binderdemo.aidls.aidlbinder.OnNewBookListener;

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    List<Book> getBookList();
    Book addBookIn(in Book book);
    Book addBookOut(out Book book);
    Book addBookInOut(inout Book book);
    void registerBookListener(OnNewBookListener listener);
    void unregisterBookListener(OnNewBookListener listener);
}
