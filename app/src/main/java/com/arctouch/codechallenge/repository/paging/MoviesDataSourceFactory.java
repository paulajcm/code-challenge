package com.arctouch.codechallenge.repository.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.arctouch.codechallenge.repository.model.Movie;

import io.reactivex.disposables.CompositeDisposable;

public class MoviesDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private CompositeDisposable compositeDisposable;

    public MoviesDataSourceFactory(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        MoviesDataSource dataSource = new MoviesDataSource(compositeDisposable);
        return dataSource;
    }
}
