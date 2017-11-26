package example.android.bakingappudacity.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Recipe;
import example.android.bakingappudacity.ui.fragments.RecipeIngredientFragment;
import example.android.bakingappudacity.ui.fragments.RecipeStepDetailFragment;
import example.android.bakingappudacity.ui.fragments.RecipeStepsFragment;
import example.android.bakingappudacity.utility.PanesHandler;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecipeDetailsActivity extends AppCompatActivity implements PanesHandler {
    private static final String EXTRA_RECIPE = "Recipe";
    boolean twoPanes;
    Recipe recipe;

    public static void startActivity(Context context, Recipe recipe) {
        if (context == null) {
            return;
        }
        Intent i = new Intent(context, RecipeDetailsActivity.class).putExtra(EXTRA_RECIPE, recipe);
        i.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);
        Toolbar toolbar = ButterKnife.findById(this, R.id.details_toolbar);
        setSupportActionBar(toolbar);

        twoPanes = findViewById(R.id.fragment_step_container) != null;
        if (getIntent() != null && getIntent().hasExtra(EXTRA_RECIPE) && getIntent().getExtras().getParcelable(EXTRA_RECIPE) != null) {
            recipe = getIntent().getExtras().getParcelable(EXTRA_RECIPE);
            getSupportActionBar().setTitle(recipe.getRecipeName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            RecipeIngredientFragment ingredientFragment = RecipeIngredientFragment.newInstance((Recipe) getIntent().getExtras().getParcelable(EXTRA_RECIPE));
            getSupportFragmentManager().beginTransaction().add(R.id.fragment1_container, ingredientFragment).commit();

            RecipeStepsFragment stepsFragment = RecipeStepsFragment.newInstance((Recipe) getIntent().getExtras().getParcelable(EXTRA_RECIPE));
            getSupportFragmentManager().beginTransaction().add(R.id.fragment2_container, stepsFragment).commit();
        }
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
}