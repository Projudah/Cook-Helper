package com.example.projudah.cookhelper;

/**
 * Created by r3xas on 12/5/2016.
 */

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecipeSingleton {

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

        System.out.println("mno such recipe found.");
        return null;
    }

    public static void addRecipeToSearchMap(
            ArrayList<Recipe> arrayOfRecipe, String name, String category, String type, String steps
    ) throws IOException{
        arrayOfRecipe.add(new Recipe(name, category, type, steps));
    }

    public static ArrayList<Recipe> getRecipesThatSatisfyString(
            ArrayList<Recipe> arrayOfRecipe, String searchString, int type
    ) throws IOException{

        ArrayList<Recipe> newArray = new ArrayList<Recipe>();
        for (int a = 0; a < arrayOfRecipe.size(); a++){
            boolean match = true;
            if (type == 0) {
                if (arrayOfRecipe.get(a).name.length() > searchString.length()) {
                    for (int b = 0; b < searchString.length(); b++) {
                        if (searchString.charAt(b) != arrayOfRecipe.get(a).name.charAt(b)) {
                            match = false;
                        }
                    }

                    if (match) {
                        newArray.add(arrayOfRecipe.get(a));
                    }
                }
            } else if (type == 1){
                if (arrayOfRecipe.get(a).name.length() > searchString.length()) {
                    for (int b = 0; b < searchString.length(); b++) {
                        if (searchString.charAt(b) != arrayOfRecipe.get(a).name.charAt(b)) {
                            match = false;
                        }
                    }

                    if (match) {
                        newArray.add(arrayOfRecipe.get(a));
                    }
                }
            } else if (type == 2){
                if (arrayOfRecipe.get(a).name.length() > searchString.length()) {
                    for (int b = 0; b < searchString.length(); b++) {
                        if (searchString.charAt(b) != arrayOfRecipe.get(a).name.charAt(b)) {
                            match = false;
                        }
                    }

                    if (match) {
                        newArray.add(arrayOfRecipe.get(a));
                    }
                }
            } else {
                throw new IOException("type must be 1, 2, or 3.");
            }
        }
        return newArray;
    }
}