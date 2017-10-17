package id.developer.agungaprian.bakingapprevisi2.network;

import java.util.ArrayList;

import id.developer.agungaprian.bakingapprevisi2.model.Recipes;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by agungaprian on 27/09/17.
 */

public interface RetrofitInterface  {
    @GET("baking.json")
    Call<ArrayList<Recipes>> getRecipeData();
}
