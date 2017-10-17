package id.developer.agungaprian.bakingapprevisi2.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.developer.agungaprian.bakingapprevisi2.R;
import id.developer.agungaprian.bakingapprevisi2.adapter.RecipeStepCardAdapter;
import id.developer.agungaprian.bakingapprevisi2.model.Ingredients;
import id.developer.agungaprian.bakingapprevisi2.model.Recipes;

/**
 * Created by agungaprian on 15/10/17.
 */

public class ListDetailRecipeFragment extends Fragment {
    ArrayList<Recipes> recipes;
    String recipesName;
    RecyclerView recyclerView;
    TextView ingredient , ingredientTitle;
    RecipeStepCardAdapter recipeDetailCardAdapter;

    public ListDetailRecipeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        recipes = new ArrayList<>();

        if (savedInstanceState != null){
            recipes = savedInstanceState.getParcelableArrayList("selected_recipe");
        }else {
            recipes = getArguments().getParcelableArrayList("selected_recipe");
        }

        List<Ingredients> ingredients = recipes.get(0).getIngredients();
        recipesName = recipes.get(0).getName();

        //inflate layout
        View rootView = inflater.inflate(R.layout.fragment_list_detail_recipe, container, false);
        ingredient = (TextView)rootView.findViewById(R.id.recipe_detail_text);
        ingredientTitle = (TextView)rootView.findViewById(R.id.ingredient_titile);

        ingredientTitle.setText(recipesName +" Ingredient");

        for (Ingredients data : ingredients){
            ingredient.append("\u2022 "+ data.getIngredient()+"");
            ingredient.append("\t\t "+data.getQuantity().toString()+"");
            ingredient.append("\t "+ data.getMeasure()+"\n");
        }


        //add recyclerview layout
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerview_list_detail_recipe);
        layoutManager();

        recipeDetailCardAdapter = new RecipeStepCardAdapter((ListDetailRecipeActivity)getActivity());
        recyclerView.setAdapter(recipeDetailCardAdapter);
        recipeDetailCardAdapter.masterRecipeData(recipes, getContext());
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList("selected_recipe", recipes);
        currentState.putString("Title", recipesName);
    }

    public void layoutManager(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
    }
}
