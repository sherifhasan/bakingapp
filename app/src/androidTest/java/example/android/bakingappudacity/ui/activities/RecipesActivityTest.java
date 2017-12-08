package example.android.bakingappudacity.ui.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import example.android.bakingappudacity.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipesActivityTest {

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityTestRule = new ActivityTestRule<>(RecipesActivity.class);

    @Test
    public void recipesActivityTest() {
        ViewInteraction textView = onView(
                allOf(withText("BakingApp"),
                        childAtPosition(
                                allOf(withId(R.id.main_toolbar),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("BakingApp")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.recipe_name_text_view), withText("Nutella Pie"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_card_view),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Nutella Pie")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.recipe_name_text_view), withText("Nutella Pie"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_card_view),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Nutella Pie")));

        ViewInteraction cardView = onView(
                allOf(withId(R.id.recipe_card_view),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_rv_view),
                                        0),
                                0),
                        isDisplayed()));
        cardView.perform(click());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.save_to_widget),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.CoordinatorLayout")),
                                        1),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction cardView2 = onView(
                allOf(withId(R.id.step_card_view),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rv_steps_view),
                                        0),
                                0),
                        isDisplayed()));
        cardView2.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.next_step), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.prev_step), withText("Prev"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0)));
        appCompatButton2.perform(scrollTo(), click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
