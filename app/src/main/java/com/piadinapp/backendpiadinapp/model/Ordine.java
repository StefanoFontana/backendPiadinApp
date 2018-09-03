package com.piadinapp.backendpiadinapp.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.piadinapp.backendpiadinapp.adapters.OrdersAdapter;

import java.util.ArrayList;
import java.util.List;

public class Ordine implements Parcelable {
    private static final String ITEM_SEPARATOR = " - ";
    private static final String PROPERTY_SEPARATOR = ";";
    private static final int ITEM_NAME_ID        = 0;
    private static final int ITEM_TYPE_ID        = 1;
    private static final int ITEM_IMPASTO_ID     = 2;
    private static final int ITEM_INGREDIENTS_ID = 3;
    private static final int ITEM_PRICE_ID       = 4;
    private static final int ITEM_QUANTITY_ID    = 5;

    private int id;
    private String email;
    private String phone;
    private String registerDate;
    private float price;
    private String elementsDescription;
    private String note;
    private String fasciaNumber;
    private int fasciaColor;
    private String startOrdine;
    private String endOrdine;

    public Ordine(int id,String mail,String phone,String date,float price,String descr,String note,
                  String fascia,int color, String startOrdine, String endOrdine)
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
        this.startOrdine = startOrdine;
        this.endOrdine = endOrdine;
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
    public String getStartOrdine() {
        return startOrdine;
    }

    public void setStartOrdine(String startOrdine) {
        this.startOrdine = startOrdine;
    }

    public String getEndOrdine() {
        return endOrdine;
    }

    public void setEndOrdine(String endOrdine) {
        this.endOrdine = endOrdine;
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
        dest.writeString(startOrdine);
        dest.writeString(endOrdine);
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
        startOrdine = in.readString();
        endOrdine = in.readString();
    }
    //----------------------------------------------------------------

    public ArrayList<OrdineItem> getOrderItems()
    {
        ArrayList<OrdineItem> finalList = new ArrayList<>();
        OrdineItem ordineItem;

        String[] itemArray = elementsDescription.split(ITEM_SEPARATOR);
        float price;
        int quantity;
        for (String item:itemArray) {
            String[] properties = item.split(PROPERTY_SEPARATOR);
            if(properties.length != 6)
                continue;

            price = Float.parseFloat(properties[ITEM_PRICE_ID].trim());
            quantity = Integer.parseInt(properties[ITEM_QUANTITY_ID].trim().substring(0,1));
            ordineItem = new OrdineItem(properties[ITEM_NAME_ID].substring(1,properties[ITEM_NAME_ID].length()),
                    properties[ITEM_TYPE_ID],
                    properties[ITEM_IMPASTO_ID],
                    properties[ITEM_INGREDIENTS_ID],
                    price,
                    quantity);

            finalList.add(ordineItem);
        }

        return finalList;
    }

    public int getQuantity(){
        int quantita = 0;
        ArrayList<OrdineItem> elementi = getOrderItems();
        for(OrdineItem elemento: elementi){
            quantita = quantita + elemento.getQuantity();
        }
        return quantita;
    }
}
