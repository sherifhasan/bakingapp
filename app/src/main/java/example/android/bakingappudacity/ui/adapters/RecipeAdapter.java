package example.android.bakingappudacity.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Recipe;

/**
 * Created by sheri on 11/10/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {
    Listener mListener;

    private List<Recipe> mRecipes;

    public RecipeAdapter() {
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public void updateAdapter(List<Recipe> recipes) {
        this.mRecipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.setContent(mRecipes != null ? mRecipes.get(position) : null);
        final Recipe recipe = mRecipes.get(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(recipe);
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return mRecipes == null ? 0 : mRecipes.size();
    }

    public static interface Listener {
        void onClick(Recipe recipe);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name_text_view)
        TextView recipeNameText = null;
        @BindView(R.id.recipe_card_view)
        CardView cardView;
        private Recipe mRecipe;

        MyViewHolder(View convertView) {
            super(convertView);

            ButterKnife.bind(this, convertView);
        }

        void setContent(Recipe recipe) {
            mRecipe = recipe;
            recipeNameText.setText(mRecipe.getRecipeName());
        }
    }
}
