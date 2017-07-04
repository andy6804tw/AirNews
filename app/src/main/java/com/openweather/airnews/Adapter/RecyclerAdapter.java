package com.openweather.airnews.Adapter;

/**
 * Created by andy6804tw on 2017/7/3.
 */

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
import android.widget.TextView;
import android.widget.Toast;

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

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String[] titles = {"Chapter One",
            "Chapter Two",
            "Chapter Three",
            "Chapter Four",
            "Chapter Five",
            "Chapter Six",
            "Chapter Seven",
            "Chapter Eight"};

    private String[] details = {"Item one details",
            "Item two details", "Item three details",
            "Item four details", "Item file details",
            "Item six details", "Item seven details",
            "Item eight details"};
    private final Context mContext;
    private final int TYPE_FOOTER = titles.length;
    public ArrayList<DataModel> list;

    public RecyclerAdapter(Context context,ArrayList<DataModel> list) {
        this.mContext = context;
        this.list=list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;
        //Footer
        public TextView tvFooter;

        public ViewHolder(View itemView,int viewType) {
            super(itemView);
            if(viewType!=SplashActivity.list.size()){
                itemImage = (ImageView)itemView.findViewById(R.id.item_image);
                itemTitle = (TextView)itemView.findViewById(R.id.item_title);
                itemDetail =(TextView)itemView.findViewById(R.id.item_detail);
            }
            else
                tvFooter =(TextView)itemView.findViewById(R.id.tvFooter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SplashActivity.list.size()) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.footer_layout, parent, false),viewType);
        }else
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.card_layout, parent, false),viewType);


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //viewHolder.itemTitle.setText(titles[i]);
        //viewHolder.itemDetail.setText(details[i]);
        //viewHolder.itemImage.setImageResource(images[i]);
        if(position!=SplashActivity.list.size()){
            new DownloadImageTask (viewHolder.itemImage).execute(SplashActivity.list.get(position).getImage());
            viewHolder.itemTitle.setText(SplashActivity.list.get(position).getTitle());
        }else{
                viewHolder.tvFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"Hi~",Toast.LENGTH_LONG).show();
                    notifyItemRemoved(list.size());
                    list.add(new DataModel("123","456","789", "101112","http://e-info.org.tw/sites/default/files/default_logo.png"));
                    list.add(new DataModel("123","456","789", "101112","http://e-info.org.tw/sites/default/files/default_logo.png"));
                    list.add(new DataModel("123","456","789", "101112","http://e-info.org.tw/sites/default/files/default_logo.png"));
                    list.add(new DataModel("123","456","789", "101112","http://e-info.org.tw/sites/default/files/default_logo.png"));
                    list.add(new DataModel("123","456","789", "101112","http://e-info.org.tw/sites/default/files/default_logo.png"));
                    list.add(new DataModel("123","456","789", "101112","http://e-info.org.tw/sites/default/files/default_logo.png"));
                   // notifyDataSetChanged();
                }
            });
        }
        if(position==SplashActivity.list.size()-1){
            //addData();
            //notifyDataSetChanged();
        }

    }

    @Override
    public int getItemCount() {
        return SplashActivity.list.size()+1;
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
                    Document document = Jsoup.connect("http://e-info.org.tw/taxonomy/term/258/all?page=1")
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