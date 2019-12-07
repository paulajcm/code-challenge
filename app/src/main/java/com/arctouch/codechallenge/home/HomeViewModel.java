package com.arctouch.codechallenge.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.arctouch.codechallenge.repository.MovieRepository;
import com.arctouch.codechallenge.repository.model.Movie;

import java.util.List;


public class HomeViewModel extends ViewModel {

    private MovieRepository movieRepository;

    private LiveData<List<Movie>> upcomingMovies;

    public HomeViewModel() {
        movieRepository = MovieRepository.getInstance();
        upcomingMovies = movieRepository.getAllUpcomingMovies();
    }


    LiveData<List<Movie>> loadData() {
        return upcomingMovies;
    }
}
