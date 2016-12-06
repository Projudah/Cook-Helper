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
    public String steps;

    public Recipe(){
        this.name="";
        this.category="";
        ingredients = new ArrayList<String>();
        this.steps = "";
    }

    public Recipe(String name, String category, String type, String steps){
        this.name = name;
        this.category = category;
        ingredients = new ArrayList<String>();
        this.type = type;
        this.steps = steps;
    }

    public String getName(){
        return this.name;
    }
    
    public String toString(){
        return("Name:"+this.getName()+" Category:"+this.category+ "Ingredients: "+ingredients.get(0));
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

}
