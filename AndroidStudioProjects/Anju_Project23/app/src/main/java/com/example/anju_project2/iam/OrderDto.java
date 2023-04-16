package com.example.anju_project2.iam;

import java.io.Serializable;

public class OrderDto implements Serializable {

    private String url;
    private String quantity;
    private String name;
    private String size;

    private String userId;
    private  String id;


    public String getUrl() {
        return url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderDto(String url, String quantity, String name, String size, String userId , String id) {
        this.url = url;
        this.quantity = quantity;
        this.name = name;
        this.size = size;
        this.userId = userId;
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
}
