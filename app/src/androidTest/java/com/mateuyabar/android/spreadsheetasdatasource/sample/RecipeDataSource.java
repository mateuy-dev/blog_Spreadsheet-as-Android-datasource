package com.mateuyabar.android.spreadsheetasdatasource.sample;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Recipe data source
 */
public interface RecipeDataSource {
    // csv https://docs.google.com/a/google.com/spreadsheets/d/1TLV0JH_DJVb5KRQf56I_Cv6oZY-jHl6bYAIQ9QuBisk/export?gid=0&format=csv
    @GET("1TLV0JH_DJVb5KRQf56I_Cv6oZY-jHl6bYAIQ9QuBisk/export?gid=0&format=csv")
    public Call<List<Recipe>> getRecipes();
}
