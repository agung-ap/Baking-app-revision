package id.developer.agungaprian.bakingapprevisi2.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

import id.developer.agungaprian.bakingapprevisi2.R;
import id.developer.agungaprian.bakingapprevisi2.model.Recipes;
import id.developer.agungaprian.bakingapprevisi2.model.Steps;
import id.developer.agungaprian.bakingapprevisi2.util.ComponentListener;
import id.developer.agungaprian.bakingapprevisi2.util.MediaPlayer;
import id.developer.agungaprian.bakingapprevisi2.util.RecipeDetailItemClickListener;

/**
 * Created by agungaprian on 16/10/17.
 */

public class ListDetailStepRecipeFragment extends Fragment {
    private ArrayList<Recipes> recipes;
    private ArrayList<Steps> steps;
    private String videoLink;

    private MediaPlayer mediaPlayer;
    private SimpleExoPlayerView playerView;
    private ComponentListener componentListener;

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
            recipes = savedInstanceState.getParcelableArrayList("selected_recipe");
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
                steps = (ArrayList<Steps>) recipes.get(0).getSteps();
                selectedIndex = 0;
            }
        }

        View rootView = inflater.inflate(R.layout.fragment_list_step_recipe_detail, container, false);
        componentListener = new ComponentListener();
        playerView = (SimpleExoPlayerView)rootView.findViewById(R.id.player_view);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        videoLink = steps.get(selectedIndex).getVideoURL();

        mediaPlayer = new MediaPlayer(playerView, componentListener, videoLink, getContext());

        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
    }
}
