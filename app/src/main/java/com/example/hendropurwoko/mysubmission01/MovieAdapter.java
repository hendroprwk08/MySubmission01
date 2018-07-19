package com.example.hendropurwoko.mysubmission01;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context c;
    private RecyclerViewClickListener mListener;
    public ArrayList<MovieClass> movieList;

    public MovieAdapter(Context c) { this.c = c; }

    public void setListPresident(ArrayList<MovieClass> list){
        this.movieList = list;
    }

    public ArrayList<MovieClass> getListData(){
        return movieList;
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        holder.tvJudul.setText(movieList.get(position).getTitle());
        holder.tvDesc.setText(movieList.get(position).getDesc());
        holder.tvDate.setText(movieList.get(position).getDate());

        Glide.with(c)
                .load(movieList.get(position).getImage())
                .override(350, 350)
                .into(holder.imgPoster);

        //holder.imgPoster.setImageResource(R.drawable.ic_launcher_background);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvJudul, tvDesc, tvDate;
        ImageView imgPoster;

        public ViewHolder(View itemView) {
            super(itemView);
            tvJudul = (TextView)itemView.findViewById(R.id.tv_title);
            tvDesc = (TextView)itemView.findViewById(R.id.tv_desc);
            tvDate = (TextView)itemView.findViewById(R.id.tv_date);
            imgPoster = (ImageView)itemView.findViewById(R.id.img_poster);
        }
    }
}