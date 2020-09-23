package experiments.waweruu.c19tn.ui.continental;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import experiments.waweruu.c19tn.R;
import experiments.waweruu.c19tn.databinding.FragmentContinentalBinding;
import experiments.waweruu.c19tn.util.Util;

public class ContinentalFragment extends Fragment {

    private FragmentContinentalBinding binding;

    public ContinentalFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContinentalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = binding.tabLayout;
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText(Util.COLUMN_AFRICA));
        tabLayout.addTab(tabLayout.newTab().setText(Util.COLUMN_ASIA));
        tabLayout.addTab(tabLayout.newTab().setText(Util.COLUMN_EUROPE));
        tabLayout.addTab(tabLayout.newTab().setText(Util.COLUMN_NORTH_AMERICA));
        tabLayout.addTab(tabLayout.newTab().setText(Util.COLUMN_OCEANIA));
        tabLayout.addTab(tabLayout.newTab().setText(Util.COLUMN_SOUTH_AMERICA));

        PagerAdapter pagerAdapter = new PagerAdapter(getParentFragmentManager(),
                tabLayout.getTabCount());
        binding.viewpager.setAdapter(pagerAdapter);
        binding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
