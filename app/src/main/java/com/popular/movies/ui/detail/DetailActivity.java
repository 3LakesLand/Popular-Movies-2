package com.popular.movies.ui.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.popular.movies.R;
import com.popular.movies.ui.model.Movie;
import com.popular.movies.ui.model.MovieReview;
import com.popular.movies.ui.model.MovieTrailer;
import com.popular.movies.ui.model.MovieViewModel;
import com.popular.movies.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A larger movie poster is displayed in the detailed view for the selected film.
 * In addition to the title, the release date, the genres, the film voting in stars and
 * the popularity, the film content is also issued.
 * <p>
 * In addition, existing Film Review can be accessed via Web Retrieval (Web browser) and Film Trailer via YouTube.
 * The film can be named a personal favorite or revoked via a FloatingActionButton.
 * As a movie favorite, it is stored in the local database and can be displayed via Menu Favorite call in the MainActivity.
 * <p>
 * The display of the film information, the review and trailer lists is done via observer patterns.
 * The query and display of the data is made possible via a separate task and
 * allows the independent display of the individual movie data.
 */
public class DetailActivity extends AppCompatActivity implements
        MovieReviewAdapter.OnMovieReviewListener, MovieTrailerAdapter.OnMovieTrailerListener,
        View.OnClickListener {
    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final Integer BAD_INTEGER = -1;

    @BindView(R.id.movie_img_iv)
    ImageView movie_img;
    @BindView(R.id.pop_tv)
    TextView popularity;
    @BindView(R.id.ratingBar)
    RatingBar voteAverage;
    @BindView(R.id.title_tv)
    TextView movieTitle;
    @BindView(R.id.release_date_tv)
    TextView releaseDate;
    @BindView(R.id.genre_tv)
    TextView genre;
    @BindView(R.id.overview_tv)
    TextView overview;
    @BindView(R.id.empty_view2)
    TextView noInternetFoundTextView;
    @BindView(R.id.loading_indicator_detail)
    ProgressBar loadingIndicatorDetail;
    @BindView(R.id.no_image_detail_found_tv)
    TextView noImageDetailFoundTextView;
    @BindView(R.id.star_bt)
    FloatingActionButton starButton;
    @BindView(R.id.review_rv)
    RecyclerView review;
    @BindView(R.id.trailer_rv)
    RecyclerView trailer;

    //for ButterKnife framework
    private Unbinder unbinder;


    private MovieReviewAdapter movieReviewAdapter;
    private MovieTrailerAdapter movieTrailerAdapter;

    /**
     * The method loads the film data based via film ID and shows the data.
     *
     * @param savedInstanceState If non-null, this Activity is being re-constructed
     *                           from a previous saved state as given here
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "onCreate");

        final MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        //bind the ImageView, ProgressBar and the TextViews
        unbinder = ButterKnife.bind(this);

        if (movieViewModel.isInternetConnection()) {
            noInternetFoundTextView.setVisibility(View.GONE);

            starButton.setOnClickListener(this);

            Intent intent = getIntent();
            if (intent != null) {
                Integer movieID = intent.getIntExtra(Utils.MOVIE_THUMBNAIL_KEY, BAD_INTEGER);
                Movie movie = new Movie(movieID);
                movieViewModel.getMovie().setValue(movie);

                loadingIndicatorDetail.setVisibility(View.VISIBLE);
                //Presentation of the movie information
                movieViewModel.getMovie().observe(this, new Observer<Movie>() {
                    @Override
                    public void onChanged(@Nullable Movie movie) {
                        loadingIndicatorDetail.setVisibility(View.INVISIBLE);
                        if (movie == null) {
                            /*
                             * If the response is not OK, then the message "no Internet connection" is showed.
                             */
                            noInternetFoundTextView.setVisibility(View.VISIBLE);
                        } else {
                            //Depending on whether the movie is in the favorite database, the button is displayed accordingly
                            if (movie.isFavorite()) {
                                starButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.buttonColorAccent)));
                            } else {
                                starButton.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.buttonColorGray)));
                            }

                            noInternetFoundTextView.setVisibility(View.GONE);
                            if (getSupportActionBar() != null) {
                                getSupportActionBar().setTitle(getString(R.string.movie_text_into_action_bar)
                                        + movie.getTitle());
                            }
                            popularity.setText(movie.getPopularity());
                            voteAverage.setRating(Float.parseFloat(movie.getVoteAverage()));
                            movieTitle.setText(movie.getTitle());
                            genre.setText(movie.getGenres());
                            releaseDate.setText(movie.getReleaseDate());
                            overview.setText(movie.getOverview());

                            if (movie.getMovieImage() == null) {
                                /*
                                 * When not successful, the progressive bar and ImageView are hidden.
                                 * For this, the "noImageFound" error text is displayed.
                                 */
                                loadingIndicatorDetail.setVisibility(View.GONE);
                                String errorText = getString(R.string.no_image_found) + movie.getTitle();
                                noImageDetailFoundTextView.setText(errorText);
                                noImageDetailFoundTextView.setVisibility(View.VISIBLE);
                                movie_img.setVisibility(View.GONE);

                            } else {
                                /*
                                 * When successfully loading, the progressive bar and
                                 * "noImageFound" error page are hidden and the movie image data will be showed.
                                 */
                                loadingIndicatorDetail.setVisibility(View.GONE);
                                noImageDetailFoundTextView.setVisibility(View.GONE);
                                movie_img.setVisibility(View.VISIBLE);
                                movie_img.setImageBitmap(movie.getMovieImage());

                            }
                        }

                    }

                });

                //Presentation of the movie reviews
                movieReviewAdapter = new MovieReviewAdapter(this);
                review.setHasFixedSize(true);
                review.setAdapter(movieReviewAdapter);
                movieViewModel.getMovieReview().observe(this, new Observer<List<MovieReview>>() {
                    @Override
                    public void onChanged(@Nullable final List<MovieReview> movieReviews) {
                        movieReviewAdapter.setMovieReviewResponse(movieReviews);
                        movieReviewAdapter.notifyDataSetChanged();
                    }
                });

                //Presentation of the movie trailers
                movieTrailerAdapter = new MovieTrailerAdapter(this);
                trailer.setHasFixedSize(true);
                trailer.setAdapter(movieTrailerAdapter);
                movieViewModel.getMovieTrailerList().observe(this, new Observer<List<MovieTrailer>>() {
                    @Override
                    public void onChanged(@Nullable List<MovieTrailer> movieTrailers) {
                        movieTrailerAdapter.setMovieTrailerResponse(movieTrailers);
                        movieTrailerAdapter.notifyDataSetChanged();
                    }
                });


                if (!movie.getMovieId().equals(BAD_INTEGER)) {
                    movieViewModel.selectMovie(movie.getMovieId());
                    movieViewModel.foundFavoriteMovie(this);
                    movieViewModel.selectMovieReview(movie.getMovieId());
                    movieViewModel.selectMovieTrailer(movie.getMovieId());
                }

            }
        } else {
            noInternetFoundTextView.setVisibility(View.VISIBLE);
            loadingIndicatorDetail.setVisibility(View.GONE);
            starButton.hide();
        }
    }


    /**
     * When the view is destroyed, the binder is reset.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        Log.d(TAG, "onDestroy()");

    }


    /**
     * When a review is selected, the method calls a Web Intent to view the review via the review URL.
     *
     * @param movieReview selected movie review
     */
    @Override
    public void onMovieReviewClick(MovieReview movieReview) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(movieReview.getUrl()));
        startActivity(intent);
    }

    /**
     * When a trailer is selected, the method calls a youtube Intent to view the trailer via the URL.
     *
     * @param movieTrailer selected movie trailer
     */
    @Override
    public void onMovieTrailerClick(MovieTrailer movieTrailer) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("www.youtube.com")
                .appendPath("watch")
                .appendQueryParameter("v", movieTrailer.getYouTubeVideoId());
        intent.setData(builder.build());
        startActivity(intent);
    }

    /**
     * If the movie is not already in the Favorites database, the method causes the movie to be written to the database.
     * If the movie is in the Favorites database, the method causes the movie to be deleted from the database.
     *
     * @param v current view not needed
     */
    @Override
    public void onClick(View v) {
        final MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.changeFavoriteStatus();
    }


}


