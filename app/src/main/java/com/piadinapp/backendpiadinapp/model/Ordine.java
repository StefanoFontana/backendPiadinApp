package com.piadinapp.backendpiadinapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.piadinapp.backendpiadinapp.adapters.OrdersAdapter;

public class Ordine implements Parcelable {
    private int id;
    private String email;
    private String phone;
    private String registerDate;
    private float price;
    private String elementsDescription;
    private String note;
    private String fasciaNumber;
    private int fasciaColor;

    public Ordine(int id,String mail,String phone,String date,float price,String descr,String note,
                  String fascia,int color)
    {
        this.id = id;
        email = mail;
        this.phone = phone;
        registerDate = date;
        this.price = price;
        elementsDescription = descr;
        this.note = note;
        fasciaNumber = fascia;
        fasciaColor = color;
    }

    public int getId()
    {
        return id;
    }
    public String getEmail()
    {
        return email;
    }
    public String getPhone()
    {
        return phone;
    }
    public String getRegisterDate()
    {
        return registerDate;
    }
    public float getPrice()
    {
        return price;
    }
    //todo get lista piadine
    public String getNote()
    {
        return note;
    }
    public String getFasciaNumber()
    {
        return fasciaNumber;
    }
    public int getFasciaColor()
    {
        return fasciaColor;
    }

    //PARCELABLE METHODS---------------------------------------------
    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(registerDate);
        dest.writeFloat(price);
        dest.writeString(elementsDescription);
        dest.writeString(note);
        dest.writeString(fasciaNumber);
        dest.writeInt(fasciaColor);
    }

    public static final Parcelable.Creator<Ordine> CREATOR = new Parcelable.Creator<Ordine>() {
        @Override
        public Ordine createFromParcel(Parcel source)
        {
            return new Ordine(source);
        }

        @Override
        public Ordine[] newArray(int size) {
            return new Ordine[size];
        }
    };

    //Usato dal creator di Parcelable
    private Ordine(Parcel in)
    {
        id = in.readInt();
        email = in.readString();
        phone = in.readString();
        registerDate = in.readString();
        price = in.readFloat();
        elementsDescription = in.readString();
        note = in.readString();
        fasciaNumber = in.readString();
        fasciaColor = in.readInt();
    }
    //----------------------------------------------------------------
}
