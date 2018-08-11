package com.pfisterfarm.bakingapp.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfisterfarm.bakingapp.R;
import com.pfisterfarm.bakingapp.RecipeDetailActivity;
import com.pfisterfarm.bakingapp.RecipeDetailFragment;
import com.pfisterfarm.bakingapp.StepDetailActivity;
import com.pfisterfarm.bakingapp.StepDetailFragment;

import java.util.ArrayList;

import static com.pfisterfarm.bakingapp.RecipeDetailFragment.ARG_ITEM_ID;

public class StepsRecyclerViewAdapter
        extends RecyclerView.Adapter<StepsRecyclerViewAdapter.ViewHolder> {

    private static final String STEP_SELECTED = "step_selected";
    private static final String RECIPE_STEPS = "recipe_steps";

    private final RecipeDetailFragment mParentActivity;
    private StepDetailFragment fragment;
    private ArrayList<Step> mSteps;
    private boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            if (!mTwoPane) {
                Intent intent = new Intent(context, StepDetailActivity.class);
                intent.putParcelableArrayListExtra(RECIPE_STEPS, mSteps);
                intent.putExtra(STEP_SELECTED, (int) view.getTag());

                context.startActivity(intent);
            } else {
                if (fragment == null) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(ARG_ITEM_ID,
                            (Step) mSteps.get((int)view.getTag()));
                    fragment = new StepDetailFragment();
                    fragment.setArguments(arguments);
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                            .add(R.id.step_fragment_container, fragment)
                            .commit();
                } else {
                    fragment.setStepDescription(mSteps.get((int)view.getTag()).getDescription());
                }
            }

        }
    };

    public StepsRecyclerViewAdapter(RecipeDetailFragment parent,
                             ArrayList<Step> steps, boolean twoPane) {
        mSteps = steps;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @Override
    public StepsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_layout, parent, false);
        return new StepsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StepsRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.mStep.setText(
                (position + 1) + ". " + mSteps.get(position).getShortDescription()
        );

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        if (mSteps != null) {
            return mSteps.size();
        } else {
            return 0;
        }
    }

//    public View.OnClickListener getOnClickListener() {
//        return mOnClickListener;
//    }

    public void setStepData(ArrayList<Step> incomingSteps) {
        mSteps = incomingSteps;
        notifyDataSetChanged();
    }

    public ArrayList<Step> getStepData() {
        return mSteps;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView mStep;

        ViewHolder(View view) {
            super(view);
            mStep = view.findViewById(R.id.step_detail);
        }
    }
}
