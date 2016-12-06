package com.example.projudah.cookhelper;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Wes on 2016-11-25.
 */
public class IngredientList<String> extends ArrayList<String> {

    ArrayList<String> ingredients;
    public IngredientList(){
        File file;
        file = new File("ingredients.json");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){}
        }
    }
  
    public void writeRecipe() throws JsonGenerationException, JsonMappingException, IOException{
        File file;
        file = new File("ingredients.json");
        if(file.exists()){
            file.delete();
        }
        else{
            try{
                file.createNewFile();
            }catch(IOException e){}
        }
     ObjectMapper mapper = new ObjectMapper();
     mapper.writeValue(file,this);
   }
   
    public IngredientList<String> readRecipe() throws JsonParseException, JsonMappingException, IOException{
        File file;
        file = new File("ingredients.json");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){}
        }
        ObjectMapper mapper = new ObjectMapper();
        this.clear();
        IngredientList<String> ingredient;
        ingredient = mapper.readValue(file, IngredientList.class);
        this.addAll(ingredient);
        return ingredient;
   }

    public void delete(String name){
        this.remove(name);
    }

    public void store(String name){
        this.add(name);
    }
}