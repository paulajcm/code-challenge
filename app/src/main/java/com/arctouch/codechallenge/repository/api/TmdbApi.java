package com.arctouch.codechallenge.repository.api;

import com.arctouch.codechallenge.repository.model.GenreResponse;
import com.arctouch.codechallenge.repository.model.Movie;
import com.arctouch.codechallenge.repository.model.UpcomingMoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TmdbApi {

    String URL = "https://api.themoviedb.org/3/";
    String API_KEY = "1f54bd990f1cdfb230adb312546d765d";

    @GET("genre/movie/list")
    Observable<GenreResponse> genres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/upcoming")
    Observable<UpcomingMoviesResponse> upcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") Long page,
            @Query("region") String region
    );

    @GET("movie/{id}")
    Observable<Movie> movie(
            @Path("id") Long id,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
