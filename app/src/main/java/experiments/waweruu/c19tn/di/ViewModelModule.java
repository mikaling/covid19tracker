package experiments.waweruu.c19tn.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import experiments.waweruu.c19tn.viewmodel.ComparisonViewModel;
import experiments.waweruu.c19tn.viewmodel.ContinentalViewModel;
import experiments.waweruu.c19tn.viewmodel.GlobalViewModel;
import experiments.waweruu.c19tn.viewmodel.LocalViewModel;
import experiments.waweruu.c19tn.viewmodel.MetricsViewModel;
import experiments.waweruu.c19tn.viewmodel.TrendsViewModel;
import experiments.waweruu.c19tn.viewmodel.ViewModelFactory;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContinentalViewModel.class)
    abstract ViewModel bindContinentalViewModel(ContinentalViewModel continentalViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GlobalViewModel.class)
    abstract ViewModel bindGlobalViewModel(GlobalViewModel globalViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LocalViewModel.class)
    abstract ViewModel bindLocalViewModel(LocalViewModel localViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ComparisonViewModel.class)
    abstract ViewModel bindComparisonViewModel(ComparisonViewModel comparisonViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MetricsViewModel.class)
    abstract ViewModel bindMetricsViewModel(MetricsViewModel metricsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TrendsViewModel.class)
    abstract ViewModel bindTrendsViewModel(TrendsViewModel trendsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factoryViewModel);
}
