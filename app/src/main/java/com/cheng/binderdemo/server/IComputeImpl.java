package com.cheng.binderdemo.server;

import android.os.RemoteException;

import com.cheng.binderdemo.aidls.aidlbinder.ICompute;

/**
 * Created by cheng on 2018/1/7.
 */

public class IComputeImpl extends ICompute.Stub {
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public int add(int x, int y) throws RemoteException {
        return x+y;
    }
}
