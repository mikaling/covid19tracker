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

import com.mukesh.MarkdownView;

import experiments.waweruu.c19tn.R;
import experiments.waweruu.c19tn.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment{

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

        MarkdownView markdownView = (MarkdownView) view.findViewById(R.id.markdown_view);

        markdownView.loadMarkdownFromAssets("about.md"); //Loads the markdown file from the assets folder
    }
}
