package experiments.waweruu.c19tn.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import experiments.waweruu.c19tn.viewmodel.ContinentalViewModel;
import experiments.waweruu.c19tn.viewmodel.GlobalViewModel;
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
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factoryViewModel);
}
