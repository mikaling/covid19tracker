package com.example.covid19tracker.local;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.covid19tracker.R;


public class LocalFragment extends Fragment implements View.OnClickListener
{
    private Button lineChartButton;
    private Button barChartButton;
    public LocalFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_local, container, false);
        lineChartButton = view.findViewById(R.id.trendsLineChart);
        lineChartButton.setOnClickListener(this);
        barChartButton = view.findViewById(R.id.comparisonBarChart);
        barChartButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.trendsLineChart)
        {
            Intent lineChartIntent = new Intent(v.getContext(),LineChartActivity.class);
            startActivity(lineChartIntent);
        }
        if (v.getId() == R.id.comparisonBarChart)
        {
//            Intent barChartIntent = new Intent(v.getContext(),BarChartActivity.class);
//            startActivity(barChartIntent);
//            BarChartFragment barChartFragment = new BarChartFragment();
//            FragmentManager fragmentManager = getFragmentManager();
//            assert fragmentManager != null;
//            fragmentManager.beginTransaction().replace(R.id.local_relative_layout,barChartFragment).commit();
            NavDirections action = LocalFragmentDirections.actionLocalFragmentToBarChartFragment();
            Navigation.findNavController(this.getView()).navigate(action);
        }
    }
}
