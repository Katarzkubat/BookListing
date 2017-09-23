package com.example.katarzkubat.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.view.View.GONE;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    private static final String USGS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?maxResult=20&q=";
    private static final int BOOK_LOADER_ID = 1;
    private BookAdapter bAdapter;
    private boolean resultsArePresent = false;
    private String resultsString = "resultsString";
    private BookLoader boookLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            resultsArePresent = savedInstanceState.getBoolean(resultsString, false);
        }

        if(resultsArePresent){
            getLoaderManager().initLoader(0, null, BookActivity.this);
        }

        setContentView(R.layout.activity_main);

        ListView booksListView = (ListView) findViewById(R.id.list);

        bAdapter = new BookAdapter(this, new ArrayList<Book>());
        booksListView.setAdapter(bAdapter);

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Book chosenBook = bAdapter.getItem(position);

                Uri bookUri = Uri.parse(chosenBook.getUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(websiteIntent);
            }
        });

        EditText editText = (EditText) findViewById(R.id.edit);
        booksListView.setEmptyView(editText);

        LoaderManager loaderManager = getLoaderManager();

        ImageView search = (ImageView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ListView booksListView = (ListView) findViewById(R.id.list);
                if (resultsArePresent) {
                    resultsArePresent = false;
                    bAdapter.clear();

                    EditText editText = (EditText) findViewById(R.id.edit);
                    editText.setText("");

                    booksListView.setEmptyView(editText);
                    TextView bEmptyStateTextView = (TextView) findViewById(R.id.user_alert);
                    bEmptyStateTextView.setText("");
                    return;
                }
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    getLoaderManager().restartLoader(0, null, BookActivity.this);

                } else {

                    LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
                    progressLayout.setVisibility(GONE);

                    TextView bEmptyStateTextView = (TextView) findViewById(R.id.user_alert);
                    bEmptyStateTextView.setText(R.string.no_internet);
                    booksListView.setEmptyView(bEmptyStateTextView);
                    resultsArePresent = true;
                }
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outSave) {
        outSave.putBoolean(resultsString, resultsArePresent);
    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int i, Bundle bundle) {

        String query = ((EditText) findViewById(R.id.edit)).getText().toString();
        String queryCleaned;

        try {
            queryCleaned = URLEncoder.encode(query, "utf-8");
        }catch(UnsupportedEncodingException e){
            queryCleaned = query;
        }

        Log.i("query", queryCleaned);

        LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        progressLayout.setVisibility(View.VISIBLE);

        String queryUrl = USGS_REQUEST_URL + queryCleaned;
        boookLoader = new BookLoader(this, queryUrl);
        return boookLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> books) {
        Log.i("onLoadFinished", "onLoadFinished");

        LinearLayout progressLayout = (LinearLayout) findViewById(R.id.progress_layout);
        progressLayout.setVisibility(GONE);

        bAdapter.clear();

        if (books != null && !books.isEmpty()) {
            bAdapter.addAll(books);

        } else {

            TextView bEmptyStateTextView = (TextView) findViewById(R.id.user_alert);
            bEmptyStateTextView.setText(R.string.no_book);
            ListView booksListView = (ListView) findViewById(R.id.list);
            booksListView.setEmptyView(bEmptyStateTextView);
        }

        resultsArePresent = true;
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        bAdapter.clear();
    }
}
