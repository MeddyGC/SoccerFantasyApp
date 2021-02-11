package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder>{

    private int images;//[] images;
    ArrayList<String> allProducts;
    public RecyclerAdapter(int images, ArrayList<String> names){
        this.images = images;
        this.allProducts = names;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout,parent,false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        int images_id = images;//[position];
        holder.Images.setImageResource(images_id);
        holder.ImageTxt.setText(allProducts.get(position));
        // allProducts.clear();


    }

    @Override
    public int getItemCount() {
        return allProducts.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView Images;
        TextView ImageTxt;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Images = itemView.findViewById(R.id.album);
            ImageTxt = itemView.findViewById(R.id.album_title);

        }
    }


}
