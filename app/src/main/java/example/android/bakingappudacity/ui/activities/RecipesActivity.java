package example.android.bakingappudacity.ui.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.bakingappudacity.IdlingResource.SimpleIdlingResource;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Recipe;
import example.android.bakingappudacity.presenter.RecipePresenter;
import example.android.bakingappudacity.ui.adapters.RecipeAdapter;

public class RecipesActivity extends AppCompatActivity {
    private static final String RECIPES_LIST = "recipes_list";
    RecipeAdapter mRecipeAdapter;
    GridLayoutManager mLayoutManager;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.recipe_rv_view)
    RecyclerView mRecyclerView;
    @BindInt(R.integer.spanCount)
    int spanCount;
    List<Recipe> mRecipes;
    RecipePresenter mPresenter;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        if (savedInstanceState == null) {
            mPresenter = new RecipePresenter(this);
            mPresenter.onTakeView(this);
        } else {
            mRecipes = savedInstanceState.getParcelable(RECIPES_LIST);
            mRecipeAdapter.updateAdapter(mRecipes);
        }
        mRecipeAdapter = new RecipeAdapter();
        mLayoutManager = new GridLayoutManager(this, spanCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecipeAdapter);
        mRecipeAdapter.setListener(new RecipeAdapter.Listener() {
            @Override
            public void onClick(Recipe recipe, int position) {
                RecipeDetailsActivity.startActivity(RecipesActivity.this, recipe, position);
            }
        });
        getIdlingResource();

    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    private void setIdleState() {
        if (mIdlingResource != null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIdlingResource.setIdleState(true);
                }
            }, 2000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onTakeView(null);
        if (!this.isChangingConfigurations())
            mPresenter = null;
    }

    public void onRecipesNext(List<Recipe> recipes) {
        mRecipes = recipes;
        mRecipeAdapter.updateAdapter(mRecipes);
        setIdleState();

    }

    public void onRecipesError(Throwable throwable) {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPES_LIST, (ArrayList<Recipe>) mRecipes);
    }
}
