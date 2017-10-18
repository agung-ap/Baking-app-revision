package id.developer.agungaprian.bakingapprevisi2.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import id.developer.agungaprian.bakingapprevisi2.R;
import id.developer.agungaprian.bakingapprevisi2.model.Recipes;
import id.developer.agungaprian.bakingapprevisi2.model.Steps;
import id.developer.agungaprian.bakingapprevisi2.util.RecipeDetailItemClickListener;

/**
 * Created by agungaprian on 16/10/17.
 */

public class ListDetailStepRecipeFragment extends Fragment {
    ArrayList<Recipes> recipes;
    String recipeName;

    private RecipeDetailItemClickListener itemClickListener;

    public ListDetailStepRecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recipes = new ArrayList<>();

        if (savedInstanceState != null){
            recipes = savedInstanceState.getParcelableArrayList("selected_recipe");
        }else {
            recipes = getArguments().getParcelableArrayList("selected_recipe");
        }

        View rootView = inflater.inflate(R.layout.fragment_list_step_recipe_detail, container, false);
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
    }
}
