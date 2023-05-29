package com.buyhelp.nofoodsharingproject.presentation.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.buyhelp.nofoodsharingproject.R;
import com.buyhelp.nofoodsharingproject.databinding.ActivitySetterBinding;
import com.buyhelp.nofoodsharingproject.data.models.Setter;
import com.buyhelp.nofoodsharingproject.presentation.services.LocationTrackingService;
import com.buyhelp.nofoodsharingproject.domain.utils.DateNowChecker;
import com.buyhelp.nofoodsharingproject.domain.utils.DateNowCheckerOld;
import com.buyhelp.nofoodsharingproject.domain.helpers.DefineUser;
import com.buyhelp.nofoodsharingproject.domain.helpers.PermissionHandler;

public class SetterActivity extends AppCompatActivity {
    private NavController navController;
    private ActivitySetterBinding binding;
    private DefineUser<Setter> defineUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetterBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        defineUser = new DefineUser<>(this);

        PermissionHandler.requestCalendarPermissions(this);
        if (defineUser.getIsLocation()) PermissionHandler.requestPermissions(this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_setter_fragment);
        navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(binding.setterNavigation, navController);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateNowChecker dateNowChecker = new DateNowChecker();
            if (dateNowChecker.getHour() >= 10 && dateNowChecker.getHour() < 23) initLocation();
        } else {
            DateNowCheckerOld dateNowCheckerOld = new DateNowCheckerOld();
            if (dateNowCheckerOld.getHour() >= 10 && dateNowCheckerOld.getHour() < 23) initLocation();
        }
    }

    public void setBottomNavigationVisibility(boolean isVisible) {
        if (isVisible) binding.setterNavigation.setVisibility(View.VISIBLE);
        else binding.setterNavigation.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        binding = null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavigationUI.onNavDestinationSelected(item, navController);
        return super.onOptionsItemSelected(item);
    }

    private void initLocation() {
        if (!PermissionHandler.checkPermissions(getApplicationContext())) PermissionHandler.requestPermissions(this);
        else {
            if (defineUser.getIsLocation()) {
                Intent serviceIntent = new Intent(this, LocationTrackingService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent);
                } else startService(serviceIntent);
            }
        }
    }
}
