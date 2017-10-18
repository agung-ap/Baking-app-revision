package id.developer.agungaprian.bakingapprevisi2.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import id.developer.agungaprian.bakingapprevisi2.R;
import id.developer.agungaprian.bakingapprevisi2.model.Recipes;
import id.developer.agungaprian.bakingapprevisi2.model.Steps;
import id.developer.agungaprian.bakingapprevisi2.util.RecipeDetailItemClickListener;

/**
 * Created by agungaprian on 30/09/17.
 */

public class ListDetailRecipeActivity extends AppCompatActivity implements
        RecipeDetailItemClickListener{
    private ArrayList<Recipes> recipes;
    ActionBar actionBar;
    String recipesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail_recipe);

        if (savedInstanceState == null){
            Bundle getBundle = getIntent().getExtras();

            recipes = new ArrayList<>();
            recipes = getBundle.getParcelableArrayList("selected_recipe");
            recipesName = recipes.get(0).getName();

            ListDetailRecipeFragment fragment = new ListDetailRecipeFragment();
            fragment.setArguments(getBundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.list_recipe_detail_fragment, fragment)
                    .commit();
        }else {
            recipesName = savedInstanceState.getString("title");
        }

        //create home button in actionbar
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(recipesName);
    }

    //go to backstack when home button pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title",recipesName);
    }

    @Override
    public void itemClickListener(List<Steps> stepOut, int itemPosition, String recipeName) {
        ListDetailStepRecipeFragment fragment = new ListDetailStepRecipeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        actionBar.setTitle(recipesName);

        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList(getString(R.string.selected_recipe),(ArrayList<Steps>) stepOut);
        stepBundle.putInt("selected_index",itemPosition);
        stepBundle.putString("title",recipesName);
        fragment.setArguments(stepBundle);

        fragmentManager.beginTransaction()
                .replace(R.id.list_recipe_detail_fragment, fragment)
                .addToBackStack("recipe_ingredient_detail")
                .commit();

        /*if (findViewById(R.id.recipe_linear_layout).getTag()!=null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet-land")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container2, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();

        }
        else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        }*/
    }
}
