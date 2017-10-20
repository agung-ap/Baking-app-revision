package id.developer.agungaprian.bakingapprevisi2.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by agungaprian on 20/10/17.
 */

public class RecipeUpdateService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public RecipeUpdateService() {
        super("RecipeUpdateService");
    }


    public static void startBakingService(Context context, ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent(context, RecipeUpdateService.class);
        intent.putExtra("FROM_ACTIVITY_INGREDIENTS_LIST",fromActivityIngredientsList);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            ArrayList<String> fromActivityIngredientsList = intent.getExtras().getStringArrayList("FROM_ACTIVITY_INGREDIENTS_LIST");
            handleActionUpdateBakingWidgets(fromActivityIngredientsList);
        }
    }

    private void handleActionUpdateBakingWidgets(ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra("FROM_ACTIVITY_INGREDIENTS_LIST",fromActivityIngredientsList);
        sendBroadcast(intent);
    }
}
