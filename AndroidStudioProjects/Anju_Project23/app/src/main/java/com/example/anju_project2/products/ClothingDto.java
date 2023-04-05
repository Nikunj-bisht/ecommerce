package com.example.anju_project2.products;


public class ClothingDto {

    private String name;
    private String price;
    private String url;

    ClothingDto(String name , String price,String url){
         this.name = name;
         this.price = price;
         this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
