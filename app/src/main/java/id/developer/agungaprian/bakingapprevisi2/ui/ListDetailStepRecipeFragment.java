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
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private BandwidthMeter bandwidthMeter;
    private ArrayList<Steps> steps = new ArrayList<>();
    private int selectedIndex;
    private Handler mainHandler;
    ArrayList<Recipes> recipe;
    String recipeName;

    private RecipeDetailItemClickListener itemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Steps> allSteps, int Index, String recipeName);
    }

    public ListDetailStepRecipeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_step_recipe_detail, container, false);

        TextView textView;
        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        itemClickListener =(ListDetailRecipeActivity)getActivity();

        recipe = new ArrayList<>();

        if (savedInstanceState != null){
            steps = savedInstanceState.getParcelableArrayList("selected_recipe");
            selectedIndex = savedInstanceState.getInt("selected_index");
            recipeName = savedInstanceState.getString("Title");

        }else {
            steps = getArguments().getParcelableArrayList("selected_step");
            if (steps != null){
                steps = getArguments().getParcelableArrayList("selected_step");
                selectedIndex = getArguments().getInt("selected_index");
                recipeName = getArguments().getString("Title");
            }else {
                recipe = getArguments().getParcelableArrayList("selected_recipe");
                steps = (ArrayList<Steps>)recipe.get(0).getSteps();
                selectedIndex = 0;
            }
        }


        textView = (TextView)rootView.findViewById(R.id.recipe_step_detail_text);
        textView.setText(steps.get(selectedIndex).getDescription());
        textView.setVisibility(View.VISIBLE);

        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        String videoURL = steps.get(selectedIndex).getVideoURL();

        if (rootView.findViewWithTag("sw600dp-port-recipe_step_detail")!=null) {
            recipeName=((ListDetailRecipeActivity) getActivity()).recipesName;
            ((ListDetailRecipeActivity) getActivity()).getSupportActionBar().setTitle(recipeName);
        }

        String imageUrl=steps.get(selectedIndex).getThumbnailURL();
        if (imageUrl!="") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();

            ImageView thumbImage = (ImageView) rootView.findViewById(R.id.thumbImage);

            Picasso.with(getContext())
                    .load(builtUri)
                    .into(thumbImage);
        }

        if (!videoURL.isEmpty()) {


            initializePlayer(Uri.parse(steps.get(selectedIndex).getVideoURL()));

            if (rootView.findViewWithTag("sw600dp-land-recipe_step_detail")!=null) {
                getActivity().findViewById(R.id.list_recipe_detail_fragment).setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
            }
            else if (isInLandscapeMode(getContext())){
                textView.setVisibility(View.GONE);
            }
        }
        else {
            simpleExoPlayer=null;
            simpleExoPlayerView.setForeground(ContextCompat.getDrawable(getContext(), R.mipmap.ic_launcher));
            simpleExoPlayerView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
        }


        Button mPrevStep = (Button) rootView.findViewById(R.id.previousStep);
        Button mNextstep = (Button) rootView.findViewById(R.id.nextStep);

        mPrevStep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (steps.get(selectedIndex).getId() > 0) {
                    if (simpleExoPlayer!=null){
                        simpleExoPlayer.stop();
                    }
                    itemClickListener.itemClickListener(steps,steps.get(selectedIndex).getId() - 1,recipeName);
                }
                else {
                    Toast.makeText(getActivity(),"You already are in the First step of the recipe", Toast.LENGTH_SHORT).show();

                }
            }});

        mNextstep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                int lastIndex = steps.size()-1;
                if (steps.get(selectedIndex).getId() < steps.get(lastIndex).getId()) {
                    if (simpleExoPlayer!=null){
                        simpleExoPlayer.stop();
                    }
                    itemClickListener.itemClickListener(steps,steps.get(selectedIndex).getId() + 1,recipeName);
                }
                else {
                    Toast.makeText(getContext(),"You already are in the Last step of the recipe", Toast.LENGTH_SHORT).show();

                }
            }});

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (simpleExoPlayer == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);

            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList("selected_recipe",steps);
        currentState.putInt("selected_recipe",selectedIndex);
        currentState.putString("Title",recipeName);
    }

    public boolean isInLandscapeMode( Context context ) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (simpleExoPlayer!=null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (simpleExoPlayer !=null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer =null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (simpleExoPlayer !=null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (simpleExoPlayer !=null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }
}
