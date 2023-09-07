package com.example.kwesijames;

import java.sql.Blob;
import java.sql.Date;

public class product {
    private int id;
    private static String prodName;
    private static Double Price;
    private String Description;
    private Double ListPrice;
    private Double RetailPrice;
    private Date DateCreated;
    private String Category;
    private Date DateUpdated;

    public product(int id, String prodName, Double price, String description) {
        this.id = id;
        this.prodName = prodName;
        this.Price = price;
        this.Description = description;
    }

    public product(String prodName, Double price) {
        this.prodName = prodName;
        this.Price = price;
    }



    public product(int id, String prodName, double price, String description, double listPrice, double retailPrice, String category) {
        this.id = id;
        this.prodName = prodName;
        this.Price = price;
        this.Description = description;
        this.ListPrice = listPrice;
        this.RetailPrice = retailPrice;
        this.Category = category;
    }

    public product(int id, String prodName, double price, String description, double listPrice, double retailPrice, Date dateCreated, String category, Date dateUpdated) {
        this.id = id;
        this.prodName = prodName;
        this.Price = price;
        this.Description = description;
        this.ListPrice = listPrice;
        this.RetailPrice = retailPrice;
        this.DateCreated = dateCreated;
        this.Category = category;
        this.DateUpdated = dateUpdated;
    }



    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public static Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getListPrice() {
        return ListPrice;
    }

    public void setListPrice(Double listPrice) {
        ListPrice = listPrice;
    }

    public Double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        RetailPrice = retailPrice;
    }

    public Date getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        DateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return DateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        DateUpdated = dateUpdated;
    }
}
