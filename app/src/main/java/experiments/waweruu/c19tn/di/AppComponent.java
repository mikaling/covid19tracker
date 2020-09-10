package experiments.waweruu.c19tn.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import experiments.waweruu.c19tn.App;
import experiments.waweruu.c19tn.ui.continental.SingleContinentFragment;
import experiments.waweruu.c19tn.ui.global.GlobalMapFragment;
import experiments.waweruu.c19tn.ui.global.GlobalStatisticsFragment;
import experiments.waweruu.c19tn.ui.local.ComparisonFragment;
import experiments.waweruu.c19tn.ui.local.LocalFragment;
import experiments.waweruu.c19tn.ui.local.TrendsFragment;
import experiments.waweruu.c19tn.ui.metrics.MetricsFragment;

@Singleton
@Component(modules = {RemoteModule.class, LocalModule.class, ViewModelModule.class, DaoModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(GlobalStatisticsFragment fragment);

    void inject(GlobalMapFragment fragment);

    void inject(SingleContinentFragment fragment);

    void inject(LocalFragment fragment);

    void inject(ComparisonFragment fragment);

    void inject(MetricsFragment fragment);

    void inject(TrendsFragment fragment);
}
