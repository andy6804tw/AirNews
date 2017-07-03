package com.openweather.airnews;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.kekstudio.dachshundtablayout.indicators.DachshundIndicator;
import com.openweather.airnews.Adapter.ViewPagerAdapter;
import com.openweather.airnews.DataModel.DataModel;
import com.openweather.airnews.Fragment.HomeFragment;
import com.openweather.airnews.Fragment.NewsFragment;
import com.openweather.airnews.Fragment.fragment_page;
import com.openweather.airnews.Marquee.NoticeMF;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Document document;
    public ArrayList<DataModel> list;
    private ViewPager viewPager;
    private DachshundTabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;

    private MarqueeView marqueeView2;
    private final List<String> datas = Arrays.asList("2日北部、竹苗地區為普通等級","其他地區為良好等級。","指標污染物為臭氧(午後時段)", "3日、4日北部、竹苗地區為普通等級", "指標污染物為臭氧(午後時段)", "其他地區為良好等級。");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list=new ArrayList<DataModel>();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new HomeFragment(),"現在情況");
        viewPagerAdapter.addFragments(new NewsFragment(),"新聞");
        viewPagerAdapter.addFragments(new fragment_page(),"預報");
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = (DachshundTabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setAnimatedIndicator(new DachshundIndicator(tabLayout));

        marqueeView2 = (MarqueeView) findViewById(R.id.marqueeView2);
        final MarqueeFactory<TextView, String> marqueeFactory2 = new NoticeMF(this);
        marqueeFactory2.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
                Toast.makeText(MainActivity.this, holder.data, Toast.LENGTH_SHORT).show();
            }
        });
        marqueeFactory2.setData(datas);
        marqueeView2.setMarqueeFactory(marqueeFactory2);
        marqueeView2.startFlipping();
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
                    Elements noteList = document.select("div").select("#block-system-main").select("div.views-row");
                    //Log.e("Data",noteList.toString());
                    int c=0;
                    //Log.e("Name"+c++,Image.attr("abs:src"));
                    for (Element element:noteList){
                        Elements title = element.select("div.views-field.views-field-title");
                        Elements url =element.select("div.views-field.views-field-title").select("a");
                        Elements Image = element.select("div.views-field.views-field-field-image").select("img");
                        Elements detail = element.select("div.views-field.views-field-body");
                        Elements time = element.select("span.views-field.views-field-created");
                        //Log.e("Title"+c++,title.text()+" "+Image.attr("abs:src")+" "+detail.text()+" "+time.text()+" "+url.attr("abs:href"));
                        list.add(new DataModel(title.text(),time.text(),detail.text(),Image.attr("abs:src"),url.attr("abs:href")));
                    }
                   for(int i =0;i<list.size();i++){
                       Log.e("Title"+i,list.get(i).getTitle());
                       Log.e("time"+i,list.get(i).getTime());
                       Log.e("detail"+i,list.get(i).getDetail());
                       Log.e("Image"+i,list.get(i).getImage());
                       Log.e("url"+i,list.get(i).getUrl());
                   }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("TAG", "run: " + e.getMessage());
                }

            }
        }).start();
    }

}
