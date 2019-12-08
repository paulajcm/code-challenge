package com.arctouch.codechallenge.repository;

import com.arctouch.codechallenge.repository.api.TmdbApi;
import com.arctouch.codechallenge.repository.data.Cache;
import com.arctouch.codechallenge.repository.model.UpcomingMoviesResponse;

import java.util.Locale;

import io.reactivex.Observable;
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

    public Observable<UpcomingMoviesResponse> getAllUpcomingMovies(long page) {
        return api.genres(TmdbApi.API_KEY, Locale.getDefault().getLanguage()).concatMap(genreResponse -> {
            Cache.setGenres(genreResponse.genres);
            return api.upcomingMovies(TmdbApi.API_KEY, Locale.getDefault().getLanguage(), page, Locale.getDefault().getCountry());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
