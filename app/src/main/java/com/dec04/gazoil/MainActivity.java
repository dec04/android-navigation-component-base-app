package com.dec04.gazoil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    DrawerLayout navigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationDrawer = findViewById(R.id.navigation_drawer__drawer_layout);
        materialToolbar = findViewById(R.id.top_app_bar__material_toolbar);
        materialToolbar.setNavigationOnClickListener(v -> {
            // Open navigation drawer
            navigationDrawer.open();
        });

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host__fragment);
        NavController navController = navHostFragment.getNavController();
    }
}