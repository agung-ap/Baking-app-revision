package id.developer.agungaprian.bakingapprevisi2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import id.developer.agungaprian.bakingapprevisi2.R;
import id.developer.agungaprian.bakingapprevisi2.model.Recipes;
import id.developer.agungaprian.bakingapprevisi2.util.RecipeItemClickListener;


public class ListRecipeActivity extends AppCompatActivity implements RecipeItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipe);

        if (savedInstanceState == null){
            ListRecipeFragment fragment = new ListRecipeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.list_recipe_framgment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void itemClickListener(Recipes itemPosition) {
        Bundle bundle = new Bundle();
        ArrayList<Recipes> recipes = new ArrayList<>();
        recipes.add(itemPosition);
        bundle.putParcelableArrayList(getString(R.string.selected_recipe), recipes);

        Intent intent = new Intent(this, ListDetailRecipeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
