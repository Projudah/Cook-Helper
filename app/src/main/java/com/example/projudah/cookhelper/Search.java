package com.example.projudah.cookhelper;

/**
 * Created by hanif on 12/5/2016.
 */

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Search {

    /**
    static File file;
    static ArrayList<Recipe> array = null;
    private RecipeSingleton(){
        //Exist to defeat construction;
    }

    public ArrayList<Recipe> getInstance() throws IOException{
        if (array == null){
            file = new File("recipes.json");

            if(!file.exists()){
                try{
                    file.createNewFile();
                }catch(IOException e){}
            }

            //Populate the array here from the json;
            ObjectMapper mapper = new ObjectMapper();
            array = mapper.readValue(file, ArrayList.class);
        }

        return array;
    }

    **/

    /**
     *
     * @param searchString - search input, this can be the string of the name, category or the type.
     * @param type - signifies the type of search you want to conduct. 1 - name, 2 - category and 3 - type.
     * @return Returns the Recipe that satisfies the condition.
     */
    public static Recipe getRecipeFromSearchMap(
            ArrayList<Recipe> arrayOfRecipe, String searchString, int type
    ) throws IOException {

        if (type == 0){
            for (Recipe rec : arrayOfRecipe){
                if (rec.name.equals(searchString)){
                    return rec;
                }
            }
        } else if (type == 1){
            for (Recipe rec : arrayOfRecipe){
                if (rec.category.equals(searchString)){
                    return rec;
                }
            }
        } else if (type == 2){
            for (Recipe rec : arrayOfRecipe){
                if (rec.type.equals(searchString)){
                    return rec;
                }
            }
        } else {
            throw new IOException("type must either be 1, 2 or 3.");
        }

        System.out.println("no such recipe found.");
        return null;
    }

    /**
     * Get an ArrayList of all the recipes from arrayOfRecipe that match the given searchString depending on the type.
     * type = 0 for name, type = 1 for category, type = 2 for type
     * @param arrayOfRecipe -- the ArrayList<Recipe> that we want to parse from
     * @param searchString -- the given search input
     * @param type -- the type of search
     * @return -- ArrayList of Recipe that satisfy the searchString
     * @throws IOException -- type is not 1, 2 or 3
     */
    public static ArrayList<Recipe> getRecipesThatSatisfyString(
            ArrayList<Recipe> arrayOfRecipe, String searchString, int type
    ) throws IOException{

        //Check search type
        if (type < 0 || type > 2){
            throw new IOException("type must be 1, 2 or 3.");
        }
        ArrayList<Recipe> newArray = new ArrayList<Recipe>();

        //Iterate through the given array to find the matching Recipe then append it to newArray if it does
        for (int a = 0; a < arrayOfRecipe.size(); a++){
            if (arrayOfRecipe.get(a).name.length() >= searchString.length()){
                boolean match = true;
                for (int b = 0; b < searchString.length(); b++){
                    if (
                            (type == 0) &&
                                    (searchString.toLowerCase().charAt(b) != arrayOfRecipe.get(a).name.toLowerCase().charAt(b))
                            ){
                        match = false;
                    } else if (
                            (type == 1) &&
                                    (searchString.toLowerCase().charAt(b) != arrayOfRecipe.get(a).category.toLowerCase().charAt(b))
                            ){
                        match = false;
                    } else if (
                            (type == 2) &&
                                    (searchString.toLowerCase().charAt(b) != arrayOfRecipe.get(a).type.toLowerCase().charAt(b))
                            ){
                        match = false;
                    }

                }
                if (match){
                    newArray.add(arrayOfRecipe.get(a));
                }
            }
        }

        return newArray;
    }

    public ArrayList<String> getIngredientFromIngredientList(
            ArrayList<String> list, String searchString
    ) throws IOException{

        ArrayList<String> newArray = new ArrayList<String>();

        for (int a = 0; a < list.size(); a++){
            if (list.get(a).length() >= searchString.length()){
                boolean match = true;
                for (int b = 0; b < searchString.length(); b++){
                    if (searchString.toLowerCase().charAt(b) != list.get(a).toLowerCase().charAt(b)){
                        match = false;
                    }
                }
                if (match){
                    newArray.add(list.get(a));
                }
            }
        }

        return newArray;
    }
}