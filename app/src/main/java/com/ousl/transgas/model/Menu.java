package com.ousl.transgas.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Menu implements Parcelable {
    private String name;
    private float price;
    private String url;
    private int totalInCart;
    private float rating;

    protected Menu(Parcel in) {
        name = in.readString();
        price = in.readFloat();
        url = in.readString();
        rating = in.readFloat();
        totalInCart = in.readInt();
    }

    public int getTotalInCart() {
        return totalInCart;
    }

    public void setTotalInCart(int totalInCart) {
        this.totalInCart = totalInCart;
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(url);
        dest.writeFloat(rating);
        dest.writeInt(totalInCart);
    }
}
