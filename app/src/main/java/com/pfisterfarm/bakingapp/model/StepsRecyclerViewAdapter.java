package com.pfisterfarm.bakingapp.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfisterfarm.bakingapp.R;
import com.pfisterfarm.bakingapp.RecipeDetailActivity;
import com.pfisterfarm.bakingapp.RecipeDetailFragment;
import com.pfisterfarm.bakingapp.StepDetailActivity;

import java.util.ArrayList;

public class StepsRecyclerViewAdapter
        extends RecyclerView.Adapter<StepsRecyclerViewAdapter.ViewHolder> {

    private final RecipeDetailFragment mParentActivity;
    private ArrayList<Step> mSteps;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, StepDetailActivity.class);
            intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, (Step) view.getTag());

            context.startActivity(intent);
        }
    };

    public StepsRecyclerViewAdapter(RecipeDetailFragment parent,
                             ArrayList<Step> steps) {
        mSteps = steps;
        mParentActivity = parent;
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

        holder.itemView.setTag(mSteps.get(position));
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

    public View.OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

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
