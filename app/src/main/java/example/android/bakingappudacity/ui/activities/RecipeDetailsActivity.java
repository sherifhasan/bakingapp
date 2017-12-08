package example.android.bakingappudacity.ui.activities;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private static final String SCROLL_POSITION = "scroll_position";
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
    @BindView(R.id.recipe_image)
    ImageView mImageView;
    @BindView(R.id.nested_scroll)
    NestedScrollView mScrollView;
    public static int scrollX = 0;
    public static int scrollY = -1;


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
            if (!TextUtils.isEmpty(recipe.getRecipeImage())) {
                mImageView.setVisibility(View.VISIBLE);
                Picasso.with(this).load(recipe.getRecipeImage()).into(mImageView);
            }
            RecipeIngredientFragment ingredientFragment = RecipeIngredientFragment.newInstance((Recipe) getIntent().getExtras().getParcelable(EXTRA_RECIPE));
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment1_container, ingredientFragment).commit();

            RecipeStepsFragment stepsFragment = RecipeStepsFragment.newInstance((Recipe) getIntent().getExtras().getParcelable(EXTRA_RECIPE));
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment2_container, stepsFragment).commit();
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

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(SCROLL_POSITION, new int[]{mScrollView.getScrollX(), mScrollView.getScrollY()});
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray(SCROLL_POSITION);
        if (position != null)
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(position[0], position[1]);
                }
            });
    }
}