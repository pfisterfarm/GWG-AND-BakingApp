package com.pfisterfarm.bakingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfisterfarm.bakingapp.model.Step;

import java.util.ArrayList;

import static com.pfisterfarm.bakingapp.RecipeDetailFragment.ARG_ITEM_ID;

public class StepDetailFragment extends Fragment {

    private static final String STEP_SELECTED = "step_selected";

    private TextView stepInstructionsTV;
    private int mCurrentStep;
    private int maxSteps;
    private Step mStep;
    BottomNavigationView bott_nav;

    public StepDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mStep = getArguments().getParcelable(ARG_ITEM_ID);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail_fragment, container, false);
        stepInstructionsTV = rootView.findViewById(R.id.step_instructions);
        stepInstructionsTV.setText(mStep.getDescription());
        return rootView;
    }

    public void setStepDescription(String newText) {
        stepInstructionsTV.setText(newText);
    }

}
