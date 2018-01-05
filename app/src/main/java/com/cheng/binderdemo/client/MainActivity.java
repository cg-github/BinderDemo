package com.cheng.binderdemo.client;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.cheng.binderdemo.aidls.aidlbinder.Book;
import com.cheng.binderdemo.R;
import com.cheng.binderdemo.aidls.aidlbinder.IBookManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_CHENG = "daxian:";

    private IBookManager mBookManager;
    private Button mButtonIn,mButtonOut,mButtonInOut;

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBookManager = IBookManager.Stub.asInterface(iBinder);
            Log.i(TAG,TAG_CHENG+"BIND SERVICE SUCCESS");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonIn = findViewById(R.id.button_in);
        mButtonOut = findViewById(R.id.button_out);
        mButtonInOut = findViewById(R.id.button_inout);
        mButtonIn.setOnClickListener(this);
        mButtonOut.setOnClickListener(this);
        mButtonInOut.setOnClickListener(this);

        Intent intent = new Intent("com.cheng.server.AidlBinderService");
        bindService(intent,mServiceConn, Service.BIND_AUTO_CREATE);
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
            default:
                break;
        }

    }
}
