package experiments.waweruu.c19tn.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import experiments.waweruu.c19tn.R;
import experiments.waweruu.c19tn.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment implements View.OnClickListener {

    private FragmentAboutBinding binding;
    //private ViewModel viewModel;
    //@Inject public ViewModelFactory factory;

    public AboutFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //viewModel = new ViewModelProvider(this, factory).get(AboutViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.androidRepoLink.setOnClickListener(this);
        binding.backendRepoLink.setOnClickListener(this);
        binding.apiLink.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Uri page = null;
        switch(view.getId()) {
            case R.id.android_repo_link:
                page = Uri.parse(getString(R.string.github_repo));
                break;

            case R.id.backend_repo_link:
                page = Uri.parse(getString(R.string.github_repo_2));
                break;

            case R.id.api_link:
                page = Uri.parse(getString(R.string.api_url));
                break;
        }
        assert page != null;
        Intent intent = new Intent(Intent.ACTION_VIEW, page);
        startActivity(intent);
    }
}
