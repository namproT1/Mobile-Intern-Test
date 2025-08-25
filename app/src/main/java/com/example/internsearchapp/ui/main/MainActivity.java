package com.example.internsearchapp.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.internsearchapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private TextInputEditText etSearch;
    private RecyclerView rvResults;
    private TextView tvEmpty;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable debounceRunnable;
    private String currentQuery = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.etSearch);
        rvResults = findViewById(R.id.rvResults);
        tvEmpty = findViewById(R.id.tvEmpty);

        rvResults.setLayoutManager(new LinearLayoutManager(this));
        LocationAdapter adapter = new LocationAdapter(this, item -> {
            // Open Google Maps with directions
            try {
                Uri uri = Uri.parse("geo:" + item.getLat() + "," + item.getLon() + "?q=" + Uri.encode(item.getDisplayName()));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            } catch (ActivityNotFoundException e) {
                // Fallback if Google Maps not installed
                Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + item.getLat() + "," + item.getLon());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(webIntent);
            }
        });
        rvResults.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, new MainViewModel.Factory(getApplication()))
                .get(MainViewModel.class);

        viewModel.getResults().observe(this, places -> {
            adapter.submitList(places, currentQuery);
            tvEmpty.setVisibility((places == null || places.isEmpty()) ? View.VISIBLE : View.GONE);
            if (places == null || places.isEmpty()) {
                tvEmpty.setText(currentQuery.isEmpty() ? R.string.empty_state : R.string.no_result);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                currentQuery = s.toString().trim();
                if (debounceRunnable != null) handler.removeCallbacks(debounceRunnable);
                debounceRunnable = () -> viewModel.search(currentQuery);
                handler.postDelayed(debounceRunnable, 1000); // 1s debounce
            }
        });
    }
}
