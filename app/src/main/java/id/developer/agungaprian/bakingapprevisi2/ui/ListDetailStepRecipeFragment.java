package id.developer.agungaprian.bakingapprevisi2.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import id.developer.agungaprian.bakingapprevisi2.R;
import id.developer.agungaprian.bakingapprevisi2.model.Recipes;
import id.developer.agungaprian.bakingapprevisi2.model.Steps;
import id.developer.agungaprian.bakingapprevisi2.util.ComponentListener;
import id.developer.agungaprian.bakingapprevisi2.util.RecipeDetailItemClickListener;

/**
 * Created by agungaprian on 16/10/17.
 */

public class ListDetailStepRecipeFragment extends Fragment {
    private ArrayList<Recipes> recipes;
    private List<Steps> steps;
    private String videoLink;
    private String imageLink;

    private TextView recipeDescription, noVideoAlert;
    private ImageView imageThumbnail;
    // bandwidth meter to measure and estimate bandwidth
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    private SimpleExoPlayer player;
    private SimpleExoPlayerView playerView;
    private ComponentListener componentListener;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    private int selectedIndex;
    private String recipeName;
    private RecipeDetailItemClickListener itemClickListener;

    public ListDetailStepRecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recipes = new ArrayList<>();
        steps = new ArrayList<>();

        if (savedInstanceState != null){
            recipes = savedInstanceState.getParcelableArrayList(getString(R.string.selected_recipe));
            selectedIndex = savedInstanceState.getInt("selected_index");
            recipeName = savedInstanceState.getString("title");
        }else {
            steps = getArguments().getParcelableArrayList(getString(R.string.selected_recipe));
            if (steps!=null) {
                steps =getArguments().getParcelableArrayList(getString(R.string.selected_recipe));
                selectedIndex=getArguments().getInt("selected_index");
                recipeName=getArguments().getString("title");
            }
            else {
                recipes =getArguments().getParcelableArrayList(getString(R.string.selected_recipe));
                //casting List to ArrayList
                steps = recipes.get(0).getSteps();
                selectedIndex = 0;
            }
        }

        View rootView = inflater.inflate(R.layout.fragment_list_step_recipe_detail, container, false);

        imageThumbnail = (ImageView)rootView.findViewById(R.id.thumb_image);
        noVideoAlert = (TextView)rootView.findViewById(R.id.no_video);
        //add textview id for write description from json
        recipeDescription = (TextView)rootView.findViewById(R.id.recipe_step_detail_text);
        recipeDescription.setText(steps.get(selectedIndex).getDescription());


        componentListener = new ComponentListener();
        playerView = (SimpleExoPlayerView)rootView.findViewById(R.id.player_view);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        //get link video from json
        videoLink = steps.get(selectedIndex).getVideoURL();

        return rootView;
    }

    public void initializePlayer() {
        if (player == null) {
            // a factory to create an AdaptiveVideoTrackSelection
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            // using a DefaultTrackSelector with an adaptive video selection factory
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory), new DefaultLoadControl());
            player.addListener(componentListener);
            player.setVideoDebugListener(componentListener);
            player.setAudioDebugListener(componentListener);
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        MediaSource mediaSource = buildMediaSource(Uri.parse(videoLink));
        player.prepare(mediaSource, true, false);
    }

    public void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.removeListener(componentListener);
            player.setVideoListener(null);
            player.setVideoDebugListener(null);
            player.setAudioDebugListener(null);
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER);
        DashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(
                dataSourceFactory);
        return new DashMediaSource(uri, dataSourceFactory, dashChunkSourceFactory, null, null);
    }

    public void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (!videoLink.isEmpty()){
                initializePlayer();
            }else {
                playerView.setVisibility(View.GONE);
                imageThumbnail.setVisibility(View.VISIBLE);
                noVideoAlert.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            if (!videoLink.isEmpty()){
                initializePlayer();
            }else {
                playerView.setVisibility(View.GONE);
                imageThumbnail.setVisibility(View.VISIBLE);
                noVideoAlert.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
    }
}
