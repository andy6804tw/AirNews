package com.openweather.airnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Document document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    document = Jsoup.connect("http://e-info.org.tw/taxonomy/term/258/all")
                            .timeout(3000)
                            .get();
                    Elements noteList = document.select("div.view-content");
                    Elements title = noteList.select("div.views-field.views-field-title");
                    Elements Image = noteList.select("div.views-field.views-field-field-image").select("img");
                    Elements detail = noteList.select("div.views-field.views-field-body");
                    Elements time = noteList.select("span.views-field.views-field-created");
                    int c=0,c2=0,c3=0,c4=0;
                    //Log.e("Name"+c++,Image.attr("abs:src"));
                   for (Element element:title) {
                         String url=element.select("a").attr("abs:href");
                        String Title=element.text();
                        Log.e("Title"+c++,url+" "+Title);
                    }
                    for(Element element:Image){
                        Log.e("img"+c2++,element.attr("abs:src"));
                    }
                    for(Element element:detail){
                        Log.e("detail"+c3++,element.text());
                    }
                    for(Element element:time){
                        Log.e("time"+c4++,element.text());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("TAG", "run: " + e.getMessage());
                }

            }
        }).start();
    }
}
