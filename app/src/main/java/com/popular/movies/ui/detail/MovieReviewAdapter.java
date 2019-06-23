package com.popular.movies.ui.detail;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popular.movies.R;
import com.popular.movies.ui.model.MovieReview;

import java.util.ArrayList;
import java.util.List;

/**
 * The adapter allows the loading and display of the movie reviews.
 */
public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MyViewHolder> {
    private static final String AUTHOR = "Author: ";
    private OnMovieReviewListener onMovieReviewListener;
    private List<MovieReview> movieReviews;

    /**
     * Constructor
     *
     * @param onMovieReviewListener for selecting the review
     */
    MovieReviewAdapter(OnMovieReviewListener onMovieReviewListener) {
        this.onMovieReviewListener = onMovieReviewListener;
        movieReviews = new ArrayList<>();
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_review_item, viewGroup, false);
        return new MovieReviewAdapter.MyViewHolder(view);
    }

    /**
     * The View Holder binds the review data object.
     *
     * @param holder   current view holder
     * @param position in the adapter
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(movieReviews.get(position));
    }

    /**
     * Count of all movie review objects.
     *
     * @return count
     */
    @Override
    public int getItemCount() {
        return movieReviews.size();
    }

    void setMovieReviewResponse(List<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }

    /**
     * Listener for selecting the review from the review overview list page.
     */
    public interface OnMovieReviewListener {
        void onMovieReviewClick(MovieReview movieReview);
    }

    /**
     * The ViewHolder contains the author and the content of the review.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView reviewAuthor;
        private TextView reviewContent;

        /**
         * Constructor
         *
         * @param itemView current item view
         */
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.review_author_tv);
            reviewContent = itemView.findViewById(R.id.review_content_tv);

            itemView.setOnClickListener(this);
        }

        /**
         * The View Holder binds the review data object.
         *
         * @param movieReview contains current author and content of the review
         */
        @SuppressLint("SetTextI18n")
        void bind(MovieReview movieReview) {
            reviewAuthor.setText(AUTHOR + movieReview.getAuthor());
            reviewContent.setText(movieReview.getContent());
        }

        /**
         * The current position is used to determine the review (review URL)
         * for the visit of the web page.
         *
         * @param view current view - not used
         */
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            MovieReview movieReview = movieReviews.get(position);
            onMovieReviewListener.onMovieReviewClick(movieReview);
        }
    }
}
