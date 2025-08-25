package com.example.internsearchapp.data.network;

import com.example.internsearchapp.data.model.LocationIQPlace;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search.php")
    Call<List<LocationIQPlace>> search(
            @Query("key") String apiKey,
            @Query("q") String query,
            @Query("format") String format,
            @Query("limit") int limit
    );
}
