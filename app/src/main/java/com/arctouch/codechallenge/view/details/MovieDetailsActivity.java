package com.arctouch.codechallenge.view.details;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.repository.model.Movie;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MovieDetailsActivity extends AppCompatActivity {

    private MovieDetailsViewModel viewModel;

    private Toolbar toolbar;
    private TextView releaseDateTextView;
    private TextView genresTextView;
    private TextView overviewTextView;
    private ImageView posterImageView;
    private ImageView backdropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        attachUi();

        int movieId = getIntent().getIntExtra("MOVIE_ID", 0);
        viewModel.loadMovie(movieId).observe(this, this::setupUi);
    }

    private void attachUi() {
        toolbar = findViewById(R.id.movieDetails_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        releaseDateTextView = findViewById(R.id.movieDetails_releaseDateText);
        genresTextView = findViewById(R.id.movieDetails_genresText);
        overviewTextView = findViewById(R.id.movieDetails_overviewText);
        posterImageView = findViewById(R.id.movieDetails_poster);
        backdropImageView = findViewById(R.id.movieDetails_backdrop);
    }

    private void setupUi(Movie movie) {
        toolbar.setTitle(movie.title);
        releaseDateTextView.setText(movie.releaseDate);
        genresTextView.setText(TextUtils.join(", ", movie.genres));
        overviewTextView.setText(movie.overview);
        if (!TextUtils.isEmpty(movie.posterPath)) {
            Glide.with(this)
                    .load(MovieImageUrlBuilder.buildPosterUrl(movie.posterPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder_white))
                    .into(posterImageView);
        }
        if (!TextUtils.isEmpty(movie.backdropPath)) {
            Glide.with(this)
                    .load(MovieImageUrlBuilder.buildBackdropUrl(movie.backdropPath))
                    .apply(new RequestOptions().placeholder(R.drawable.ic_image_placeholder_white))
                    .into(backdropImageView);
        }
    }
}
