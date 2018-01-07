package com.cheng.binderdemo.server;

import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.cheng.binderdemo.aidls.aidlbinder.Book;
import com.cheng.binderdemo.aidls.aidlbinder.IBookManager;
import com.cheng.binderdemo.aidls.aidlbinder.OnNewBookListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by cheng on 2018/1/7.
 */

public class IBookManagerImpl extends IBookManager.Stub {

    private static final String TAG = AidlBinderService.class.getSimpleName();
    private static final String TAG_CHENG = "daxian:";

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<OnNewBookListener> mListenerList = new RemoteCallbackList<>();

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
        Log.i(TAG,TAG_CHENG+" before register "+mListenerList);
        mListenerList.register(listener);
        generateBook();
        Log.i(TAG,TAG_CHENG+" after register "+mListenerList);
    }

    @Override
    public void unregisterBookListener(OnNewBookListener listener) throws RemoteException {
        Log.i(TAG,TAG_CHENG+" listener unregister "+listener);
        Log.i(TAG,TAG_CHENG+" before unregister "+mListenerList);
        mListenerList.unregister(listener);
        generateBook();
        Log.i(TAG,TAG_CHENG+" after unregister "+mListenerList);
    }

    private void generateBook(){
        for(int i=0;i<10;i++){
            Book book = new Book("book "+i,9+i);
            int N = mListenerList.beginBroadcast();
            for (int j=0;j<N;j++){
                OnNewBookListener listener = mListenerList.getBroadcastItem(j);
                try {
                    if (listener!=null){
                        listener.onNewBookCome(book);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            mListenerList.finishBroadcast();
        }
    }
}
