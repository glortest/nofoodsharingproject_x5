package com.example.nofoodsharingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.nofoodsharingproject.activities.GetterAC;
import com.example.nofoodsharingproject.activities.MainAuthAC;
import com.yandex.mapkit.MapKitFactory;

public class MainActivity extends AppCompatActivity {
    //    private ActivityMainBinding binding;
    NavController navController = null;
    boolean isAuth = false;
    // костыль
    boolean isGetter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // костыль !
        isAuth = getIntent().getBooleanExtra("isAuth", false);

        // server request ...
//        if (!isAuth) { потом заменить
        if (isAuth) {
            Intent intentAuth = new Intent(getApplicationContext(), MainAuthAC.class);
            startActivity(intentAuth);
            finish();
        }
        else if (isGetter) {
            Intent intentGetter = new Intent(getApplicationContext(), GetterAC.class);
            startActivity(intentGetter);
            finish();
        }

    }

}