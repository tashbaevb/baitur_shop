package com.example.baitur.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Parcelable, Serializable {
    @SerializedName("product")
    @Expose
    int product_id;

    @SerializedName("user_name")
    @Expose
    String nameUser;

    @SerializedName("user_address")
    @Expose
    String addressUser;

    @SerializedName("counter")
    @Expose
    int counterProduct;


    public Order(int product_id, String nameUser, String addressUser, int counterProduct) {
        this.product_id = product_id;
        this.nameUser = nameUser;
        this.addressUser = addressUser;
        this.counterProduct = counterProduct;
    }

    protected Order(Parcel in) {
        product_id = in.readInt();
        nameUser = in.readString();
        addressUser = in.readString();
        counterProduct = in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public int getCounterProduct() {
        return counterProduct;
    }

    public void setCounterProduct(int counterProduct) {
        this.counterProduct = counterProduct;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(product_id);
        dest.writeString(nameUser);
        dest.writeString(addressUser);
        dest.writeInt(counterProduct);
    }
}
