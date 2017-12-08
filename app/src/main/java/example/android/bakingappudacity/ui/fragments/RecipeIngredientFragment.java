package example.android.bakingappudacity.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Recipe;
import example.android.bakingappudacity.ui.adapters.IngredientAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeIngredientFragment extends Fragment {

    private static final String ARGUMENT_EXTRA = "Recipe";

    @BindView(R.id.rv_ingredients_view)
    RecyclerView mIngredientRecyclerView;
    IngredientAdapter mIngredientAdapter;
    RecyclerView.LayoutManager mIngredientLayoutManager;
    private Recipe recipe;
    int currentVisiblePosition = 0;

    public RecipeIngredientFragment() {
    }

    public static RecipeIngredientFragment newInstance(Recipe recipe) {
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_EXTRA, recipe);
        RecipeIngredientFragment fragment = new RecipeIngredientFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient, container, false);
        ButterKnife.bind(this, rootView);

        mIngredientAdapter = new IngredientAdapter();
        mIngredientRecyclerView.setNestedScrollingEnabled(false);
        mIngredientRecyclerView.setAdapter(mIngredientAdapter);
        mIngredientLayoutManager = new LinearLayoutManager(getActivity());
        mIngredientRecyclerView.setLayoutManager(mIngredientLayoutManager);
        mIngredientRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (savedInstanceState == null) {
            if (getArguments() != null && getArguments().containsKey(ARGUMENT_EXTRA) && getArguments().getParcelable(ARGUMENT_EXTRA) != null) {
                recipe = getArguments().getParcelable(ARGUMENT_EXTRA);
                if (recipe.getIngredients() != null) {
                    Log.d("Quantity: ", recipe.getIngredients().get(0).getQuantity());
                    mIngredientAdapter.updateAdapter(recipe.getIngredients());
                }
            }
        } else {
            recipe = savedInstanceState.getParcelable(ARGUMENT_EXTRA);
            if (recipe.getIngredients() != null) {
                Log.d("Quantity: from saved ", recipe.getIngredients().get(0).getQuantity());
                mIngredientAdapter.updateAdapter(recipe.getIngredients());
            }
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARGUMENT_EXTRA, recipe);
    }

    @Override
    public void onPause() {
        super.onPause();
        currentVisiblePosition = ((LinearLayoutManager) mIngredientRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        mIngredientRecyclerView.getLayoutManager().scrollToPosition(currentVisiblePosition);
        currentVisiblePosition = 0;
    }
}