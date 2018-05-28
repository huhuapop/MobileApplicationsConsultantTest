package com.mobileapplicationsconsultanttest;

/**
 * Created by huhuapop on 5/25/2018.
 */

public class Book {
    private String title;
    private String imageURL;
    private String author;

    public Book(){

    }

    public Book(String title, String imageURL,String author){
        this.title=title;
        this.imageURL=imageURL;
        this.author=author;
    }

    public String getTitle(){
        return  title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public  String getimageURL(){
        return  imageURL;
    }

    public  void setimageURL(String ImageURL){
        this.imageURL=imageURL;
    }

    public String getauthor(){
        return  author;
    }

    public void setauthor(String author){
        this.author=author;
    }

}
