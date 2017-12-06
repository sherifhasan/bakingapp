package example.android.bakingappudacity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import example.android.bakingappudacity.ui.activities.RecipeDetailsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by sheri on 12/6/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityTest {

    @Rule
    public IntentsTestRule<RecipeDetailsActivity> mRule = new IntentsTestRule<>(RecipeDetailsActivity.class);
    private IdlingResource mIdlingResource;

    @Before
    public void register() {
        mIdlingResource = mRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void testToolbar() throws Exception {
        onView(withId(R.id.details_toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void checkFragmentsIsShown() throws Exception {
        onView(withId(R.id.fragment1_container)).check(matches(isDisplayed()));
        onView(withId(R.id.fragment2_container)).check(matches(isDisplayed()));
    }


    @After
    public void unregister() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}