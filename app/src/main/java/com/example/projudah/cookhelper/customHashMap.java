package com.example.projudah.cookhelper;

import java.util.ArrayList;

/**
 * Created by r3xas on 11/29/2016.
 */

public class customHashMap<Recipe> {


    private ExpandableArray<ExpandableArray<Recipe>> map;

    /**
     * customHashMap uses ExpandableArray<ExpandableArray<Recipe>> as the data structure.
     * It uses the last 4 characters (with only alphanumerics allowed) as the key for the item.
     */
    public customHashMap() {
        map = new ExpandableArray<ExpandableArray<Recipe>>(this.power(26,4));
    }

    /**
     * Push the given item inside the HashMap; the location of the item is decided by the key. If
     * the target location is empty, the given item will be converted into an ArrayList containing
     * only that item then is put at the location.
     * @param key
     * @param item
     */
    public void push(String key, Recipe item){
        int index = this.translate(key.toLowerCase()); //we are only using lowercase characters
        if (map.get(index) == null || map.size() == 0){
            ExpandableArray<Recipe> newItem = new ExpandableArray<Recipe>();
            newItem.add(item, 0);
            map.add(newItem, index);
        } else {
            map.get(index).add(item);
        }
    }

    /**
     * Return the entire items inside the map that satisfy the key.
     * @param stringKey
     * @return
     */
    public ExpandableArray<Recipe> get(String stringKey){
        if (stringKey.length() == 1){
            this.getAll(this.power((int) stringKey.charAt(0), 4));
        }
        return map.get(translate(stringKey.toLowerCase())); //we are only using lowercase character
    }

    /**
     * Returns the ExpandableArray<Recipe> located at the given index.
     * @param index
     * @return
     */
    public ExpandableArray<Recipe> get(int index){
        //This is used if the index is given;
        return map.get(index);
    }

    /**
     * Return all the recipes that satisfy the given string as an ArrayList
     * @param string
     * @return
     */
    public ArrayList<Recipe> getAllRecipeInIterableFromRange(String string){
        ArrayList<Recipe> someArray = new ArrayList<Recipe>();
        ExpandableArray<ExpandableArray<Recipe>> subsetOfMap = this.getAll(this.translate(string));
        for (int a = 0; a < subsetOfMap.size(); a++){
            for (int b = 0; b < subsetOfMap.get(a).size(); b++){
                someArray.add(subsetOfMap.get(a).get(b));
            }
        }

        return someArray;
    }
    /**
     * Returns the integer representation of the string.
     * @param string
     * @return
     */
    private int translate(String string){
        int someInt = 0; int mult = 26; int b = 0; int c = 0;
        char[] charArray = string.toCharArray();
        for (int a = 0; a < charArray.length; a++){
            c = charArray.length - a - 1;
            someInt += (
                    (alphabetCharToInt(charArray[a])) *
                            ((int) Math.pow((double)mult , (double) c))
            );
        }
        return someInt;
    }

    /**
     * Returns the integer representation of a characters. The values are exactly the JAVA default
     * minus 97. The modification is needed for the purpose of simpler and smaller hashing.
     * @param a
     * @return
     */
    private int alphabetCharToInt(char a){
        return ((int) a - 97);
    }

    /**
     * Returns all the item mapped by customHashMap.get(range*n) for all n until range*n > customHashMap.size();
     * @param range
     * @return
     */
    private ExpandableArray<ExpandableArray<Recipe>> getAll(int range){
        ExpandableArray<ExpandableArray<Recipe>> allItem  = new ExpandableArray<ExpandableArray<Recipe>>();
        int counter = 1;
        while (range*counter < map.size()){
            allItem.add(map.get(range*counter));
            counter++;
        }
        return allItem;
    }

    /**
     * Returns the size of the available array;
     * @return
     */
    public int size(){
        return map.size();
    }

    /**
     * A private method to calculate a^b;
     * @param a
     * @param b
     * @return
     */
    private int power(int a, int b){
        return (int) Math.pow((double) a, (double) b);
    }
}