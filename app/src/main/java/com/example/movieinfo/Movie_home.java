package com.example.movieinfo;

public class Movie_home {

    private String title,thumbnail,summery,language, type;

    public String getLanguage() {
        return language;
    }

    public String getType() {
        return type;
    }

    public Movie_home(String title, String thumbnail, String summery, String language, String type, Double rating) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.summery = summery;
        this.language = language;
        this.type = type;
        this.rating = rating;
    }

    private Double rating;

    public Movie_home(String title, String thumbnail, String summery, Double rating) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.summery = summery;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getSummery() {
        return summery;
    }

    public Double getRating() {
        return rating;
    }
}
