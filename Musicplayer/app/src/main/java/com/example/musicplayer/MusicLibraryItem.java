package com.example.musicplayer;

public class MusicLibraryItem {
    private int imageResource;
    private String title;
    private String author;
    //private String music;

    MusicLibraryItem(int imageResource, String title, String author){
        this.imageResource = imageResource;
        this.title = title;
        this.author = author;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

}