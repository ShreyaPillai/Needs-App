package com.example.needs.model;

public class Item {

    private int id;
    private String itemname;
    private String itemColor;
    private int itemQty;
    private  String itemSize;
    private String dateitemadded;

    public Item() {
    }

    public Item(int id, String itemname, String itemColor, int itemQty, String itemSize, String dateitemadded) {
        this.id = id;
        this.itemname = itemname;
        this.itemColor = itemColor;
        this.itemQty = itemQty;
        this.itemSize = itemSize;
        this.dateitemadded = dateitemadded;
    }

    public Item(String itemname, String itemColor, int itemQty, String itemSize, String dateitemadded) {
        this.itemname = itemname;
        this.itemColor = itemColor;
        this.itemQty = itemQty;
        this.itemSize = itemSize;
        this.dateitemadded = dateitemadded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }

    public String getDateitemadded() {
        return dateitemadded;
    }

    public void setDateitemadded(String dateitemadded) {
        this.dateitemadded = dateitemadded;
    }
}
