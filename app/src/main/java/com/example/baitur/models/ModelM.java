package com.example.baitur.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Annotation;

public class ModelM implements Parcelable, SerializedName {
    @SerializedName("id")
    @Expose
    int modelId;
    @SerializedName("title")
    @Expose
    String modelTitle;
    @SerializedName("price")
    @Expose
    Double modelPrice;
    @SerializedName("description")
    @Expose
    String modelDescription;
    @SerializedName("image")
    @Expose
    String modelImage;


    public ModelM(int modelId, String modelTitle, Double modelPrice,
                  String modelDescription, String modelImage, int counterProduct) {
        this.modelId = modelId;
        this.modelTitle = modelTitle;
        this.modelPrice = modelPrice;
        this.modelDescription = modelDescription;
        this.modelImage = modelImage;

    }

    protected ModelM(Parcel in) {
        modelId = in.readInt();
        modelTitle = in.readString();
        if (in.readByte() == 0) {
            modelPrice = null;
        } else {
            modelPrice = in.readDouble();
        }
        modelDescription = in.readString();
        modelImage = in.readString();
    }

    public static final Creator<ModelM> CREATOR = new Creator<ModelM>() {
        @Override
        public ModelM createFromParcel(Parcel in) {
            return new ModelM(in);
        }

        @Override
        public ModelM[] newArray(int size) {
            return new ModelM[size];
        }
    };

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getModelTitle() {
        return modelTitle;
    }

    public void setModelTitle(String modelTitle) {
        this.modelTitle = modelTitle;
    }

    public Double getModelPrice() {
        return modelPrice;
    }

    public void setModelPrice(Double modelPrice) {
        this.modelPrice = modelPrice;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getModelImage() {
        return modelImage;
    }

    public void setModelImage(String modelImage) {
        this.modelImage = modelImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(modelId);
        parcel.writeString(modelTitle);
        if (modelPrice == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(modelPrice);
        }
        parcel.writeString(modelDescription);
        parcel.writeString(modelImage);
    }

    @Override
    public String value() {
        return null;
    }

    @Override
    public String[] alternate() {
        return new String[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
