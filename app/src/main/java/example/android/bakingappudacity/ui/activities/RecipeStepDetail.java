package example.android.bakingappudacity.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import butterknife.ButterKnife;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Recipe;
import example.android.bakingappudacity.ui.fragments.RecipeStepDetailFragment;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecipeStepDetail extends AppCompatActivity {

    private static final String EXTRA_RECIPE = "Recipe";
    private static final String POSITION = "pos";
    boolean twoPanes;
    Recipe recipe;
    int position;


    public static void startActivity(Context context, Recipe recipe, int postion) {
        if (context == null) {
            return;
        }
        Intent i = new Intent(context, RecipeStepDetail.class).putExtra(EXTRA_RECIPE, recipe).putExtra(POSITION, postion);
        i.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getIntent() != null && getIntent().hasExtra(EXTRA_RECIPE) && getIntent().getExtras().getParcelable(EXTRA_RECIPE) != null) {
            recipe = getIntent().getExtras().getParcelable(EXTRA_RECIPE);
            position = getIntent().getExtras().getInt(POSITION);
            Log.d("Pos", position + "");
            getSupportActionBar().setTitle(recipe.getRecipeName());

            RecipeStepDetailFragment stepsFragment = RecipeStepDetailFragment.newInstance(recipe, position);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_step_container_detail, stepsFragment).commit();
        }
    }


}