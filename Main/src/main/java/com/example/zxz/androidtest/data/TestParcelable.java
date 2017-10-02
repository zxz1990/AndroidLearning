package com.example.zxz.androidtest.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xuezhi.zxz on 2017/5/15.
 */

public class TestParcelable implements Parcelable {

    public String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public TestParcelable(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<TestParcelable> CREATOR = new Creator<TestParcelable>() {
        @Override
        public TestParcelable createFromParcel(Parcel source) {
            return new TestParcelable(source);
        }

        @Override
        public TestParcelable[] newArray(int size) {
            return new TestParcelable[size];
        }


    };


}
