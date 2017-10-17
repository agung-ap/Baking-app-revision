package id.developer.agungaprian.bakingapprevisi2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.developer.agungaprian.bakingapprevisi2.R;
import id.developer.agungaprian.bakingapprevisi2.model.Recipes;
import id.developer.agungaprian.bakingapprevisi2.util.RecipeItemClickListener;

/**
 * Created by agungaprian on 27/09/17.
 */

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.MyItemHolder> {
    Context context;
    ArrayList<Recipes> data = new ArrayList<>();
    final private RecipeItemClickListener listener;

    public RecipeCardAdapter(RecipeItemClickListener listener) {
        this.listener = listener;
    }

    public void recipeData(ArrayList<Recipes> data, Context context){
        this.context = context;
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecipeCardAdapter.MyItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_recipe_card,parent,false);
        return new MyItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeCardAdapter.MyItemHolder holder, int position) {
        holder.titleRecipe.setText(data.get(position).getName());
        holder.numberOfServing.setText(data.get(position).getServings());

        Picasso.with(context)
                .load(R.mipmap.ic_launcher)
                .into(holder.imageRecipe);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size():0 ;
    }

    public class MyItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageRecipe;
        TextView titleRecipe;
        TextView numberOfServing;

        public MyItemHolder(View itemView) {
            super(itemView);

            imageRecipe = (ImageView)itemView.findViewById(R.id.image_recipe);
            titleRecipe = (TextView)itemView.findViewById(R.id.title_recipe);
            numberOfServing = (TextView)itemView.findViewById(R.id.serving_recipe);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position =  getAdapterPosition();
            listener.itemClickListener(data.get(position));
        }
    }
}