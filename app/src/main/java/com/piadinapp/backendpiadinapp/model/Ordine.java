package com.piadinapp.backendpiadinapp.model;

public class Ordine {
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
}
