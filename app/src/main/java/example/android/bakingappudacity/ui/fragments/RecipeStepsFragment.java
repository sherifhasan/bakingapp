package example.android.bakingappudacity.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Recipe;
import example.android.bakingappudacity.ui.adapters.StepAdapter;
import example.android.bakingappudacity.utility.PanesHandler;

/**
 * Created by sheri on 11/22/2017.
 */

public class RecipeStepsFragment extends Fragment {
    private static final String ARGUMENT_EXTRA = "Recipe";

    @BindView(R.id.rv_steps_view)
    RecyclerView mStepsRecyclerView;
    StepAdapter mStepAdapter;
    RecyclerView.LayoutManager mStepLayoutManager;
    PanesHandler panesHandler;
    Recipe recipe;

    public RecipeStepsFragment() {
    }

    public static RecipeStepsFragment newInstance(Recipe recipe) {
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_EXTRA, recipe);
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        ButterKnife.bind(this, rootView);

        mStepAdapter = new StepAdapter();
        mStepsRecyclerView.setNestedScrollingEnabled(false);
        mStepsRecyclerView.setAdapter(mStepAdapter);
        mStepLayoutManager = new LinearLayoutManager(getActivity());
        mStepsRecyclerView.setLayoutManager(mStepLayoutManager);
        mStepsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (savedInstanceState == null) {
            if (getArguments() != null && getArguments().containsKey(ARGUMENT_EXTRA) && getArguments().getParcelable(ARGUMENT_EXTRA) != null) {
                recipe = getArguments().getParcelable(ARGUMENT_EXTRA);
                if (recipe.getSteps() != null) {
                    mStepAdapter.updateAdapter(recipe.getSteps());
                }
            }
        } else {
            recipe = savedInstanceState.getParcelable(ARGUMENT_EXTRA);
            if (recipe.getSteps() != null) {
                mStepAdapter.updateAdapter(recipe.getSteps());
            }
        }
        mStepAdapter.setListener(new StepAdapter.Listener() {
            @Override
            public void onClick(int pos) {
                ((PanesHandler) getActivity()).setSelectedName(recipe, pos);

            }
        });
        return rootView;
    }

    public void setPanesListener(PanesHandler nameListener) {
        panesHandler = nameListener;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARGUMENT_EXTRA, recipe);
    }

}