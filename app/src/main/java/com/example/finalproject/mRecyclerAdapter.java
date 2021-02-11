package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class mRecyclerAdapter extends RecyclerView.Adapter<mRecyclerAdapter.ViewHolder> {
    ArrayList<String> filmArrayList;
    public mRecyclerAdapter(ArrayList<String> footballArrayList) {
        filmArrayList = footballArrayList;
    }

    @NonNull
    @Override
    public mRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.therow,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mRecyclerAdapter.ViewHolder holder, int position) {
        holder.mName.setText(filmArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return filmArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.thename);
        }
    }
}
