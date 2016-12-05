package com.example.projudah.cookhelper;

/**
 * Created by r3xas on 11/29/2016.
 * updated as of 1/12/2016
 */

import java.io.IOException;

public class customHashMap {
    ExpandableArray<ExpandableArray<Recipe>> map;
    private final static int lettersCount = 26;

    public customHashMap(int maxChar){
        map =
                new ExpandableArray<ExpandableArray<Recipe>>((this.powerSum(26, maxChar)));
    }

    //////////////////PUSH//////////////////////////////////

    public void push(Recipe item) throws IOException{

        String string = "" +
                item.name.charAt(0) +
                item.category.charAt(0) +
                item.type.charAt(0);


        int a = alphabetCharToInt(item.name.toLowerCase().charAt(0));
        int b = alphabetCharToInt(item.category.toLowerCase().charAt(0));
        int c = alphabetCharToInt(item.type.toLowerCase().charAt(0));
        int location = (
                (a*(this.power(lettersCount, 2))) +
                (b*(this.power(lettersCount, 1))) +
                (c*(this.power(lettersCount, 0)))
        );

        System.out.println("push:location:" + location);
        if (string.length()  != 3){
            throw new IOException(
                    "insufficient string length. Either name, category or type is empty."
            );
        } else {
            if (map.get(location) == null){
                ExpandableArray<Recipe> newRecipeArray =
                        new ExpandableArray<Recipe>();
                newRecipeArray.add(item);
                map.add(
                        newRecipeArray,
                        location
                );
            } else {
                map.get(location).add(item);
            }
        }
    }
    /////////////////PULL//////////////////////////////////

    public ExpandableArray<Recipe> get(int index){
        //This is used if the index is given;
        return this.map.get(index);
    }

    public Recipe searchSpecificRecipe(
            String inputString, int num
    ) throws IOException{
        ExpandableArray<Recipe> arrayOfRecipe =
                this.getAllRecipeChildren(inputString, num);
        if (arrayOfRecipe == null){
            throw new IOException("arrayOfRecipe is null");
        } else {
            System.out.println("size:" + arrayOfRecipe.itemSize());
            for (int a = 0; a < arrayOfRecipe.itemSize(); a++){
                if (num == 0){
                    if (inputString == arrayOfRecipe.get(a).name){
                        return arrayOfRecipe.get(a);
                    }
                } else if (num == 1){
                    if (inputString == arrayOfRecipe.get(a).category){
                        return arrayOfRecipe.get(a);
                    }
                } else if (num == 2){
                    if (inputString == arrayOfRecipe.get(a).type){
                        return arrayOfRecipe.get(a);
                    }
                } else {
                    throw new IOException("num must be 1, 2 or 3.");
                }
            }
            return null;
        }
    }

    public ExpandableArray<Recipe> getAllRecipeChildren(
            String inputString, int num
    ) throws IOException{
        //Call getAllRecipeParent to get the parent array;
        ExpandableArray<ExpandableArray<Recipe>> arrayOfParent =
                this.getAllRecipeParent(inputString, num);
        if (arrayOfParent == null){
            return null;
        } else {
            ExpandableArray<Recipe> arrayOfChildren =
                    new ExpandableArray<Recipe>();
            for (int a = 0; a < arrayOfParent.itemSize(); a++) {
                for (int b = 0; b < arrayOfParent.get(a).itemSize(); b++) {
                    arrayOfChildren.add(arrayOfParent.get(a).get(b));
                }
            }
            return arrayOfChildren;
        }
    }

    private ExpandableArray<ExpandableArray<Recipe>> getAllRecipeParent(
            String inputString, int num
    ) throws IOException{

        //Get all the parent array;
        char theChar = inputString.toLowerCase().charAt(0);
        ExpandableArray<ExpandableArray<Recipe>> arrayOfParent =
                new ExpandableArray<ExpandableArray<Recipe>>();

        for (int a = 1; a <= lettersCount; a++){
            for (int b = 1; b < lettersCount; b++){
                int location =
                        this.translateLetter(theChar, num) +
                                this.translateNumber(a, b, num);
                arrayOfParent.add(map.get(
                        location
                ));
                /**
                System.out.println(
                        "get:location:" + location +
                                ":value:" + map.get(location)
                );
                 **/
            }
        }
        return arrayOfParent;
    }

    ////////////////BACKEND METHODS////////////////////////

    public int translateLetter(
            char a, int num
    ) throws IOException{
        int b;

        if (num == 0){
            b = 2;
        } else if (num == 1){
            b = 1;
        } else if (num == 2){
            b = 0;
        } else {
            throw new IOException("num must be 1, 2 or 3.");
        }

        return (
                this.alphabetCharToInt(a)*this.power(lettersCount, b)
        );
    }

    public int translateNumber(
            int d, int e, int num
    ) throws IOException{

        int a, b;

        if (num == 0){
            a = 1;b = 0;
        } else if (num == 1){
            a = 2; b = 0;
        } else if (num == 2){
            a = 2; b = 1;
        } else {
            throw new IOException("num must be 1, 2 or 3.");
        }
        return (
                (d*this.power(lettersCount,  a)) + (e*this.power(lettersCount, b))
        );
    }

    public int alphabetCharToInt(char a){
        return ((int) a - 96);
    }

    public int power(int a, int b){
        return ((int) (Math.pow((double) a, (double) b)));
    }

    public int powerSum(int num, int count){
        int sum = 0;
        for (int a = 0; a <= count; a++){
            sum += this.power(num, a);
        }
        return sum;
    }

    public String combineString(Recipe recipe){

        return "" +
                recipe.name.charAt(0) +
                recipe.category.charAt(0) +
                recipe.type.charAt(0);
    }
}