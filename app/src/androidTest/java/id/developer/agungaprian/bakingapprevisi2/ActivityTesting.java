package id.developer.agungaprian.bakingapprevisi2;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import id.developer.agungaprian.bakingapprevisi2.ui.ListRecipeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by agungaprian on 30/10/17.
 */

@RunWith(AndroidJUnit4.class)
public class ActivityTesting {
    @Rule public ActivityTestRule<ListRecipeActivity> activityActivityTestRule
            = new ActivityTestRule<>(ListRecipeActivity.class);

    private IdlingResource idlingResource;
    @Before
    public void registerIdlingResource() {
        idlingResource = activityActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void checkText_RecipeActivity() {
        onView(withId(R.id.recyclerview_list_recipe)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));
    }

    @Test
    public void checkPlayerViewIsVisible_RecipeDetailActivity1() {
        onView(withId(R.id.recyclerview_list_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.recyclerview_list_detail_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.player_view)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}
