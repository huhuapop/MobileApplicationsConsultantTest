package com.mobileapplicationsconsultanttest;

/**
 * Created by huhuapop on 5/25/2018.
 */

public class Book {
    private String title;
    private String imageURL;

    public Book(){

    }

    public Book(String title, String imageURL){
        this.title=title;
        this.imageURL=imageURL;
    }

    public String getTitle(){
        return  title;
    }

    public  String getImageURL(){
        return  imageURL;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public  void setImageURL(String ImageURL){
        this.imageURL=imageURL;
    }



}
