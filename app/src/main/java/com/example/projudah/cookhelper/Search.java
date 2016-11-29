package com.example.projudah.cookhelper;

import java.util.HashMap;

/**
 * Created by r3xas on 11/29/2016.
 */

public class Search {

    HashMap<String, Recipe> map;
    //Constructor
    public Search(HashMap<String, Recipe> map){
        this.map = map;
    }

    public Recipe queryFromHashMap(String query){
        Recipe
                item = this.map.get(this.translate(query));

        return item;
    }

    public void addIntoHashMap(Recipe item){
        /**
         * Use the last 4 characters from the item name as the key
         */
        map.put(theLastFourChar(item.getName()), item);
    }

    public String translate(String string){
        /**
         * This will remove any spaces and lowercase the givenString
         **/
        return (string.toLowerCase()).replaceAll("[^a-zA-Z0-9]","");
    }

    public String theLastFourChar(String string){
        char[] stringArray = string.toCharArray();
        string = "";
        for (int a = 0; a < 4; a++){
            string += stringArray[stringArray.length - 4 + a];
        }
        System.out.println(string);
        return string;
    }

    public int alphabetCharToInt(char a){
        return ((int) a - 97);
    }
}
