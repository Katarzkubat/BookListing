package com.example.katarzkubat.booklistingapp;

import static com.example.katarzkubat.booklistingapp.R.id.image;

public class Book {

    private String bTitle;
    private String bAuthor;
    private String bPublisher;
    private String bDate;
    private String bUrl;

    public Book(String title, String author, String publisher, String date, String url) {
        bTitle = title;
        bAuthor = author;
        bPublisher = publisher;
        bDate = date;
        bUrl = url;
    }

    public String getTitle() {
        return bTitle;
    }

    public String getAuthor() {
        return bAuthor;}

    public String getPublisher() {
        return bPublisher;
    }

    public String getDate() {
        return bDate;
    }

    public String getUrl() {
        return bUrl;
    }
}
