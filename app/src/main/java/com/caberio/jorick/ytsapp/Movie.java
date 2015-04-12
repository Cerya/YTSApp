package com.caberio.jorick.ytsapp;


import java.io.Serializable;

public class Movie implements Serializable{

    private  int id;
    private String title;
    private String titleLong;
    private int  year;
    private double rating;
    private String coverImage;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleLong() {
        return titleLong;
    }

    public void setTitleLong(String titleLong) {
        this.titleLong = titleLong;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setCoverimage(String coverImage){
        this.coverImage = coverImage.replace("\\","");
    }

    public String getCoverImage(){
        return coverImage;
    }

    @Override
    public String toString(){
        return titleLong;
    }


}
