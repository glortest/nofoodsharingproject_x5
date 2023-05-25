package com.buyhelp.nofoodsharingproject.data.api.getter;

import android.content.Context;

import com.buyhelp.nofoodsharingproject.data.api.getter.dto.RequestChangeToken;
import com.buyhelp.nofoodsharingproject.data.api.getter.dto.RequestGetterEditProfile;
import com.buyhelp.nofoodsharingproject.data.api.notifications.dto.ResponseFCMToken;
import com.buyhelp.nofoodsharingproject.models.Getter;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class GetterRepository {
    public Call<Getter> editProfile(Context ctx, String userID, String login, String phone, String password, String oldPassword) {
        return GetterApiService.getInstance(ctx).editProfile(new RequestGetterEditProfile(userID, login, phone, password, oldPassword));
    }
    public Call<ResponseFCMToken> getFCMtoken(Context ctx, String authorID) {
        return GetterApiService.getInstance(ctx).getFCMtoken(authorID);
    }
    public Call<ResponseBody> changeToken(Context ctx, String userID, String fcmToken) {
        return GetterApiService.getInstance(ctx).changeToken(new RequestChangeToken(userID, fcmToken));
    }
}