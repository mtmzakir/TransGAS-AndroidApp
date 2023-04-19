package com.ousl.transgas.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class GasModel implements Parcelable {
    private String name;
    private String address;
    private float delivery_charge;
    private String image;
    private List<Menu> menus;

    protected GasModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        delivery_charge = in.readFloat();
        image = in.readString();
        menus = in.createTypedArrayList(Menu.CREATOR);
    }

    public static final Creator<GasModel> CREATOR = new Creator<GasModel>() {
        @Override
        public GasModel createFromParcel(Parcel in) {
            return new GasModel(in);
        }

        @Override
        public GasModel[] newArray(int size) {
            return new GasModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(float delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeFloat(delivery_charge);
        dest.writeString(image);
        dest.writeTypedList(menus);
    }
}
