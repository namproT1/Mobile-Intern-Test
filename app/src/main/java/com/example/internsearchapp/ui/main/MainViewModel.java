package com.example.internsearchapp.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.internsearchapp.data.model.LocationIQPlace;
import com.example.internsearchapp.data.repository.LocationRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final LocationRepository repository;
    private final MutableLiveData<List<LocationIQPlace>> results = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new LocationRepository(application);
    }

    public LiveData<List<LocationIQPlace>> getResults() {
        return results;
    }

    public void search(String query) {
        if (query == null || query.isEmpty()) {
            results.setValue(java.util.Collections.emptyList());
            return;
        }
        repository.search(query, places -> results.postValue(places));
    }

    public static class Factory extends androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory {
        private final Application app;
        public Factory(@NonNull Application application) {
            super(application);
            this.app = application;
        }
        @NonNull @Override
        public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainViewModel(app);
        }
    }
}
