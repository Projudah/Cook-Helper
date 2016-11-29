package com.example.projudah.cookhelper;

import android.content.Context;

import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;


/**
 * Created by Wes on 2016-11-24.
 */
public class Recipe {

    public String name;
    public String category;
    public ArrayList<String> ingredients;
    public String type;

    public Recipe(){
        this.name="";
        this.category="";
        ingredients = new ArrayList<String>();
    }

    public Recipe(String name, String category, String type){
        this.name = name;
        this.category = category;
        ingredients = new ArrayList<String>();
        this.type = type;
    }

    public String getName(){
        return this.name;
    }

    public String toString(){
        return("Name:"+this.getName()+" Category:"+this.category+ "Ingredients: "+ingredients.get(0));
    }

    public void writeRecipe(String dir) throws JsonGenerationException, JsonMappingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(dir),this);
    }
    public String writeAsString() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
    private Recipe readRecipe(File file) throws JsonParseException, JsonMappingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        Recipe returnRecipe = new Recipe();
        returnRecipe = mapper.readValue(file, Recipe.class);
        return returnRecipe;
    }

    public void addIngredient(String ingredient){
        this.ingredients.add(ingredient);
    }
    /*
    public static void main (String[] args){
        ObjectMapper mapper = new ObjectMapper();
        Recipe test = new Recipe();
        test.name = "this is test";
        test.category = "category";
        test.addIngredient("butter");
        try {
            test.writeRecipe();
        }
        catch(IOException e){}
        Recipe test2 = new Recipe();
        test2.category = "category";
        try {
            test2 = test2.readRecipe(new File("this is test.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(test2.toString());

        IngredientList<String> testlist = new IngredientList<String>();
        testlist.add("ham");
        try{
          testlist.writeRecipe();
        }catch(Exception e){}
        IngredientList<String> testlist2 = new IngredientList<String>();
        try{
          testlist2 = testlist2.readRecipe();
        }catch(Exception e){}
        testlist2.add("Turkey");
        try{
          testlist2.writeRecipe();
        }catch(Exception e){}
    }
    */

}
