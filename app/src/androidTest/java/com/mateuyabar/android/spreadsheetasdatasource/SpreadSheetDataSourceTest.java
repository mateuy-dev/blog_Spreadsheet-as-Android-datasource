package com.mateuyabar.android.spreadsheetasdatasource;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.mateuyabar.android.spreadsheetasdatasource.sample.RecipeDataSource;
import com.mateuyabar.android.spreadsheetasdatasource.sample.Recipe;

import java.util.List;

import retrofit.Retrofit;

/**
 * Test
 */
public class SpreadSheetDataSourceTest extends ApplicationTestCase<Application> {
    public SpreadSheetDataSourceTest() {
        super(Application.class);
    }

    public void testSpreadSheatData() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://docs.google.com/a/google.com/spreadsheets/d/")
                .addConverterFactory(new CsvConverterFactory())
                .build();

        RecipeDataSource service = retrofit.create(RecipeDataSource.class);
        List<Recipe> models = service.getRecipes().clone().execute().body();
        assertEquals(models.size(), 3);
        Recipe first = models.get(0);
        assertEquals(first.getId(), "1");
        assertEquals(first.getName(), "Rabbit with chocolate");
        assertEquals(first.getIntTest()+0, 1);
        assertEquals(first.getDoubleTest()+0.0, 1.1);
        assertEquals(first.getLongTest()+0, 1l);
        assertEquals(first.getBooleanTest(), Boolean.TRUE);

        Recipe third = models.get(2);
        assertEquals(third.getBooleanTest(), Boolean.FALSE);


    }
}