package com.cheng.binderdemo.client;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cheng.binderdemo.Constants;
import com.cheng.binderdemo.aidls.aidlbinder.Book;
import com.cheng.binderdemo.R;
import com.cheng.binderdemo.aidls.aidlbinder.IBookManager;
import com.cheng.binderdemo.aidls.aidlbinder.ICompute;
import com.cheng.binderdemo.aidls.aidlbinder.IQueryBinder;
import com.cheng.binderdemo.aidls.aidlbinder.OnNewBookListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_CHENG = "daxian:";

    private IBookManager mBookManager;
    private ICompute mCompute;
    private IQueryBinder mBinderPool;
    private Button mButtonIn,mButtonOut,mButtonInOut,mButtonUrg,mButtonAdd;

    private OnNewBookListener mListener = new OnNewBookListener.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void onNewBookCome(Book book) throws RemoteException {
            Log.i(TAG,TAG_CHENG+"new book come :"+book);
        }
    };

    //服务死亡代理
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBookManager!=null){
                return;
            }
            Log.i(TAG,TAG_CHENG+"service dead!!!!!");
            mBookManager.asBinder().unlinkToDeath(mDeathRecipient,0);
            mBookManager = null;
            bindBookService(getApplicationContext());
        }
    };

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinderPool = IQueryBinder.Stub.asInterface(iBinder);

            try {
                mBookManager = IBookManager.Stub.asInterface(mBinderPool.queryBinder(Constants.BINDER_IBOOKMANAGER)) ;
                mCompute = ICompute.Stub.asInterface(mBinderPool.queryBinder(Constants.BINDER_ICOMPUTE));
                iBinder.linkToDeath(mDeathRecipient,0);
                mBookManager.registerBookListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.i(TAG,TAG_CHENG+"BIND SERVICE SUCCESS");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            try {
                mBookManager.unregisterBookListener(mListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.i(TAG,TAG_CHENG+"SERVICE DISCONNECT!");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonIn = findViewById(R.id.button_in);
        mButtonOut = findViewById(R.id.button_out);
        mButtonInOut = findViewById(R.id.button_inout);
        mButtonUrg = findViewById(R.id.button_unregister);
        mButtonAdd = findViewById(R.id.button_add);
        mButtonIn.setOnClickListener(this);
        mButtonOut.setOnClickListener(this);
        mButtonInOut.setOnClickListener(this);
        mButtonUrg.setOnClickListener(this);
        mButtonAdd.setOnClickListener(this);

        bindBookService(this);
        Log.i(TAG,TAG_CHENG+"onCreate");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button_in:
                Book bookin = new Book("testbookin",9.99);
                try {
                    Book reBook = mBookManager.addBookIn(bookin);
                    Log.i(TAG,TAG_CHENG+"\nbook:"+bookin);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_out:
                Book bookout = new Book("testbookout",9.99);
                try {
                    Book reBook = mBookManager.addBookOut(bookout);
                    Log.i(TAG,TAG_CHENG+"\nbook:"+bookout);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_inout:
                Book bookinout = new Book("testbookinout",9.99);
                try {
                    Book reBook = mBookManager.addBookInOut(bookinout);
                    Log.i(TAG,TAG_CHENG+"\nbook:"+bookinout);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_unregister:
                try {
                    mBookManager.unregisterBookListener(mListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_add:
                try {
                    int result = mCompute.add(1,8);
                    Toast.makeText(MainActivity.this,""+result,Toast.LENGTH_LONG).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    void bindBookService(Context context){
        Intent intent = new Intent("com.cheng.binderdemo.server.AidlBinderService");
        intent.setPackage(context.getPackageName());
        context.bindService(intent,mServiceConn, Service.BIND_AUTO_CREATE);
    }
}
