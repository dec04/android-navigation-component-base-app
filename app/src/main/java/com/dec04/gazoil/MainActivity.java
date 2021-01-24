package com.dec04.gazoil;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.dec04.gazoil.utils.Utility.DEBUG_TAG;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar topAppBar;
    BottomNavigationView bottomNavigation;
    DrawerLayout navigationDrawer;
    NavigationView navView;
    NavController navController;
    AppBarConfiguration appTopBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // region Variables
        navigationDrawer = findViewById(R.id.navigation_drawer__drawer_layout);
        navView = findViewById(R.id.drawer_navigation_view);
        topAppBar = findViewById(R.id.top_app_bar__material_toolbar);
        bottomNavigation = findViewById(R.id.app_bottom_navigation_view);
        // endregion

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host__fragment);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        appTopBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(navigationDrawer)
                .build();

        // region Important for action bar menu
        setSupportActionBar(topAppBar);
        NavigationUI.setupActionBarWithNavController(this, navController, appTopBarConfiguration);
        // endregion

        NavigationUI.setupWithNavController(topAppBar, navController, appTopBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(bottomNavigation, navController);

        navController.addOnDestinationChangedListener((navController, destination, arguments) -> {
            if (destination.getId() == R.id.loginFragment) {
                // todo: set unselect menu items
            }
        });

        Menu menu = navView.getMenu();
        setNavigationDrawerHeader();
        setSocialLinks(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        Log.d(DEBUG_TAG, "Menu click");

        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(DEBUG_TAG, "Navigate Up");

        return NavigationUI.navigateUp(navController, appTopBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Important for action bar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    private void setNavigationDrawerHeader() {
        View header = navView.getHeaderView(0);
        Button logInButton = header.findViewById(R.id.navigation_drawer__login_button);
        logInButton.setOnClickListener(v -> {
            navController.navigate(R.id.loginFragment);
            navigationDrawer.close();
        });
    }

    private void setSocialLinks(Menu menu) {
        ImageView vkImageView = menu
                .findItem(R.id.social_buttons__container__menu_item)
                .getActionView()
                .findViewById(R.id.vk__social_button__image_view);

        ImageView instagramImageView = menu
                .findItem(R.id.social_buttons__container__menu_item)
                .getActionView()
                .findViewById(R.id.instagram__social_button__image_view);

        ImageView facebookImageView = menu
                .findItem(R.id.social_buttons__container__menu_item)
                .getActionView()
                .findViewById(R.id.facebook__social_button__image_view);

        ImageView okImageView = menu
                .findItem(R.id.social_buttons__container__menu_item)
                .getActionView()
                .findViewById(R.id.ok__social_button__image_view);

        vkImageView.setOnClickListener(v -> goToUrl(getString(R.string.vk_url)));
        instagramImageView.setOnClickListener(v -> goToUrl(getString(R.string.instagram_url)));
        facebookImageView.setOnClickListener(v -> goToUrl(getString(R.string.facebook_url)));
        okImageView.setOnClickListener(v -> goToUrl(getString(R.string.ok_url)));

        // Get linkable navigation drawer bottom text view
        TextView byDec04 = findViewById(R.id.by_dec04);
        byDec04.setText(String.format("By Dec04, version %s", getAppVersion()));
        byDec04.setClickable(true);
        byDec04.setMovementMethod(LinkMovementMethod.getInstance());
        Pattern p = Pattern.compile("dec04", Pattern.CASE_INSENSITIVE);
        Linkify.addLinks(byDec04, p, "https://github.com/");

    }

    private String getAppVersion() {
        String version = "";

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    private void goToUrl(String url) {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browse);
    }
}