package com.example.katarzkubat.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private String bUrl;

    public BookLoader(Context context, String url) {
        super(context);
        bUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        if (bUrl == null) {
            return null;
        }

        ArrayList<Book> books = QueryUtils.fetchBooksData(bUrl);
        return books;
    }
}
