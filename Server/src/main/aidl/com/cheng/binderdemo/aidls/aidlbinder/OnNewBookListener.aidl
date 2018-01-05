// OnNewBookListener.aidl
package com.cheng.binderdemo.aidls.aidlbinder;

// Declare any non-default types here with import statements
import com.cheng.binderdemo.aidls.aidlbinder.Book;

interface OnNewBookListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void onNewBookCome(in Book book);
}
