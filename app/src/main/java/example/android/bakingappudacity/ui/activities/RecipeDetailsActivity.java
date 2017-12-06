package example.android.bakingappudacity.ui.activities;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.bakingappudacity.IdlingResource.SimpleIdlingResource;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Recipe;
import example.android.bakingappudacity.ui.fragments.RecipeIngredientFragment;
import example.android.bakingappudacity.ui.fragments.RecipeStepDetailFragment;
import example.android.bakingappudacity.ui.fragments.RecipeStepsFragment;
import example.android.bakingappudacity.ui.widgets.BakingAppWidget;
import example.android.bakingappudacity.utility.PanesHandler;
import example.android.bakingappudacity.utility.Utility;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecipeDetailsActivity extends AppCompatActivity implements PanesHandler {
    private static final String EXTRA_RECIPE = "Recipe";
    private static final String SELECTED_RECIPE = "index";
    boolean twoPanes;
    Recipe recipe;
    @BindView(R.id.details_toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.fragment_step_container)
    FrameLayout mFrame;
    @BindView(R.id.save_to_widget)
    FloatingActionButton mFabButton;
    int selectedRecipe;

    @Nullable
    private SimpleIdlingResource mIdlingResource;


    public static void startActivity(Context context, Recipe recipe, int position) {
        if (context == null) {
            return;
        }
        Intent i = new Intent(context, RecipeDetailsActivity.class).putExtra(EXTRA_RECIPE, recipe).putExtra(SELECTED_RECIPE, position);
        i.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        twoPanes = mFrame != null;
        if (getIntent() != null && getIntent().hasExtra(EXTRA_RECIPE) && getIntent().getExtras().getParcelable(EXTRA_RECIPE) != null) {
            recipe = getIntent().getExtras().getParcelable(EXTRA_RECIPE);
            selectedRecipe = getIntent().getExtras().getInt(SELECTED_RECIPE);
            getSupportActionBar().setTitle(recipe.getRecipeName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            RecipeIngredientFragment ingredientFragment = RecipeIngredientFragment.newInstance((Recipe) getIntent().getExtras().getParcelable(EXTRA_RECIPE));
            getSupportFragmentManager().beginTransaction().add(R.id.fragment1_container, ingredientFragment).commit();

            RecipeStepsFragment stepsFragment = RecipeStepsFragment.newInstance((Recipe) getIntent().getExtras().getParcelable(EXTRA_RECIPE));
            getSupportFragmentManager().beginTransaction().add(R.id.fragment2_container, stepsFragment).commit();
        }
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utility.setSelectedRecipeIndex(RecipeDetailsActivity.this, selectedRecipe);
                Intent intent = new Intent(RecipeDetailsActivity.this, BakingAppWidget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] ids = {R.xml.baking_app_widget_info};
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                sendBroadcast(intent);
                Toast.makeText(RecipeDetailsActivity.this, recipe.getRecipeName() + " " + getString(R.string.add), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void setSelectedName(Recipe recipe, int pos) {
        if (twoPanes) {
            RecipeStepDetailFragment fragment = RecipeStepDetailFragment.newInstance(recipe, pos);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_step_container, fragment, RecipeStepDetailFragment.class.getSimpleName())
                    .commit();
        } else {
            RecipeStepDetail.startActivity(getApplicationContext(), recipe, pos);
        }
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
            }, 1000);
        }
    }

}