package com.pfisterfarm.bakingapp.retrofit;

import com.pfisterfarm.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingInterface {
    @GET("baking.json")
    Call<ArrayList<Recipe>> fetchRecipes();
}
