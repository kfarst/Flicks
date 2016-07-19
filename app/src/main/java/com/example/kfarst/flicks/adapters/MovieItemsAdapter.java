package com.example.kfarst.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kfarst.flicks.R;
import com.example.kfarst.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kfarst on 7/13/16.
 */
public class MovieItemsAdapter extends RecyclerView.Adapter<MovieItemsAdapter.ViewHolder> {
    private List<Movie> mMovies;

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    private int orientation;

    // Pass in the contact array into the constructor
    public MovieItemsAdapter(List<Movie> listItems, int displayWidth) {
        mMovies = listItems;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView movieTitle;
        public TextView movieSummary;
        public ImageView moviePoster;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieSummary = (TextView) itemView.findViewById(R.id.movieSummary);
            moviePoster = (ImageView) itemView.findViewById(R.id.moviePoster);
        }
    }

    @Override
    public MovieItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View listItemView = inflater.inflate(R.layout.item_movie, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(listItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieItemsAdapter.ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);

        holder.movieTitle.setText(movie.getTitle());
        holder.movieSummary.setText(movie.getOverview());

        Picasso
                .with(holder.moviePoster.getContext())
                .load(imageUrl(orientation == Configuration.ORIENTATION_PORTRAIT ? movie.getPosterPath() : movie.getBackdropPath()))
                .placeholder(orientation == Configuration.ORIENTATION_PORTRAIT ? R.drawable.portrait_placeholder : R.drawable.landscape_placeholder)
                //.resize(0, holder.moviePoster.getHeight())
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    private String imageUrl (String imageName) {
        return "https://image.tmdb.org/t/p/w342" + imageName;
    }

    // Clean all elements of the recycler
    public void clear() {
        mMovies.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(ArrayList<Movie> movies) {
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }
}
