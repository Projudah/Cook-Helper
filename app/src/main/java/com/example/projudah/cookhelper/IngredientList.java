package com.example.projudah.cookhelper;

import android.app.Activity;
import android.content.Context;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Wes on 2016-11-25.
 */
public class IngredientList{
    ArrayList<String> ingredients;

    public IngredientList(){
        ingredients = new ArrayList<>();
        File file;
        file = new File("ingredients.json");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){}
        }
    }
  
    public void writeRecipe(Activity cont) throws JsonGenerationException, JsonMappingException, IOException{

        ObjectMapper mapper = new ObjectMapper();
        String sdf = mapper.writeValueAsString(ingredients);
        FileOutputStream outputStream;

        try {
            outputStream = cont.openFileOutput("ingredients.json",0);
            outputStream.write(sdf.getBytes());
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
   }
   
    public ArrayList<String> readRecipe(Activity cont) throws JsonParseException, JsonMappingException, IOException{
        File file;
        file = new File(cont.getFilesDir().getAbsolutePath()+"/ingredients.json");
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){}
        }
        ObjectMapper mapper = new ObjectMapper();
        ingredients.clear();
        ingredients = mapper.readValue(file,ArrayList.class);
        return ingredients;
   }

    public void delete(String name){
        ingredients.remove(name);
    }

    public void store(String name){
        ingredients.add(name);
    }


}

