package com.caberio.jorick.ytsapp;


import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable{

    private  int id;
    private String title;
    private String titleLong;

    private String coverImage;
    List<Torrent> torrents;


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

    public void setCoverimage(String coverImage){
        this.coverImage = coverImage.replace("\\","");
    }

    public String getCoverImage(){
        return coverImage;
    }

    public List<Torrent> getTorrents() {
        return torrents;
    }

    public void setTorrents(List<Torrent> torrents) {
        this.torrents = torrents;
    }

    @Override
    public String toString(){
        return titleLong;
    }


}
