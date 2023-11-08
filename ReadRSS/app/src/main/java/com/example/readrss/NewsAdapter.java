package com.example.readrss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, ArrayList<News> userArraylist) {
        super(context, R.layout.list_rss, userArraylist);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_rss, parent, false);

        }
        TextView nTitle = convertView.findViewById(R.id.title);
        TextView nPubDate = convertView.findViewById(R.id.date);

        nTitle.setText(news.getTitle());
        nPubDate.setText(news.getPubDate());

        return convertView;
    }
}
