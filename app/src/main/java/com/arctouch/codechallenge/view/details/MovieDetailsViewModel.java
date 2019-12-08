package com.arctouch.codechallenge.view.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arctouch.codechallenge.repository.MovieRepository;
import com.arctouch.codechallenge.repository.model.Movie;

import io.reactivex.disposables.CompositeDisposable;

public class MovieDetailsViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    LiveData<Movie> loadMovie(int movieId) {
        MutableLiveData<Movie> movie = new MutableLiveData<>();
        MovieRepository.getInstance().getMovie(movieId)
                .doOnSubscribe(disposable -> compositeDisposable.add(disposable))
                .subscribe(response -> {
                    movie.setValue(response);
                });
        return movie;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
