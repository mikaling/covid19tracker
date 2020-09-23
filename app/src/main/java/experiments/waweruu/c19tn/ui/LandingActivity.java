package experiments.waweruu.c19tn.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import experiments.waweruu.c19tn.R;
import experiments.waweruu.c19tn.databinding.ActivityLandingBinding;

public class LandingActivity extends AppCompatActivity {

    ActivityLandingBinding landingBinding;
    int[] arrayOfMenuItems = {R.id.global_fragment, R.id.continental_fragment,
            R.id.local_fragment, R.id.metrics_fragment, R.id.about_fragment};
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
            arrayOfMenuItems).build();
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        landingBinding = ActivityLandingBinding.inflate(getLayoutInflater());
        View view = landingBinding.getRoot();
        setContentView(view);

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

        setSupportActionBar(landingBinding.materialToolbar);

        navController = Navigation.findNavController(this, R.id.navigation_host_fragment);
        NavigationUI.setupWithNavController(landingBinding.bottomNavigation, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}