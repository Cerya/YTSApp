package com.caberio.jorick.ytsapp;


import java.io.Serializable;

public class Torrent implements Serializable {

    private String quality;
    private int seeds;
    private int peers;
    private String size;

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getSeeds() {
        return seeds;
    }

    public void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    public int getPeers() {
        return peers;
    }

    public void setPeers(int peers) {
        this.peers = peers;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString(){
        return "quality: " + this.getQuality() +", size: " +this.getSize();
    }



}
