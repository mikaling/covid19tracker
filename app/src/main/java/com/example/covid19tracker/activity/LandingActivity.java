package com.example.covid19tracker.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.covid19tracker.R;
import com.example.covid19tracker.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {

    ActivityLandingBinding landingBinding;
    int[] arrayOfMenuItems = {R.id.global_fragment, R.id.continental_fragment,
            R.id.local_fragment, R.id.metrics_fragment, R.id.about_fragment};
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            arrayOfMenuItems).build();
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /* Setting up a dark status bar*/
            Window window = this.getWindow();
            //clear FLAG_TRANSLUCENT_STATUS flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //change the color
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }

        landingBinding = ActivityLandingBinding.inflate(getLayoutInflater());
        View view = landingBinding.getRoot();
        setContentView(view);
        setSupportActionBar(landingBinding.materialToolbar);

        /*
        * Using the Navigation component to handle navigation between fragments
        * We have this as our base activity and then set up our navigation controller with
        * the bottom navigation view for our top level destinations and
        * the action bar to show the title of a given fragment and provide an Up clickable
        * that allows one to move back to the top level */
        navController = Navigation.findNavController(this, R.id.navigation_host_fragment);
        NavigationUI.setupWithNavController(landingBinding.bottomNavigation, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
