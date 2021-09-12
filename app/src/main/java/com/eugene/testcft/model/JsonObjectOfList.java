package com.eugene.testcft.model;

import android.os.Parcel;
import android.os.Parcelable;

public class JsonObjectOfList implements Parcelable {
    String name;
    String charCode;
    String value;

    public JsonObjectOfList(String name, String charCode, String value) {
        this.name = name;
        this.charCode = charCode;
        this.value = value;
    }

    public String getNameJSO() {
        return name;
    }

    public String getCharCodeJSO() {
        return charCode;
    }

    public String getValueJSO() {
        return value;
    }

    protected JsonObjectOfList(Parcel in) {
        name = in.readString();
        charCode = in.readString();
        value = in.readString();
    }

    public static final Creator<JsonObjectOfList> CREATOR = new Creator<JsonObjectOfList>() {
        @Override
        public JsonObjectOfList createFromParcel(Parcel in) {
            return new JsonObjectOfList(in);
        }

        @Override
        public JsonObjectOfList[] newArray(int size) {
            return new JsonObjectOfList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(charCode);
        dest.writeString(value);
    }
}
