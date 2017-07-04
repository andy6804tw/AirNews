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

import com.openweather.airnews.DataModel.DataModel;
import com.openweather.airnews.MainActivity;
import com.openweather.airnews.R;

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

        public ViewHolder(View itemView,int viewType) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);

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
        if (viewType == 9) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.footer_layout, parent, false),viewType);
        }else
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.card_layout, parent, false),viewType);


    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //viewHolder.itemTitle.setText(titles[i]);
        //viewHolder.itemDetail.setText(details[i]);
        //viewHolder.itemImage.setImageResource(images[i]);
        if(position!=9){
            //new DownloadImageTask (viewHolder.itemImage).execute("http://e-info.org.tw/sites/default/files/styles/article_list/public/34622196154_b9bc69779c_b.jpg?itok=A1NE3mRx");
            viewHolder.itemTitle.setText(MainActivity.list.size()+" "+position);
            //viewHolder.itemTitle.setText(position+"..+.");
        }

    }

    @Override
    public int getItemCount() {
        return 9+1;
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

}