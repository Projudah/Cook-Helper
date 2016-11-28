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
  
  File file;
  public IngredientList(){
    file = new File("ingredients.json");
    if(!file.exists()){
      try{
        file.createNewFile();
      }catch(IOException e){}
    }
  }
  
   public void writeRecipe() throws JsonGenerationException, JsonMappingException, IOException{
     ObjectMapper mapper = new ObjectMapper();
     mapper.writeValue(file,this);
   }
   
   public IngredientList<String> readRecipe() throws JsonParseException, JsonMappingException, IOException{
     ObjectMapper mapper = new ObjectMapper();
     IngredientList<String> fuckthis = new IngredientList<String>();
     fuckthis = mapper.readValue(file, IngredientList.class);
     return fuckthis;
   }
   
}