package com.example.readrss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ListView lv_bao;
    ArrayList<News> arrayNews;
    NewsAdapter newsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_bao = findViewById(R.id.lv_bao);
        arrayNews = new ArrayList<>();
        newsAdapter = new NewsAdapter(MainActivity.this, arrayNews);
        lv_bao.setAdapter(newsAdapter);

        new ReadRSS().execute("https://vnexpress.net/rss/the-thao.rss");

        lv_bao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("linkTinTuc", arrayNews.get(i).getLink());
                startActivity(intent);
            }
        });
    }

    private class ReadRSS extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }

                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            String tieuDe = "";
            String img = "";
            String[] image = new String[10];
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                tieuDe = parser.getValue(element, "title");
                arrayNews.add(new News(tieuDe, parser.getValue(element, "link"), parser.getValue(element, "pubDate")));
            }

            newsAdapter.notifyDataSetChanged();
        }
    }
}