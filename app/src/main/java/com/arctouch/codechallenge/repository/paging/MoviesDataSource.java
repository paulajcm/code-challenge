package com.arctouch.codechallenge.repository.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.arctouch.codechallenge.repository.MovieRepository;
import com.arctouch.codechallenge.repository.data.Cache;
import com.arctouch.codechallenge.repository.model.Genre;
import com.arctouch.codechallenge.repository.model.Movie;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class MoviesDataSource extends PageKeyedDataSource<Integer, Movie> {

    private CompositeDisposable compositeDisposable;
    private int sourceIndex;

    public MoviesDataSource(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
        sourceIndex = 1;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        MovieRepository.getInstance().getAllUpcomingMovies(sourceIndex)
                .doOnSubscribe(disposable -> compositeDisposable.add(disposable))
                .subscribe(response -> {
                    for (Movie movie : response.results) {
                        movie.genres = new ArrayList<>();
                        for (Genre genre : Cache.getGenres()) {
                            if (movie.genreIds.contains(genre.id)) {
                                movie.genres.add(genre);
                            }
                        }
                    }
                    sourceIndex++;
                    callback.onResult(response.results, null, sourceIndex);
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        MovieRepository.getInstance().getAllUpcomingMovies(sourceIndex)
                .doOnSubscribe(disposable -> compositeDisposable.add(disposable))
                .subscribe(response -> {
                    for (Movie movie : response.results) {
                        movie.genres = new ArrayList<>();
                        for (Genre genre : Cache.getGenres()) {
                            if (movie.genreIds.contains(genre.id)) {
                                movie.genres.add(genre);
                            }
                        }
                    }
                    sourceIndex++;
                    callback.onResult(response.results, sourceIndex);
                });
    }
}

