// IQueryBinder.aidl
package com.cheng.binderdemo.aidls.aidlbinder;

// Declare any non-default types here with import statements
import android.os.IBinder;

interface IQueryBinder {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    IBinder queryBinder(int code);
}
