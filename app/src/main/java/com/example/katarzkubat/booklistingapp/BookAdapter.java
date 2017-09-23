package com.example.katarzkubat.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String title;
        String author;
        String publisher;
        String date;

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_book, parent, false);
        }

        Book chosenBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        String formattedTitle = formatTitle(chosenBook.getTitle());
        titleView.setText(formattedTitle);

        TextView authorView = (TextView) listItemView.findViewById(R.id.author);
        String formattedAuthor = formatAuthor(chosenBook.getAuthor());
        authorView.setText(formattedAuthor);

        TextView publisherView = (TextView) listItemView.findViewById(R.id.publisher);
        String formattedPublisher = formatPublisher(chosenBook.getPublisher());
        publisherView.setText(formattedPublisher);

        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(chosenBook.getDate());
        dateView.setText(formattedDate);

        return listItemView;
    }

        public String formatTitle(String titleView) {
            return titleView;
        }

        public String formatAuthor(String authorView) {
            return authorView;
        }

        public String formatPublisher(String publisherView){
            return publisherView;
        }

        public String formatDate(String dateView) {
            return dateView;
        }
}

