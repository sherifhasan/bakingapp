package example.android.bakingappudacity.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Recipe;
import example.android.bakingappudacity.presenter.RecipePresenter;
import example.android.bakingappudacity.ui.activities.RecipeDetailsActivity;
import example.android.bakingappudacity.ui.adapters.RecipeAdapter;


public class RecipesFragment extends Fragment {

    private static final String RECIPES_LIST = "recipes_list";
    RecipeAdapter mRecipeAdapter;
    GridLayoutManager mLayoutManager;
    @BindView(R.id.recipe_rv_view)
    RecyclerView mRecyclerView;
    @BindInt(R.integer.spanCount)
    int spanCount;
    List<Recipe> mRecipes;
    RecipePresenter mPresenter;


    public RecipesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, rootView);


        mRecipeAdapter = new RecipeAdapter();
        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecipeAdapter);
        mRecipeAdapter.setListener(new RecipeAdapter.Listener() {
            @Override
            public void onClick(Recipe recipe, int position) {
                RecipeDetailsActivity.startActivity(getActivity(), recipe, position);
            }
        });

        if (savedInstanceState != null) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES_LIST);
            mRecipeAdapter.updateAdapter(mRecipes);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter = new RecipePresenter(getActivity());
        mPresenter.onTakeView(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onTakeView(null);
        if (!getActivity().isChangingConfigurations())
            mPresenter = null;
    }

    public void onRecipesNext(List<Recipe> recipes) {
        mRecipes = recipes;
        mRecipeAdapter.updateAdapter(mRecipes);

    }

    public void onRecipesError(Throwable throwable) {
        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPES_LIST, (ArrayList<Recipe>) mRecipes);
    }
}