package com.cheng.binderdemo.aidls.aidlbinder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 李国财 on 2018-01-05.
 */

public class Book implements Parcelable{
    private String mName;
    private double mPrice;

    public Book(){
    }

    public Book(String mName, double mPrice) {
        this.mName = mName;
        this.mPrice = mPrice;
    }

    protected Book(Parcel in) {
        mName = in.readString();
        mPrice = in.readDouble();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public String toString() {
        return "Book{" +
                "mName='" + mName + '\'' +
                ", mPrice=" + mPrice +
                '}';
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeDouble(mPrice);
    }

    public  void readFromParcel(Parcel in){
        this.mName = in.readString();
        this.mPrice = in.readDouble();
    }
}
