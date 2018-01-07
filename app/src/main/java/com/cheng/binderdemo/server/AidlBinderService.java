package com.cheng.binderdemo.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cheng.binderdemo.Constants;
import com.cheng.binderdemo.aidls.aidlbinder.Book;
import com.cheng.binderdemo.aidls.aidlbinder.IBookManager;
import com.cheng.binderdemo.aidls.aidlbinder.IQueryBinder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by 李国财 on 2018-01-05.
 */

public class AidlBinderService extends Service {
    private static final String TAG = AidlBinderService.class.getSimpleName();
    private static final String TAG_CHENG = "daxian:";


    private IBinder mBinder = new IQueryBinder.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public IBinder queryBinder(int code) throws RemoteException {
            switch (code){
                case Constants.BINDER_ICOMPUTE:
                    return new IComputeImpl();
                case Constants.BINDER_IBOOKMANAGER:
                    return new IBookManagerImpl();
                default:
                    return null;
            }
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,TAG_CHENG+"onBind");
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                generateBook();
//            }
//        }).start();
        Log.i(TAG,TAG_CHENG+"onCreate");
    }


}
