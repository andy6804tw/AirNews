package com.openweather.airnews.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.openweather.airnews.DataModel.DataModel;
import com.openweather.airnews.LoadingSplash.SplashActivity;
import com.openweather.airnews.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by andy6804tw on 2017/7/6.
 */

public class HomeFragmentRVA extends RecyclerView.Adapter<HomeFragmentRVA.ViewHolder> {


    private final Context mContext;
    public ArrayList<DataModel> list;
    private int page=1;

    public HomeFragmentRVA(Context context) {
        this.mContext = context;
    }
    public HomeFragmentRVA(Context context, ArrayList<DataModel> list) {
        this.mContext = context;
        this.list=list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

       //痊癒變數

        public ViewHolder(View itemView,int viewType) {
            super(itemView);
            if(viewType==0){
               //FindViewByID
            }
            else {
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + SplashActivity.list.size()+"  "+list.size()+" "+position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.card_map, parent, false),viewType);
        }else
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.card_now, parent, false),viewType);


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        //viewHolder.itemTitle.setText(titles[i]);
        //viewHolder.itemDetail.setText(details[i]);
        //viewHolder.itemImage.setImageResource(images[i]);
        if(position==0){


        }else{

        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }
    @Override
    public int getItemViewType(int position) {return  position;}

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    private void addData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Document document = Jsoup.connect("http://e-info.org.tw/taxonomy/term/258/all?page="+page)
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