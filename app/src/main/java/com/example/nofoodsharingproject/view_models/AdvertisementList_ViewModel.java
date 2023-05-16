package com.example.nofoodsharingproject.view_models;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nofoodsharingproject.data.api.adverts.dto.ResponseActiveAdverts;
import com.example.nofoodsharingproject.data.api.adverts.AdvertsRepository;
import com.example.nofoodsharingproject.data.api.map.MapRepository;
import com.example.nofoodsharingproject.data.api.map.dto.MarketTitleResponse;
import com.example.nofoodsharingproject.models.Advertisement;
import com.example.nofoodsharingproject.models.LoaderStatus;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdvertisementList_ViewModel extends AndroidViewModel {
    public List<Advertisement> adverts = new ArrayList<>();
    private final MutableLiveData<List<Advertisement>> _adverts = new MutableLiveData<>();
    private final MutableLiveData<LoaderStatus> _status = new MutableLiveData<>();
    private final MutableLiveData<String> activeMarket = new MutableLiveData<>();
    private String userID;
    private final String[] fullListMarkets = new String[]{
            "Большая Андроньевская улица, 22", "1, микрорайон Парковый, Котельники",
            "Ковров пер., 8, стр. 1", "Нижегородская улица, 34"
    };

    public AdvertisementList_ViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Advertisement>> getAllAdverts(String userID) {
        this.userID = userID;
        _status.setValue(LoaderStatus.LOADING);

        if (activeMarket.getValue() == null || activeMarket.getValue().length() == 0) {
            MapRepository.getPinMarket("setter", userID).enqueue(new Callback<MarketTitleResponse>() {
                @Override
                public void onResponse(@NotNull Call<MarketTitleResponse> call, @NotNull Response<MarketTitleResponse> response) {
                    if (response.code() == 404 || response.code() == 400) loadAdverts("");
                    else if (response.code() == 200 && response.body() != null)
                        loadAdverts(response.body().getMarket());
                }

                @Override
                public void onFailure(@NotNull Call<MarketTitleResponse> call, @NotNull Throwable t) {
                    _status.setValue(LoaderStatus.FAILURE);
                    t.printStackTrace();
                }
            });
        } else loadAdverts(activeMarket.getValue());

        return _adverts;
    }

    public LiveData<LoaderStatus> getLoaderStatus() {
        return _status;
    }

    public void loadAdverts(String market) {
        if (LoaderStatus.Status.LOADED.equals(this.getLoaderStatus().getValue().getStatus())) _status.setValue(LoaderStatus.LOADING);
        AdvertsRepository.getListAdverts(market).enqueue(new Callback<ResponseActiveAdverts>() {
            @Override
            public void onResponse(@NotNull Call<ResponseActiveAdverts> call, @NotNull Response<ResponseActiveAdverts> response) {
                if (response.body() != null && response.code() != 400 && response.code() != 404) {
                    _adverts.setValue(Arrays.asList(response.body().getAdvertisements()));
                    if (LoaderStatus.Status.LOADING.equals(getLoaderStatus().getValue().getStatus())) _status.setValue(LoaderStatus.LOADED);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseActiveAdverts> call, @NotNull Throwable t) {
                _status.setValue(LoaderStatus.FAILURE);
                t.printStackTrace();
            }
        });
    }

    public LiveData<String> getMarket() {
        MapRepository.getPinMarket("setter", userID).enqueue(new Callback<MarketTitleResponse>() {
            @Override
            public void onResponse(@NotNull Call<MarketTitleResponse> call, @NotNull Response<MarketTitleResponse> response) {
                if (response.code() == 200 && response.body() != null) activeMarket.setValue(response.body().getMarket());
            }

            @Override
            public void onFailure(@NotNull Call<MarketTitleResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                activeMarket.setValue("");
            }
        });

        return activeMarket;
    }

    public void setActiveMarket(int position) {
        this.activeMarket.setValue(fullListMarkets[position]);
        this.loadAdverts(fullListMarkets[position]);
    }

    public String[] getFullListMarkets() {
        return this.fullListMarkets;
    }

    public int getActiveMarketPosition() {
        for (int i = 0; i < fullListMarkets.length; i++) {
            if (fullListMarkets[i].equals(activeMarket.getValue())) return i;
        }
        return -1;
    }
}
