package experiments.waweruu.c19tn;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import experiments.waweruu.c19tn.di.AppComponent;
import experiments.waweruu.c19tn.di.DaggerAppComponent;

public class App extends MultiDexApplication {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder().application(this).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
