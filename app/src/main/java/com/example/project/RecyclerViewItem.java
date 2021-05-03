package com.example.project;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class RecyclerViewItem{
    @SerializedName("ID")
    private String id;
    private String name;
    private String type;
    private String company;
    private String location;
    private String category;
    private int size;
    private int cost;
    private AuxData [] auxdata;

    public RecyclerViewItem(String id, String name, String category, String location){
        this.id = id;
        this.name = name;
        this.category = category;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public int getSize() {
        return size;
    }

    public int getCost() {
        return cost;
    }

    public AuxData [] getAuxdata() {
        return auxdata;
    }

    @Override
    public String toString() {
        return id;
    }
}
