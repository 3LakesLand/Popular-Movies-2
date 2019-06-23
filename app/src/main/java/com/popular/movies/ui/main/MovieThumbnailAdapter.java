package com.popular.movies.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.popular.movies.R;
import com.popular.movies.ui.model.MovieThumbnail;
import com.popular.movies.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter allows the loading and display of the individual small movie posters (via grid layout items).
 */
public class MovieThumbnailAdapter extends RecyclerView.Adapter<MovieThumbnailAdapter.MyViewHolder> {
    private static final String NO_IMAGE_FOUND = "No image found for title:\n\n";

    private List<MovieThumbnail> movieThumbnails;
    private OnMovieThumbnailListener onMovieThumbnailListener;

    /**
     * The Adapter depicts each movie poster as a small image in a grid view.
     *
     * @param onMovieThumbnailListener for selecting the film
     */
    MovieThumbnailAdapter(OnMovieThumbnailListener onMovieThumbnailListener) {
        this.onMovieThumbnailListener = onMovieThumbnailListener;
        movieThumbnails = new ArrayList<>();
    }

    /**
     * The method saves the movies for the overview page
     *
     * @param movieThumbnails list of the movies (ID, Title and URL on the movie poster)
     */
    void setMovieThumbnailResponse(List<MovieThumbnail> movieThumbnails) {
        this.movieThumbnails = movieThumbnails;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    /**
     * The View Holder binds the movie data object.
     *
     * @param holder   current view holder
     * @param position in the adapter
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(movieThumbnails.get(position));
    }

    /**
     * Count of all movie film thumbnail objects.
     *
     * @return count
     */
    @Override
    public int getItemCount() {
        return movieThumbnails.size();
    }

    /**
     * Listener for selecting the film from the film overview page.
     */
    public interface OnMovieThumbnailListener {
        void onMovieThumbnailClick(MovieThumbnail movieThumbnail);
    }

    /**
     * The ViewHolder contains the movie image, movie title, the Progress Bar
     * (which is displayed when the image is loaded) and the replacement Text View
     * if no movie image can be loaded.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movieThumbnailImage;
        ProgressBar loadingIndicator;
        TextView noImageFoundTextView;
        String movieTitle;

        /**
         * Constructor
         *
         * @param itemView current item view
         */
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movieThumbnailImage = itemView.findViewById(R.id.movie_img);
            loadingIndicator = itemView.findViewById(R.id.loading_indicator);
            noImageFoundTextView = itemView.findViewById(R.id.no_image_found_tv);
            itemView.setOnClickListener(this);
        }

        /**
         * The View Holder binds the movie data object.
         * In addition, a large film poster for detailed presentation is loaded
         *
         * @param movieThumbnail contains current movie poster URL and film title
         */
        void bind(MovieThumbnail movieThumbnail) {
            movieTitle = movieThumbnail.getTitle();
            loadingIndicator.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(Utils.SMALL_IMAGE_URL + movieThumbnail.getPosterPath())
                    .into(movieThumbnailImage, new Callback() {
                        /**
                         * When successfully loading, the progressive bar and
                         * "noImageFound" error page are hidden.
                         */
                        @Override
                        public void onSuccess() {
                            loadingIndicator.setVisibility(View.GONE);
                            noImageFoundTextView.setVisibility(View.GONE);
                            movieThumbnailImage.setVisibility(View.VISIBLE);
                        }

                        /**
                         * When not successful, the progressive bar and ImageView are hidden.
                         * For this, the "noImageFound" error text is displayed.
                         * @param ex of the response error of the movie DB server
                         */
                        @Override
                        public void onError(Exception ex) {
                            loadingIndicator.setVisibility(View.GONE);
                            String errorText = NO_IMAGE_FOUND + movieTitle;
                            noImageFoundTextView.setText(errorText);
                            noImageFoundTextView.setVisibility(View.VISIBLE);
                            movieThumbnailImage.setVisibility(View.GONE);
                        }
                    });
        }

        /**
         * The current position is used to determine the film data (Film ID)
         * for the visit of the detail page.
         *
         * @param view current view - not used
         */
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            MovieThumbnail movieThumbnail = movieThumbnails.get(position);
            onMovieThumbnailListener.onMovieThumbnailClick(movieThumbnail);
        }
    }
}
