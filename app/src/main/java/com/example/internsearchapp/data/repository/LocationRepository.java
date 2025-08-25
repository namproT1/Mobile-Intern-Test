package com.example.internsearchapp.data.repository;

import android.app.Application;
import android.util.Log;

import com.example.internsearchapp.BuildConfig;
import com.example.internsearchapp.data.model.LocationIQPlace;
import com.example.internsearchapp.data.network.ApiService;
import com.example.internsearchapp.data.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRepository {

    private final ApiService api;

    public interface CallbackList { void onResult(List<LocationIQPlace> list); }

    public LocationRepository(Application app) {
        api = RetrofitClient.create(ApiService.class);
    }

    public void search(String query, CallbackList cb) {
        String key = BuildConfig.LOCATIONIQ_API_KEY;
        api.search(key, query, "json", 20).enqueue(new Callback<List<LocationIQPlace>>() {
            @Override
            public void onResponse(Call<List<LocationIQPlace>> call, Response<List<LocationIQPlace>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cb.onResult(response.body());
                } else {
                    cb.onResult(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<LocationIQPlace>> call, Throwable t) {
                cb.onResult(new ArrayList<>());
            }
        });
    }
}
