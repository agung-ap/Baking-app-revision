package id.developer.agungaprian.bakingapprevisi2.ui;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import id.developer.agungaprian.bakingapprevisi2.R;
import id.developer.agungaprian.bakingapprevisi2.adapter.RecipeCardAdapter;
import id.developer.agungaprian.bakingapprevisi2.idlingResource.SimpleIdlingResource;
import id.developer.agungaprian.bakingapprevisi2.model.Recipes;
import id.developer.agungaprian.bakingapprevisi2.network.RetrofitBuilder;
import id.developer.agungaprian.bakingapprevisi2.network.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by agungaprian on 27/09/17.
 */

public class ListRecipeFragment extends Fragment{
    private RecyclerView recyclerView;
    private RecipeCardAdapter cardAdapter;
    ArrayList<Recipes> recipeList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_recipe, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerview_list_recipe);
        cardAdapter = new RecipeCardAdapter((ListRecipeActivity)getActivity());
        recyclerView.setAdapter(cardAdapter);

        layoutManager();

        return rootView;
    }
    //get data from json
    public void getRequestData(){
        RetrofitInterface iRecipe = RetrofitBuilder.retrieveData();
        Call<ArrayList<Recipes>> recipe = iRecipe.getRecipeData();

        SimpleIdlingResource idlingResource = (SimpleIdlingResource)((ListRecipeActivity)getActivity()).getIdlingResource();


        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        recipe.enqueue(new Callback<ArrayList<Recipes>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipes>> call, Response<ArrayList<Recipes>> response) {
                recipeList = response.body();

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("recipe_list", recipeList);

                cardAdapter.recipeData(recipeList, getContext());
            }

            @Override
            public void onFailure(Call<ArrayList<Recipes>> call, Throwable t) {
                Log.v("http fail: ", t.getMessage());
                Toast.makeText(getContext(), "koneksi gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void layoutManager(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        getRequestData();
    }


}
