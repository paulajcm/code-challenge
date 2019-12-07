package com.arctouch.codechallenge.home;

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

        this.recyclerView = findViewById(R.id.recyclerView);
        this.progressBar = findViewById(R.id.progressBar);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.loadData().observe(this, movies -> {
            recyclerView.setAdapter(new HomeAdapter(movies));
            progressBar.setVisibility(View.GONE);
        });
    }

}
