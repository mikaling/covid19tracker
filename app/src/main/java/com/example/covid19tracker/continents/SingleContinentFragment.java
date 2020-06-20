package com.example.covid19tracker.continents;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.example.covid19tracker.R;
import com.example.covid19tracker.global.WorldFragment;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleContinentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleContinentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "SingleContinentFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private WebView webView;
    public SingleContinentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingleContinentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleContinentFragment newInstance(String param1, String param2) {
        SingleContinentFragment fragment = new SingleContinentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_continent, container, false);
        webView = view.findViewById(R.id.continent_webview);
        return view;
    }

    public class WebAppInterface {
        Context mContext;

        public WebAppInterface (Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void getArrayGeoChartData() {

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadWebView();
    }

    public void loadWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.addJavascriptInterface(new WebAppInterface(getContext()), "Android");
//        webView.setInitialScale(1);

        webView.loadUrl("file:///android_asset/continents.html");
    }
}
