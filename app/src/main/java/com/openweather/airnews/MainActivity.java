package com.openweather.airnews;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;
import com.kekstudio.dachshundtablayout.indicators.DachshundIndicator;
import com.openweather.airnews.Adapter.ViewPagerAdapter;
import com.openweather.airnews.Fragment.HomeFragment;
import com.openweather.airnews.Fragment.NewsFragment;
import com.openweather.airnews.Fragment.fragment_page;
import com.openweather.airnews.Marquee.NoticeMF;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private DachshundTabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;

    private MarqueeView marqueeView2;
    private final List<String> datas = Arrays.asList("2日北部、竹苗地區為普通等級","其他地區為良好等級。","指標污染物為臭氧(午後時段)", "3日、4日北部、竹苗地區為普通等級", "指標污染物為臭氧(午後時段)", "其他地區為良好等級。");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

}
