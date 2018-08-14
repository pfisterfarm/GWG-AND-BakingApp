package com.pfisterfarm.bakingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfisterfarm.bakingapp.model.Ingredient;
import com.pfisterfarm.bakingapp.model.IngredientsRecyclerViewAdapter;
import com.pfisterfarm.bakingapp.model.Recipe;
import com.pfisterfarm.bakingapp.model.Step;
import com.pfisterfarm.bakingapp.model.StepsRecyclerViewAdapter;
import com.pfisterfarm.bakingapp.utils.helpers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String RECIPE_NAME = "recipe_name";
    public static final String INGREDIENT_SET = "ingredient_set";
    public static final String SCREEN_MODE = "screen_mode";

    private Recipe mRecipe;
    private RecyclerView ingredients_rv;
    private RecyclerView steps_rv;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;
    private IngredientsRecyclerViewAdapter mIngredientsAdapter;
    private StepsRecyclerViewAdapter mStepsAdapter;
    private boolean mTwoPane;
    private View rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Activity activity = this.getActivity();

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mRecipe = getArguments().getParcelable(ARG_ITEM_ID);
            mTwoPane = getArguments().getBoolean(SCREEN_MODE);

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
            SharedPreferences.Editor edit = pref.edit();
            ArrayList<Ingredient> storeIngredients = mRecipe.getIngredients();
            Set ingredientSet = new HashSet();
            for (Ingredient ingredient : storeIngredients) {
                ingredientSet.add(helpers.displayAmount(ingredient.getQuantity(), ingredient.getMeasure()) + ingredient.getIngredient());
            }
            edit.putString(RECIPE_NAME, mRecipe.getName());
            edit.putStringSet(INGREDIENT_SET, ingredientSet);
            edit.commit();


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        } else {
            ((ViewGroup) container.getParent()).removeView(rootView);
        }

        ingredients_rv = container.findViewById(R.id.ingredients_list);
        mIngredients = mRecipe.getIngredients();

        steps_rv = container.findViewById(R.id.steps_list);
        mSteps = mRecipe.getSteps();

        setupRecyclerViews();

        mIngredientsAdapter.setIngredientData(mIngredients);
        mStepsAdapter.setStepData(mSteps);

        return rootView;
    }

    private void setupRecyclerViews() {
        mIngredientsAdapter = new IngredientsRecyclerViewAdapter(this, mIngredients);
        ingredients_rv.setAdapter(mIngredientsAdapter);
        mStepsAdapter = new StepsRecyclerViewAdapter(this, mSteps, mTwoPane, mRecipe.getName());
        steps_rv.setAdapter(mStepsAdapter);

    }


}
