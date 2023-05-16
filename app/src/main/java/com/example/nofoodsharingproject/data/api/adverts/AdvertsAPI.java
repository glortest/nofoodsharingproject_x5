package com.example.nofoodsharingproject.data.api.adverts;

import com.example.nofoodsharingproject.data.api.adverts.dto.RequestDoneAdvert;
import com.example.nofoodsharingproject.data.api.adverts.dto.RequestTakingProduct;
import com.example.nofoodsharingproject.data.api.adverts.dto.ResponseActiveAdverts;
import com.example.nofoodsharingproject.data.api.adverts.dto.ResponseDeleteAdvert;
import com.example.nofoodsharingproject.data.api.adverts.dto.ResponseHistoryAdverts;
import com.example.nofoodsharingproject.models.Advertisement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdvertsAPI {
    @GET("/advertisements/get_active/{market}")
    Call<ResponseActiveAdverts> getListAdvertisements(@Path("market") String market);
    @POST("/advertisements/create")
    Call<Advertisement> createAdvert(@Body Advertisement advert);

    @PUT("/advertisements/getting_product")
    Call<RequestDoneAdvert> makeDoneAdvert (@Body RequestDoneAdvert req);

    @GET("/advertisements/get_own_item/{authorID}")
    Call<Advertisement> getOwnAdvert(@Path("authorID") String authorID);

    @GET("/advertisements/get_item_by_id/{advertID}")
    Call<Advertisement> getAdvertByID(@Path("advertID") String advertID);

    @DELETE("/advertisements/done/{advertID}")
    Call<ResponseDeleteAdvert> deleteAdvert(@Path("advertID") String advertID);

    @PUT("/advertisements/finish_getting_product")
    Call<Advertisement> takingProducts(@Body RequestTakingProduct requestTakingProduct);

    @GET("/advertisements/find_setter_advertisements/{userID}")
    Call<ResponseHistoryAdverts> findSetterAdvertisements(@Path("userID") String userID);
}
