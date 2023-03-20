package com.example.praktick;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;


public class OptionsFeels extends RecyclerView.Adapter<OptionsFeels.ViewHolder> {

    private List<Feels> dataFeels;
    private Context context;

    public OptionsFeels(List<Feels> dataFeels, Context context) {
        this.dataFeels = dataFeels;
        this.context = context;
    }

    @NonNull
    @Override
    public OptionsFeels.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OptionsFeels.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_feels, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsFeels.ViewHolder holder, int position) {
        final Feels feels = dataFeels.get(position);
        holder.view.setText(feels.getTitle());

        if(feels.getImage().equals("null"))
        {
            holder.image.setImageResource(R.drawable.logo);
        }
        else
        {
            new OptionsFeels.DownloadImageTask((ImageView) holder.image)
                    .execute(feels.getImage());
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
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

    @Override
    public int getItemCount() {
        return dataFeels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView view;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
        }
    }
}


