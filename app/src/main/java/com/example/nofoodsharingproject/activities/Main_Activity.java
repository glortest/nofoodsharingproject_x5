package com.example.nofoodsharingproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.example.nofoodsharingproject.data.api.auth.dto.CheckAuthI;
import com.example.nofoodsharingproject.data.api.auth.AuthRepository;
import com.example.nofoodsharingproject.databinding.ActivityMainBinding;
import com.example.nofoodsharingproject.utils.DefineUser;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Activity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DefineUser defineUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        defineUser = new DefineUser(this);
        String res = defineUser.isGetter();
        if (res != null) {
            if (res.equals("setter")) authSetter();
            else if (res.equals("getter")) authGetter();
            else redirectToAuth();
        } else redirectToAuth();
    }

    public void authSetter() {
        AuthRepository.checkAuthSetter(defineUser.getToken()).enqueue(new Callback<CheckAuthI>() {
            @Override
            public void onResponse(@NotNull Call<CheckAuthI> call, @NotNull Response<CheckAuthI> response) {
                if (response.body() != null && !response.body().getIsAuth()) redirectToAuth();
                else {
                    Intent intentSetter = new Intent(getApplicationContext(), Setter_Activity.class);
                    startActivity(intentSetter);
                    finish();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CheckAuthI> call, @NotNull Throwable t) {
                redirectToAuth();
            }
        });
    }

    public void authGetter() {
        AuthRepository.checkAuthGetter(defineUser.getToken()).enqueue(new Callback<CheckAuthI>() {
            @Override
            public void onResponse(@NotNull Call<CheckAuthI> call, @NotNull Response<CheckAuthI> response) {
                if (response.body() == null) redirectToAuth();
                else if (response.body().getIsAuth()) {
                    Intent intentGetter = new Intent(getApplicationContext(), Getter_Activity.class);
                    startActivity(intentGetter);
                    finish();
                } else redirectToAuth();
            }

            @Override
            public void onFailure(@NotNull Call<CheckAuthI> call, @NotNull Throwable t) {
                redirectToAuth();
            }
        });
    }

    public void redirectToAuth() {
        Intent intentAuth = new Intent(getApplicationContext(), MainAuth_Activity.class);
        startActivity(intentAuth);
        finish();
    }
}