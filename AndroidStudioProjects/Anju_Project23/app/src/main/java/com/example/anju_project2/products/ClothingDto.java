package com.example.anju_project2.products;


public class ClothingDto {

    private String name;
    private String price;
    private String url;

    private String title;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

 public ClothingDto(){

 }

  public   ClothingDto(String name , String price, String url,String id,String title){
         this.name = name;
         this.price = price;
         this.url = url;
         this.id = id;
         this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
