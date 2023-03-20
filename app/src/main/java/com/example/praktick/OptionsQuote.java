package com.example.praktick;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class OptionsQuote extends BaseAdapter {

    private Context mContext;
    List<Quote> quoteList;

    public OptionsQuote(Context mContext, List<Quote> maskList) {
        this.mContext = mContext;
        this.quoteList = maskList;
    }

    @Override
    public int getCount() {
        return quoteList.size();
    }

    @Override
    public Object getItem(int i) {
        return quoteList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return quoteList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext,R.layout.item_qoute,null);

        TextView zag = v.findViewById(R.id.tvZag);
        ImageView image = v.findViewById(R.id.image);
        TextView desc = v.findViewById(R.id.tbDesc);

        Quote quote = quoteList.get(position);
        zag.setText(quote.getTitle());

        if(quote.getImage().equals("null"))
        {
            image.setImageResource(R.drawable.logo);
        }
        else
        {
            new decodeImage((ImageView) image)
                    .execute(quote.getImage());
        }

        desc.setText(quote.getDescription());
        return v;
    }

    public static class decodeImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public decodeImage(ImageView bmImage) {
            this.imageView = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}
