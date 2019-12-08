package com.arctouch.codechallenge.view.home;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;

import com.arctouch.codechallenge.R;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel viewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupUi();
    }

    private void setupUi() {

        this.progressBar = findViewById(R.id.progressBar);
        this.recyclerView = findViewById(R.id.recyclerView);

        HomeAdapter adapter = new HomeAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.loadData().observe(this, movies -> {
            adapter.submitList(movies);
            progressBar.setVisibility(View.GONE);
        });
    }

}
