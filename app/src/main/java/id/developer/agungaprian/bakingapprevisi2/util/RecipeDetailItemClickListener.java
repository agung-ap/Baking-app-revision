package id.developer.agungaprian.bakingapprevisi2.util;

import java.util.List;

import id.developer.agungaprian.bakingapprevisi2.model.Steps;

/**
 * Created by agungaprian on 16/10/17.
 */

public interface RecipeDetailItemClickListener {
    void itemClickListener(List<Steps> stepOut, int itemPosition, String recipeName);
}
