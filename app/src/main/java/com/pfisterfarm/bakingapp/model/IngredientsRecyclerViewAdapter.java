package com.pfisterfarm.bakingapp.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfisterfarm.bakingapp.R;
import com.pfisterfarm.bakingapp.RecipeDetailFragment;

import java.util.ArrayList;

public class IngredientsRecyclerViewAdapter
        extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.ViewHolder> {

    private final RecipeDetailFragment mParentActivity;
    private ArrayList<Ingredient> mIngredients;
    // Ingredients onClickListener... currently not being used
//        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        };

    public IngredientsRecyclerViewAdapter(RecipeDetailFragment parent,
                                   ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
        mParentActivity = parent;
//            mTwoPane = twoPane;
    }

    @Override
    public IngredientsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_layout, parent, false);
        return new IngredientsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IngredientsRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.mIngredientDetail.setText(
                mIngredients.get(position).getQuantity() + " " +
                        mIngredients.get(position).getMeasure() + " " +
                        mIngredients.get(position).getIngredient()
        );

        holder.itemView.setTag(mIngredients.get(position));
//            holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null) {
            return mIngredients.size();
        } else {
            return 0;
        }
    }

    public void setIngredientData(ArrayList<Ingredient> incomingIngredients) {
        mIngredients = incomingIngredients;
        notifyDataSetChanged();
    }

    public ArrayList<Ingredient> getIngredientData() {
        return mIngredients;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView mIngredientDetail;

        ViewHolder(View view) {
            super(view);
            mIngredientDetail = view.findViewById(R.id.ingredient_detail);
        }
    }
}
