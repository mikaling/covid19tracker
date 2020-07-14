package com.example.covid19tracker.continents;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    private final String AFRICA_REGION_CODE = "002";
    private final String EUROPE_REGION_CODE = "150";
    private final String ASIA_REGION_CODE = "142";
    private final String NORTH_AMERICA_REGION_CODE = "021";
    private final String SOUTH_AMERICA_REGION_CODE = "005";
    private final String OCEANIA_REGION_CODE = "009";

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.numOfTabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return new SingleContinentFragment().newInstance(AFRICA_REGION_CODE);
            case 1: return new SingleContinentFragment().newInstance(EUROPE_REGION_CODE);
            case 2: return new SingleContinentFragment().newInstance(ASIA_REGION_CODE);
            case 3: return new SingleContinentFragment().newInstance(NORTH_AMERICA_REGION_CODE);
            case 4: return new SingleContinentFragment().newInstance(SOUTH_AMERICA_REGION_CODE);
            case 5: return new SingleContinentFragment().newInstance(OCEANIA_REGION_CODE);
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
