package com.buyhelp.nofoodsharingproject.presentation.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.buyhelp.nofoodsharingproject.R;
import com.buyhelp.nofoodsharingproject.data.api.adverts.dto.ResponseDeleteAdvert;
import com.buyhelp.nofoodsharingproject.data.api.adverts.AdvertsRepository;
import com.buyhelp.nofoodsharingproject.data.models.Advertisement;
import com.buyhelp.nofoodsharingproject.data.models.Getter;
import com.buyhelp.nofoodsharingproject.domain.utils.DefineUser;

import org.jetbrains.annotations.NotNull;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Не используется !
public class AdvertisementExpires extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        getAdvert(context);
    }

    private String getUserID(Context ctx) {
        DefineUser<Getter> defineUser = new DefineUser<>(ctx);

        return defineUser.getUser().getX5_Id();
    }

    private void getAdvert(Context ctx) {
        AdvertsRepository advertsRepository = new AdvertsRepository();
        advertsRepository.getOwnAdvert(ctx, getUserID(ctx)).enqueue(new Callback<Advertisement>() {
            @Override
            public void onResponse(@NotNull Call<Advertisement> call, @NotNull Response<Advertisement> response) {
                if (response.body() != null && response.isSuccessful()) {
                    deleteAdvert(ctx, response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Advertisement> call, @NotNull Throwable t) {
                Log.e("err", ctx.getString(R.string.unvisinle_error));
                t.printStackTrace();
            }
        });
    }

    private void deleteAdvert(Context context, Advertisement advert) {
        AdvertsRepository advertsRepository = new AdvertsRepository();
        if (advert != null) advertsRepository.deleteAdvert(context, advert.getAdvertsID()).enqueue(new Callback<ResponseDeleteAdvert>() {
            @Override
            public void onResponse(@NotNull Call<ResponseDeleteAdvert> call, @NotNull Response<ResponseDeleteAdvert> response) {
                if (!response.isSuccessful()) Log.e("err", context.getString(R.string.unvisinle_error));
            }

            @Override
            public void onFailure(@NotNull Call<ResponseDeleteAdvert> call, @NotNull Throwable t) {
                Log.e("err", context.getString(R.string.unvisinle_error));
                t.printStackTrace();
            }
        });
    }
}
