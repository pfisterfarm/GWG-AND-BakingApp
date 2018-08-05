package com.pfisterfarm.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfisterfarm.bakingapp.model.Recipe;
import com.pfisterfarm.bakingapp.retrofit.BakingClient;
import com.pfisterfarm.bakingapp.retrofit.BakingInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.squareup.picasso.Picasso;


public class RecipeListActivity extends AppCompatActivity {


    private static final String RECIPES = "recipes";
    private BakingInterface bakingService;
    private ArrayList<Recipe> recipeList;
    View recyclerView;
    RecipeRecyclerViewAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList(RECIPES);
            mRecipeAdapter.setRecipeData(recipeList);
        } else {
            recipeList = new ArrayList<Recipe>();
            BakingInterface bakingInterface = BakingClient.getClient();
            Call<ArrayList<Recipe>> recipeList = bakingInterface.fetchRecipes();

            recipeList.enqueue(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                    ArrayList<Recipe> recipes = response.body();
                    mRecipeAdapter.setRecipeData(recipes);
                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    Log.v("fetch recipes failed: ", t.getMessage());
                }
            });
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(RECIPES, mRecipeAdapter.getRecipeData());
        super.onSaveInstanceState(outState);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecipeAdapter = new RecipeRecyclerViewAdapter(this, recipeList);
        recyclerView.setAdapter(mRecipeAdapter);
    }

    public static class RecipeRecyclerViewAdapter
            extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

        private final RecipeListActivity mParentActivity;
        private ArrayList<Recipe> mRecipes;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, (Recipe) view.getTag());

                    context.startActivity(intent);

            }
        };

        RecipeRecyclerViewAdapter(RecipeListActivity parent,
                                      ArrayList<Recipe> recipes) {
            mRecipes = recipes;
            mParentActivity = parent;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            String thumbURL;
            holder.mRecipeName.setText(mRecipes.get(position).getName());
            holder.mRecipeServes.setText("Serves " + mRecipes.get(position).getServings());
            thumbURL = mRecipes.get(position).getImage();
            if ((thumbURL == null) || (thumbURL == "")) {
                // NOTE: default thumbnail taken from http://www.clipartpanda.com/clipart_images/baking-pan-clipart-clipart-71806623
                holder.mThumbnail.setImageResource(R.drawable.baking);
             } else {
                    // load thumbnail from URL provided, with default thumbnail as backup
                    Picasso.with(holder.mThumbnail.getContext()).
                            load(thumbURL).
                            error(R.drawable.baking).
                            fit().
                            into(holder.mThumbnail);
              }


            holder.itemView.setTag(mRecipes.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            if (mRecipes != null) {
                return mRecipes.size();
            } else {
                return 0;
            }
        }

        public void setRecipeData(ArrayList<Recipe> incomingRecipes) {
            mRecipes = incomingRecipes;
            notifyDataSetChanged();
        }

        public ArrayList<Recipe> getRecipeData() {
            return mRecipes;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView mThumbnail;
            final TextView mRecipeName;
            final TextView mRecipeServes;

            ViewHolder(View view) {
                super(view);
                mThumbnail = (ImageView) view.findViewById(R.id.recipe_thumbnail);
                mRecipeName = (TextView) view.findViewById(R.id.recipe_name);
                mRecipeServes = (TextView) view.findViewById(R.id.recipe_serves);
            }
        }
    }
}