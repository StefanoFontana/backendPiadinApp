package com.piadinapp.backendpiadinapp.model;

public class OrdineItem {
    private String name;
    private String type;
    private String impasto;
    private String ingredientList;
    private float price;
    private int quantity;

    public OrdineItem(String name,String type,String impasto,String ingredients,float price,
                      int quantity)
    {
        this.name = name;
        this.type = type;
        this.impasto = impasto;
        this.ingredientList = ingredients;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName()
    {
        return name;
    }
    public String getType()
    {
        return type;
    }
    public String getImpasto()
    {
        return impasto;
    }
    public String getIngredientList()
    {
        return ingredientList;
    }
    public float getPrice()
    {
        return price;
    }
    public int getQuantity()
    {
        return quantity;
    }
}
