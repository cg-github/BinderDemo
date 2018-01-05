package com.cheng.binderdemo.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cheng.binderdemo.aidls.aidlbinder.Book;
import com.cheng.binderdemo.aidls.aidlbinder.IBookManager;
import com.cheng.binderdemo.aidls.aidlbinder.OnNewBookListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Created by 李国财 on 2018-01-05.
 */

public class AidlBinderService extends Service {

    private static final String TAG = AidlBinderService.class.getSimpleName();
    private static final String TAG_CHENG = "daxian:";

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private IBinder mBinder = new IBookManager.Stub(){

       @Override
       public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

       }

       @Override
       public List<Book> getBookList() throws RemoteException {
           return mBookList;
       }

       @Override
       public Book addBookIn(Book book) throws RemoteException {
           Log.i(TAG,TAG_CHENG+" addBookIn "+book);
           if (!mBookList.contains(book)){
               mBookList.add(book);
           }
           Book result = book;
           book.setmName("this is BookIn");
           book.setmPrice(2333);
           return result;
       }

       @Override
       public Book addBookOut(Book book) throws RemoteException {
           Log.i(TAG,TAG_CHENG+" addBookOut "+book);
           if (!mBookList.contains(book)){
               mBookList.add(book);
           }
           Book result = book;
           book.setmName("this is BookOut");
           book.setmPrice(23333);
           return result;
       }

       @Override
       public Book addBookInOut(Book book) throws RemoteException {
           Log.i(TAG,TAG_CHENG+" addBookInOut "+book);
           if (!mBookList.contains(book)){
               mBookList.add(book);
           }
           Book result = book;
           book.setmName("this is BookInOut");
           book.setmPrice(233333);
           return result;
       }

       @Override
       public void registerBookListener(OnNewBookListener listener) throws RemoteException {

       }

       @Override
       public void unregisterBookListener(OnNewBookListener listener) throws RemoteException {

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
        Log.i(TAG,TAG_CHENG+"onCreate");
    }
}
