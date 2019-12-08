package com.arctouch.codechallenge.view.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.util.NetworkChecker;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel viewModel;
    private HomeAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private BroadcastReceiver networkReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupUi();
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadData();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkReceiver);
    }

    private void setupUi() {

        this.progressBar = findViewById(R.id.progressBar);
        this.recyclerView = findViewById(R.id.recyclerView);

        adapter = new HomeAdapter();
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        if (NetworkChecker.isNetworkAvailable(getApplicationContext())) {
            viewModel.loadData().observe(this, movies -> {
                adapter.submitList(movies);
                progressBar.setVisibility(View.GONE);
            });
        } else {
            Snackbar.make(progressBar, R.string.waiting_network, BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }

}
