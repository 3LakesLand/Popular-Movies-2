package com.popular.movies.ui.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popular.movies.R;
import com.popular.movies.ui.model.MovieTrailer;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter allows the loading and display of the movie trailers.
 */
public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MyViewHolder> {

    private OnMovieTrailerListener onMovieTrailerListener;
    private List<MovieTrailer> movieTrailers;

    /**
     * Constructor
     *
     * @param onMovieTrailerListener for selecting the trailer
     */
    MovieTrailerAdapter(OnMovieTrailerListener onMovieTrailerListener) {
        this.onMovieTrailerListener = onMovieTrailerListener;
        movieTrailers = new ArrayList<>();
    }

    /**
     * The method creates a View Holder object.
     *
     * @param viewGroup contains the application context
     * @param viewType  - not used
     * @return new View Holder object
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_trailer_item, viewGroup, false);
        return new MovieTrailerAdapter.MyViewHolder(view);
    }

    /**
     * The View Holder binds the trailer data object.
     *
     * @param holder   current view holder
     * @param position in the adapter
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(movieTrailers.get(position));
    }

    /**
     * Count of all movie trailer objects.
     *
     * @return count
     */
    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }

    void setMovieTrailerResponse(List<MovieTrailer> movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    /**
     * Listener for selecting the trailer from the trailer overview list page.
     */
    public interface OnMovieTrailerListener {
        void onMovieTrailerClick(MovieTrailer movieTrailer);
    }

    /**
     * The ViewHolder contains the trailer name.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView trailerName;

        /**
         * Constructor
         *
         * @param itemView current item view
         */
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.trailer_name_tv);

            itemView.setOnClickListener(this);
        }

        /**
         * The View Holder binds the trailer name object.
         *
         * @param movieTrailer contains current author and content of the review
         */
        void bind(MovieTrailer movieTrailer) {
            trailerName.setText(movieTrailer.getName());
        }

        /**
         * The current position is used to determine the trailer (youtube URL)
         * for the visit of the youtube page.
         *
         * @param view current view - not used
         */
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            MovieTrailer movieTrailer = movieTrailers.get(position);
            onMovieTrailerListener.onMovieTrailerClick(movieTrailer);
        }
    }
}
