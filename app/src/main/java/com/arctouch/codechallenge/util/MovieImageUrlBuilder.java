package com.arctouch.codechallenge.util;

import com.arctouch.codechallenge.repository.api.TmdbApi;

public class MovieImageUrlBuilder {

    private static final String BACKDROP_URL = "https://image.tmdb.org/t/p/w780";
    private static final String POSTER_URL = "https://image.tmdb.org/t/p/w154";

    public static String buildPosterUrl(String posterPath) {
        return POSTER_URL + posterPath + "?api_key=" + TmdbApi.API_KEY;
    }

    public static String buildBackdropUrl(String backdropPath) {
        return BACKDROP_URL + backdropPath + "?api_key=" + TmdbApi.API_KEY;
    }
}
