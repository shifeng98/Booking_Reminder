package com.example.shoppinglist;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String name;
    private String size;
    private String date;
    private String details;
    private int id;
    private int quantity;
    private int urgent;
    private int bought;

    public Item(String name, String size, String details,int quantity, boolean isUrgent) {
        this.name = name;
        this.size = size;
        this.date = "";
        this.details = details;
        this.id = 0;
        this.quantity = quantity;
        if(isUrgent){
            this.urgent = 1;
        }else{
            this.urgent = 0;
        }
        this.bought = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgentSet) {
        if(urgentSet){
            this.urgent = 1;
        }else{
            this.urgent = 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBought() {
        return bought;
    }

    public void setBought(boolean isBought) {
        if(isBought){
            this.bought = 1;
        }else{
            this.bought = 0;
        }
    }

    protected Item(Parcel in) {
        name = in.readString();
        size = in.readString();
        date = in.readString();
        details = in.readString();
        quantity = in.readInt();
        urgent = in.readInt();
        bought = in.readInt();
        id = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(size);
        parcel.writeString(date);
        parcel.writeString(details);
        parcel.writeInt(quantity);
        parcel.writeInt(urgent);
        parcel.writeInt(bought);
        parcel.writeInt(id);
    }
}
