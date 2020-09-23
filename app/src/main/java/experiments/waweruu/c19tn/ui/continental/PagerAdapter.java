package experiments.waweruu.c19tn.ui.continental;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import experiments.waweruu.c19tn.util.Util;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numOfTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new SingleContinentFragment().newInstance(Util.AFRICA_REGION_CODE);
            case 1: return new SingleContinentFragment().newInstance(Util.ASIA_REGION_CODE);
            case 2: return new SingleContinentFragment().newInstance(Util.EUROPE_REGION_CODE);
            case 3: return new SingleContinentFragment().newInstance(Util.NORTH_AMERICA_REGION_CODE);
            case 4: return new SingleContinentFragment().newInstance(Util.OCEANIA_REGION_CODE);
            case 5: return new SingleContinentFragment().newInstance(Util.SOUTH_AMERICA_REGION_CODE);
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
