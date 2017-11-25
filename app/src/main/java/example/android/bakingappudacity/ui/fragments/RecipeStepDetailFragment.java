package example.android.bakingappudacity.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Recipe;
import example.android.bakingappudacity.models.Step;
import example.android.bakingappudacity.ui.adapters.StepInstructionsAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeStepDetailFragment extends Fragment {

    private static final String ARGUMENT_EXTRA = "Recipe";
    private static final String RECIPE_LIST = "RecipeList";
    private static final String POSITION = "pos";
    int postion;
    Recipe recipe;
    @BindView(R.id.rv_steps_inst)
    RecyclerView mStepInstructionsRecyclerView;
    StepInstructionsAdapter mStepInstructionsAdapter;
    RecyclerView.LayoutManager mStepInstructionsLayoutManager;

    public static RecipeStepDetailFragment newInstance(Recipe step, int pos) {
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_EXTRA, step);
        args.putInt(POSITION, pos);
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RecipeStepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        ButterKnife.bind(this, rootView);
        mStepInstructionsAdapter = new StepInstructionsAdapter();
        mStepInstructionsRecyclerView.setNestedScrollingEnabled(false);
        mStepInstructionsRecyclerView.setAdapter(mStepInstructionsAdapter);
        mStepInstructionsLayoutManager = new LinearLayoutManager(getActivity());
        mStepInstructionsRecyclerView.setLayoutManager(mStepInstructionsLayoutManager);
        mStepInstructionsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (getArguments() != null && getArguments().containsKey(ARGUMENT_EXTRA) && getArguments().getParcelable(ARGUMENT_EXTRA) != null) {
            postion = getArguments().getInt(POSITION);
            recipe = getArguments().getParcelable(ARGUMENT_EXTRA);
            if (savedInstanceState == null) {
                mStepInstructionsAdapter.updateAdapter(recipe.getSteps());
            } else {
                ArrayList<Step> steps = savedInstanceState.getParcelable(RECIPE_LIST);
                mStepInstructionsAdapter.updateAdapter(steps);
            }
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_LIST, (ArrayList<Step>) recipe.getSteps());
    }
}