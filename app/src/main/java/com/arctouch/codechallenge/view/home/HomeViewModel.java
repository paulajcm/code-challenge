package com.arctouch.codechallenge.view.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.arctouch.codechallenge.repository.paging.MoviesDataSourceFactory;
import com.arctouch.codechallenge.repository.model.Movie;

import io.reactivex.disposables.CompositeDisposable;


public class HomeViewModel extends ViewModel {

    private static final int PAGE_SIZE = 20;
    private MoviesDataSourceFactory moviesDataSourceFactory;
    private LiveData<PagedList<Movie>> upcomingMovies;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public HomeViewModel() {
        moviesDataSourceFactory = new MoviesDataSourceFactory(compositeDisposable);

        initPaging();
    }

    private void initPaging() {

        PagedList.Config config =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(PAGE_SIZE)
                        .setPageSize(PAGE_SIZE).build();

        upcomingMovies = new LivePagedListBuilder<>(moviesDataSourceFactory, config)
                .build();
    }


    LiveData<PagedList<Movie>> loadData() {
        return upcomingMovies;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
