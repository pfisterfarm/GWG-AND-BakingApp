package com.pfisterfarm.bakingapp;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeRecyclerTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mRecipeRecyclerTest
            = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void checkRecipeList_MakeSureDataIsCorrect() {

        onView(ViewMatchers.withId(R.id.recipe_list))
                .check(matches(hasDescendant(withText("Cheesecake"))));

    }
}

