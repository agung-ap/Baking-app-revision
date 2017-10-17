package id.developer.agungaprian.bakingapprevisi2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


import id.developer.agungaprian.bakingapprevisi2.R;
import id.developer.agungaprian.bakingapprevisi2.model.Recipes;
import id.developer.agungaprian.bakingapprevisi2.model.Steps;
import id.developer.agungaprian.bakingapprevisi2.util.RecipeDetailItemClickListener;


/**
 * Created by agungaprian on 30/09/17.
 */

public class RecipeStepCardAdapter extends RecyclerView.Adapter<RecipeStepCardAdapter.ViewHolder> {
    final private RecipeDetailItemClickListener detailItemClickListener;
    String recipeName;
    List<Steps> data;
    Context context;

    public RecipeStepCardAdapter(RecipeDetailItemClickListener detailItemClickListener) {
        this.detailItemClickListener = detailItemClickListener;
    }

    public void masterRecipeData (List<Recipes> data, Context context){
        this.data = data.get(0).getSteps();
        recipeName = data.get(0).getName();
        this.context = context;
        notifyDataSetChanged();
    }


    @Override
    public RecipeStepCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_step,parent,false);

        return new RecipeStepCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepCardAdapter.ViewHolder holder, int position) {
        String id = String.valueOf(data.get(position).getId());

        holder.steps.setText(id);
        holder.shortDescription.setText(data.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size():0 ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView steps, shortDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            steps = (TextView)itemView.findViewById(R.id.id_list_step);
            shortDescription = (TextView)itemView.findViewById(R.id.id_title_step);
        }

        @Override
        public void onClick(View v) {
            detailItemClickListener.itemClickListener(data, getAdapterPosition(), recipeName);
        }
    }
}
