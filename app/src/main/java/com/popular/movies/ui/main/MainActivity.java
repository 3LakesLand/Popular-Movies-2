package com.popular.movies.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.popular.movies.R;
import com.popular.movies.ui.detail.DetailActivity;
import com.popular.movies.ui.model.MovieThumbnail;
import com.popular.movies.ui.model.MovieThumbnailViewModel;
import com.popular.movies.utils.MarkOfMovie;
import com.popular.movies.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * The project shows a List of film images to display. If an image is selected, then more movie information
 * about the movie will appear on the detail page (review and trailer can be accessed via the Internet and Youtube).
 * In addition, individual movies can be declared local favorites (these movies are stored in the local room database).
 * <p>
 * You set the MOVIE_DB_API_KEY in the build.gradle file.
 * <p>
 * The project uses the repository and MVVM architecture of https://developer.android.com/jetpack/docs/guide
 * to split the application into different layers (Separation of Concerns).
 * The surface calls the display data only from the repository. The repository organizes data delivery and storage.
 * To prevent the dependency between DB data (external and internal) with the surface, the live data is copied (via MutableLiveData).
 * <p>
 * The data is updated via the Observer pattern, always taking into account the lifecycle of the activity involved.
 * <p>
 * <p>
 * <p>
 * The MainActivity class describes the main activity with the menu selection popular, top rated and favorite movie thumbnail.
 * After the menu selection popular or top rated, the data is selected from an external Movie DB.
 * The personal movie favorites come from the local Room database.
 * The class gives the commands to its MovieModel (selectMovieThumbnails) via the menu.
 * The model passes them on to the repository, with the Response data always stored as a list in the same MovieThumbnail class.
 */
