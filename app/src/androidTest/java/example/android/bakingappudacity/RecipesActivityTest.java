package example.android.bakingappudacity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import example.android.bakingappudacity.ui.activities.RecipesActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by sheri on 12/5/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipesActivityTest {
    private static final String INTENT_EXTRA = "Recipe";

    @Rule
    public IntentsTestRule<RecipesActivity> mRule = new IntentsTestRule<>(RecipesActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void register() {
        mIdlingResource = mRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    //Test clicking on Brownies from RecyclerView
    @Test
    public void clickOnRecipeCardToOpenRecipeDetailsActivity() throws Exception {
        onView(withId(R.id.recipe_rv_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(hasExtraWithKey(INTENT_EXTRA));
    }

    @Test
    public void testToolbar() throws Exception {
        onView(withId(R.id.main_toolbar)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(withParent(withId(R.id.main_toolbar))));
    }

    @After
    public void unregister() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}