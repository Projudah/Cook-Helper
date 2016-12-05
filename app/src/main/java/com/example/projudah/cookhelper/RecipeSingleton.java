package com.example.projudah.cookhelper;

/**
 * Created by r3xas on 12/5/2016.
 */

import java.io.IOException;

public class RecipeSingleton {

    private static customHashMap theSearchMap = new customHashMap(3);

    public customHashMap getRecipeSearchInstance(){
        return theSearchMap;
    }

    /**
     *
     * @param searchString - search input, this can be the string of the name, category or the type.
     * @param type - signifies the type of search you want to conduct. 1 - name, 2 - category and 3 - type.
     * @return Returns the Recipe that satisfies the condition.
     */
    public Recipe getRecipeFromSearchMap(String searchString, int type) throws IOException {
        return theSearchMap.searchSpecificRecipe(searchString, type);
    }

    public void addRecipetoSearchMap(String name, String category, String type, IngredientList ingredient) throws IOException{
        Recipe newRecipe = new Recipe(name, category, type);
        theSearchMap.push(newRecipe);
    }

    public ExpandableArray<Recipe> getRecipesThatSatisfyString(String searchString, int type) throws IOException{
        return theSearchMap.getAllRecipeChildren(searchString, type);
    }
}