public class MainActivity extends AppCompatActivity implements MovieThumbnailAdapter.OnMovieThumbnailListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_view)
    TextView noInternetFoundTextView;


    //for ButterKnife framework
    private Unbinder unbinder;

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * Popular or Top rated or Favorite can be selected through the menu.
     *
     * @param menu current Menu
     * @return true (inflated the menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * Shows the list of film images (small size).
     *
     * @param savedInstanceState If non-null, this Activity is being re-constructed
     *                           from a previous saved state as given here
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Customises the ActionBar name of the selected search method (default value: Popular).
         */
        if (getSupportActionBar() != null) {
            String actionBarText = getString(R.string.movie_text_into_action_bar) +
                    getString(R.string.popular_text_into_action_bar);
            getSupportActionBar().setTitle(actionBarText);
        }


        //bind the RecyclerView and the TextViews
        unbinder = ButterKnife.bind(this);

        recyclerView.setHasFixedSize(Boolean.TRUE);

        loadMovieThumbnails();
    }

    /**
     * Loads the Movie Thumbnails via MovieModel from the MovieRepository.
     * Once the data is loaded, the Observer updates the movie display.
     * If Internet access is not possible, an error message is displayed.
     */
    private void loadMovieThumbnails() {
        final MovieThumbnailViewModel movieThumbnailViewModel = ViewModelProviders.of(this).get(MovieThumbnailViewModel.class);

        final MovieThumbnailAdapter movieThumbnailAdapter = new MovieThumbnailAdapter(this);
        recyclerView.setAdapter(movieThumbnailAdapter);
        movieThumbnailViewModel.getMovieThumbnailFavoriteList().observe(this, new Observer<List<MovieThumbnail>>() {
            @Override
            public void onChanged(@Nullable List<MovieThumbnail> movieThumbnails) {
                if (MarkOfMovie.FAVORITE.equals(movieThumbnailViewModel.getMarkOfMovie())) {
                    movieThumbnailAdapter.setMovieThumbnailResponse(movieThumbnails);
                    movieThumbnailAdapter.notifyDataSetChanged();
                }
            }
        });
        movieThumbnailViewModel.getMovieThumbnailPopularList().observe(this, new Observer<List<MovieThumbnail>>() {
            @Override
            public void onChanged(@Nullable List<MovieThumbnail> movieThumbnails) {
                if (MarkOfMovie.POPULAR.equals(movieThumbnailViewModel.getMarkOfMovie())) {
                    movieThumbnailAdapter.setMovieThumbnailResponse(movieThumbnails);
                    movieThumbnailAdapter.notifyDataSetChanged();
                }
            }
        });
        movieThumbnailViewModel.getMovieThumbnailTopRatedList().observe(this, new Observer<List<MovieThumbnail>>() {
            @Override
            public void onChanged(@Nullable List<MovieThumbnail> movieThumbnails) {
                if (MarkOfMovie.TOP_RATED.equals(movieThumbnailViewModel.getMarkOfMovie())) {
                    movieThumbnailAdapter.setMovieThumbnailResponse(movieThumbnails);
                    movieThumbnailAdapter.notifyDataSetChanged();
                }
            }
        });

        checkInternetAndSelectMovieThumbnails();

    }

    /**
     * The method check the internet connection.
     * When true, then select movie thumbnails.
     * When false, the "no internet connection" was showed.
     *
     */
    private void checkInternetAndSelectMovieThumbnails() {
        final MovieThumbnailViewModel movieThumbnailViewModel = ViewModelProviders.of(this).get(MovieThumbnailViewModel.class);

        if (movieThumbnailViewModel.isInternetConnection()) {
            noInternetFoundTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            movieThumbnailViewModel.selectMovieThumbnails(this);
        } else {
            noInternetFoundTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
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
     * Evaluation of the menu selection.
     * - Customises the ActionBar name of the selected search method (Popular or top rated)
     * - If there is an Internet connection, we will reload the film images depending
     * on the menu selection (search criterion).
     *
     * @param item - search criterion
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final MovieThumbnailViewModel movieThumbnailViewModel = ViewModelProviders.of(this).get(MovieThumbnailViewModel.class);

        switch (item.getItemId()) {
            case R.id.popular_item:
                movieThumbnailViewModel.setMarkOfMovie(MarkOfMovie.POPULAR);
                if (getSupportActionBar() != null) {
                    String actionBarText = getString(R.string.movie_text_into_action_bar) +
                            getString(R.string.popular_text_into_action_bar);
                    getSupportActionBar().setTitle(actionBarText);
                }
                checkInternetAndSelectMovieThumbnails();
                break;
            case R.id.top_rated_item:
                movieThumbnailViewModel.setMarkOfMovie(MarkOfMovie.TOP_RATED);
                if (getSupportActionBar() != null) {
                    String actionBarText = getString(R.string.movie_text_into_action_bar) +
                            getString(R.string.top_rated_text_into_action_bar);
                    getSupportActionBar().setTitle(actionBarText);
                }
                checkInternetAndSelectMovieThumbnails();
                break;
            case R.id.favorite_item:
                movieThumbnailViewModel.setMarkOfMovie(MarkOfMovie.FAVORITE);
                if (getSupportActionBar() != null) {
                    String actionBarText = getString(R.string.movie_text_into_action_bar) +
                            getString(R.string.favorite_text_into_action_bar);
                    getSupportActionBar().setTitle(actionBarText);
                }
                noInternetFoundTextView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                movieThumbnailViewModel.selectMovieThumbnails(this);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }


    /**
     * When a movie image is clicked, the detail page for that movie is called.
     * For this purpose, the ID of the image is passed.
     * As long as the DetailActivity is active, the Observer is not needed in the MainActivity
     * (it will be deleted).
     *
     * @param movieThumbnail contains the ID of the image
     */
    @Override
    public void onMovieThumbnailClick(MovieThumbnail movieThumbnail) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Utils.MOVIE_THUMBNAIL_KEY, movieThumbnail.getMovieId());
        startActivity(intent);
    }


}
