package com.pfisterfarm.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.pfisterfarm.bakingapp.model.Recipe;
import com.pfisterfarm.bakingapp.model.Step;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {

    private static final String RECIPE_STEPS = "recipe_steps";
    private static final String STEP_SELECTED = "step_selected";
    private static final String RECIPE_NAME = "recipe_name";
    private static final String STEP_DETAIL_TAG = "step_detail_tag";

    private BottomNavigationView bott_nav;
    private ArrayList<Step> mSteps;
    private int maxSteps;
    private int mCurrentStep;
    private String mRecipeName;
    private StepDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {

            mSteps = getIntent().getParcelableArrayListExtra(RECIPE_STEPS);
            mCurrentStep = getIntent().getIntExtra(STEP_SELECTED, 0);
            mRecipeName = getIntent().getStringExtra(RECIPE_NAME);
            setTitle(mRecipeName);
            maxSteps = mSteps.size();

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(RecipeDetailFragment.ARG_ITEM_ID,
                    mSteps.get(mCurrentStep));
            fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_fragment_container, fragment, STEP_DETAIL_TAG)
                    .commit();
        } else {
            mSteps = savedInstanceState.getParcelableArrayList(RECIPE_STEPS);
            mCurrentStep = savedInstanceState.getInt(STEP_SELECTED);
            mRecipeName = savedInstanceState.getString(RECIPE_NAME);
            setTitle(mRecipeName);
            maxSteps = mSteps.size();
            fragment = (StepDetailFragment)getSupportFragmentManager().findFragmentByTag(STEP_DETAIL_TAG);
        }
        bott_nav = findViewById(R.id.step_navigation);
        bott_nav.getMenu().findItem(R.id.display_step_no).setTitle("Step " + (mCurrentStep + 1) + " of " + maxSteps);
        bott_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_previous:
                        if (mCurrentStep > 0) {
                            mCurrentStep--;
                            fragment.releasePlayer();
                            loadStepData(mCurrentStep);
                        }
                        break;
                    case R.id.action_next:
                        if (mCurrentStep < maxSteps-1) {
                            mCurrentStep++;
                            fragment.releasePlayer();
                            loadStepData(mCurrentStep);
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_STEPS, mSteps);
        outState.putInt(STEP_SELECTED, mCurrentStep);
        outState.putString(RECIPE_NAME, mRecipeName);
    }

    private void loadStepData(int whatStep) {
        fragment.setCurrentStep(mSteps.get(whatStep));
        bott_nav.getMenu().findItem(R.id.display_step_no).setTitle("Step " + (whatStep + 1) + " of " + maxSteps);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
