package id.developer.agungaprian.bakingapprevisi2.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import id.developer.agungaprian.bakingapprevisi2.R;
import id.developer.agungaprian.bakingapprevisi2.model.Recipes;

import static id.developer.agungaprian.bakingapprevisi2.widget.RecipeWidgetProvider.recipeList;

/**
 * Created by agungaprian on 20/10/17.
 */

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
        );
        return (new ListProvider(this.getApplicationContext(), intent));
    }

    public class ListProvider implements RemoteViewsService.RemoteViewsFactory{
        private ArrayList<String> recipes = new ArrayList();
        private Context context = null;
        private int appWidgetId;

        public ListProvider(Context context, Intent intent) {
            this.context = context;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            recipes = recipeList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return recipes.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

            views.setTextViewText(R.id.appwidget_text, recipes.get(position));

            Intent fillInIntent = new Intent();
            //fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.appwidget_text, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
