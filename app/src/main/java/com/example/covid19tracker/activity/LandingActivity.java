package com.example.covid19tracker.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.covid19tracker.R;
import com.example.covid19tracker.databinding.ActivityLandingBinding;
import com.example.covid19tracker.global.WorldFragment;
import com.example.covid19tracker.local.LocalFragment;
import com.example.covid19tracker.regional.RegionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LandingActivity extends AppCompatActivity {

    ActivityLandingBinding landingBinding;

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

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment globalFragment = new WorldFragment();
        final Fragment regionalFragment = new RegionFragment();
        final Fragment localFragment = new LocalFragment();

        landingBinding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.global_page:
                default:
                    fragment = globalFragment;
                    break;

                case R.id.regional_page:
                    fragment = regionalFragment;
                    break;

                case R.id.local_page:
                    fragment = localFragment;
                    break;
            }
            fragmentManager.beginTransaction().replace(R.id.frame_content
                    , fragment).commit();
            return true;
        });

        landingBinding.bottomNavigation.setSelectedItemId(R.id.global_page);
    }
}
