package example.android.bakingappudacity.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Ingredient;

/**
 * Created by sheri on 11/14/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {

    private List<Ingredient> mIngredients;
    Context context;

    public IngredientAdapter() {
    }

    public void updateAdapter(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.ingredient_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.MyViewHolder holder, int position) {
        holder.setContent(mIngredients != null ? mIngredients.get(position) : null);
    }

    @Override
    public int getItemCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ingredient_quantity)
        TextView quantityTextView = null;
        @BindView(R.id.tv_ingredient_measure)
        TextView measureTextView = null;
        @BindView(R.id.tv_ingredient_ingredient)
        TextView ingredientTextView = null;

        private Ingredient mIngredient;

        MyViewHolder(View convertView) {
            super(convertView);

            ButterKnife.bind(this, convertView);

        }

        void setContent(Ingredient ingredient) {
            mIngredient = ingredient;
            if (mIngredient.getQuantity() != null) {

                String quantity = context.getString(R.string.quantity);
                quantityTextView.setText(quantity + mIngredient.getQuantity());
            }
            if (mIngredient.getMeasure() != null) {
                String measure = context.getString(R.string.measure);
                measureTextView.setText(measure + mIngredient.getMeasure());
            }
            if (mIngredient.getIngredient() != null) {
                String ing = context.getString(R.string.ingredient);
                ingredientTextView.setText(ing + mIngredient.getIngredient());
            }
        }
    }
}