package com.example.movieinfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter_home extends RecyclerView.Adapter<Adapter_home.Movie_holder_home> {

    private Context context;
    private List<Movie_home> movieList;
    List<Movie_home> filteredMovieList;

    public Adapter_home(Context context, List<Movie_home> movieList) {
        this.context = context;
        this.movieList = movieList;
        this.filteredMovieList = movieList;
    }

    @NonNull
    @Override
    public Movie_holder_home onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new Movie_holder_home(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Movie_holder_home holder, int position) {
        Movie_home movie_home = movieList.get(position);
        holder.rating_home.setText(movie_home.getRating().toString());
        holder.movie_name_home.setText(movie_home.getTitle());
        holder.summery_home.setText(movie_home.getSummery());
        Glide.with(context).load(movie_home.getThumbnail()).into(holder.thumbnail_home);

        holder.movie_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detail_activity.class);

                Bundle bundle = new Bundle();
                bundle.putString("mTitle",movie_home.getTitle());
                bundle.putString("mThumbnail",movie_home.getThumbnail());
                bundle.putString("mSummery",movie_home.getSummery());
                bundle.putDouble("mRating",movie_home.getRating());

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filteredMovieList.size();
    }

    public class Movie_holder_home extends RecyclerView.ViewHolder{

        ImageView thumbnail_home;
        TextView movie_name_home,rating_home,summery_home;
        CardView movie_item;

        public Movie_holder_home(@NonNull View itemView) {
            super(itemView);

            thumbnail_home = itemView.findViewById(R.id.thumbnail_home);
            movie_name_home = itemView.findViewById(R.id.movie_name_home);
            rating_home = itemView.findViewById(R.id.rating_home);
            summery_home = itemView.findViewById(R.id.summery_home);
            movie_item = itemView.findViewById(R.id.movie_item);
        }
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String key = charSequence.toString();
                if (key.isEmpty()){
                    filteredMovieList = movieList;
                } else {
                    List<Movie_home> listFiltered = new ArrayList<>();
                    for (Movie_home row: movieList){
                        String strKey = row.getTitle().replaceAll("\\s","");
                        if (strKey.toLowerCase().contains(key.toLowerCase())){
                            listFiltered.add(row);
                        }
                    }

                    filteredMovieList = listFiltered;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredMovieList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                filteredMovieList = (List<Movie_home>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
