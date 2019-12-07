package com.arctouch.codechallenge.repository;

import androidx.lifecycle.MutableLiveData;

import com.arctouch.codechallenge.repository.api.TmdbApi;
import com.arctouch.codechallenge.repository.data.Cache;
import com.arctouch.codechallenge.repository.model.Genre;
import com.arctouch.codechallenge.repository.model.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MovieRepository {

    private TmdbApi api = new Retrofit.Builder()
            .baseUrl(TmdbApi.URL)
            .client(new OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TmdbApi.class);

    private static MovieRepository INSTANCE;

    public static MovieRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MovieRepository();
        }
        return INSTANCE;
    }

    public MutableLiveData<List<Movie>> getAllUpcomingMovies() {
        MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
        api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE).concatMap(genreResponse -> {
            Cache.setGenres(genreResponse.genres);
            return api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, 1L, TmdbApi.DEFAULT_REGION);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    for (Movie movie : response.results) {
                        movie.genres = new ArrayList<>();
                        for (Genre genre : Cache.getGenres()) {
                            if (movie.genreIds.contains(genre.id)) {
                                movie.genres.add(genre);
                            }
                        }
                    }
                    movies.setValue(response.results);
                });

        return movies;
    }
}
