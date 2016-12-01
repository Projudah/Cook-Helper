package com.example.projudah.cookhelper;

public class customHashMap {
    private int maxChar;
    ExpandableArray<ExpandableArray<Recipe>> map;
    public customHashMap(int maxChar){
        map =
                new ExpandableArray<ExpandableArray<Recipe>>((this.powerSum(26, maxChar)));
        this.maxChar = maxChar;
    }

    //////////////////PUSH//////////////////////////////////
    /**
     * Recipe will provide the name that will be used as the key for the recipe;
     * Recipe's name must be at least maxChar long;
     * @param item -- the Recipe item that is pushed into the hashMap
     */
    public void push(Recipe item){

        /**
         * turn the Recipe's name into a key. toLowerCase() is required to reduce the
         * size of the hashMap;
         */
        int itemKey = this.translateString(item.getName().toLowerCase());
        if (this.map.get(itemKey) == null){

            //if there are no ExpArray at location == itemKey, then create a new
            //ExpArray at the location.
            ExpandableArray<Recipe> newItem = new ExpandableArray<Recipe>();
            newItem.add(item);;
            map.add(newItem, itemKey);

        } else {

            //add item into the ExpArray at location == itemKey;
            this.map.get(itemKey).add(item);
        }
    }
    /////////////////PULL//////////////////////////////////

    /**
     * Get a list of Recipe based on the given index
     * @param index
     * @return
     */
    public ExpandableArray<Recipe> get(int index){
        //This is used if the index is given;
        return this.map.get(index);
    }

    /**
     * Get all the Recipe that satisfy the given string
     * @param inputString -- search input
     * @return -- recipes that satisfy the search input
     */
    public ExpandableArray<Recipe> getAllRecipeChildren(String inputString){

        inputString = limitString(inputString, maxChar);
        //Call getAllRecipeParent to get the parent array;
        ExpandableArray<ExpandableArray<Recipe>> arrayOfParent =
                this.getAllRecipeParent(inputString);
        ExpandableArray<Recipe> arrayOfChildren =
                new ExpandableArray<Recipe>();
        for (int a = 0; a < arrayOfParent.itemSize(); a++){
            for (int b = 0; b < arrayOfParent.get(a).itemSize(); b++){
                arrayOfChildren.add(arrayOfParent.get(a).get(b));
            }
        }
        return arrayOfChildren;
    }

    /**
     * Returns a list of arrays of recipes that satisfies the given input
     * @param inputString
     * @return
     */
    public ExpandableArray<ExpandableArray<Recipe>> getAllRecipeParent(String inputString){

        inputString = limitString(inputString, maxChar);
        //Get all the parent array;
        ExpandableArray<ExpandableArray<Recipe>> arrayOfParent = new
                ExpandableArray<ExpandableArray<Recipe>>();

        int count = this.power(26, (maxChar - inputString.length()));
        int start = this.translateString(inputString);
        for (int a = 0; a < count; a++){
            arrayOfParent.add(this.get(start + a));
        }
        return arrayOfParent;
    }

    public Recipe searchSpecificRecipe(String input){
        ExpandableArray<Recipe> arrayOfRecipe = this.getAllRecipeChildren(input);
        for (int a = 0; a < arrayOfRecipe.arraySize(); a++){
            if (arrayOfRecipe.get(a) != null){
                if (arrayOfRecipe.get(a).getName().equals(input)){
                    return arrayOfRecipe.get(a);
                }
            }
        }
        return null;
    }

    ////////////////BACKEND METHODS////////////////////////

    /**
     * Returns the integer representation of a string;
     * @param string -- given string to be interpreted
     * @return -- integer representation of the string
     */
    public int translateString(String string){
        System.out.println("translating:" + string);
        int someInt = 0;
        for (int a = 1; a <= string.length(); a++){
            someInt +=
                    alphabetCharToInt(string.charAt(a - 1)) * (this.power(26, maxChar - a));
        }
        for (int b = 0; b < (maxChar - string.length()); b++){
            someInt += this.power(26, b);
        }
        return someInt;
    }


    /**
     * Lower the number representation of char in JAVA by 96 for
     * the purpose of hashing;
     * @param a
     * @return
     */
    public int alphabetCharToInt(char a){
        return ((int) a - 96);
    }

    /**
     * Return a^b;
     * @param a -- number to be power-ed
     * @param b -- magnitude of power
     * @return -- result of a^b
     */
    public int power(int a, int b){
        return ((int) (Math.pow((double) a, (double) b)));
    }

    /**
     * Return the sum of the sequence a^n + a^(n-1) + ... + a^(0);
     * @param num -- value of a
     * @param count -- value of n
     * @return -- the sum of the sequence a^n + a^(n-1) + ... + a^(0)
     */
    public int powerSum(int num, int count){
        int sum = 0;
        for (int a = 0; a <= count; a++){
            sum += this.power(num, a);
        }
        return sum;
    }

    /**
     * Given a string of length larger than the limit, will return a string that limited
     * to 4 characters from the left;
     * @param inputString -- string to be limited at length of limit
     * @param limit -- the length limit of inputString
     * @return -- inputString[0...4]
     */
    public String limitString(String inputString, int limit){
        String newString = "";
        char[] charArray = inputString.toCharArray();
        for (int a = 0; a < limit; a++){
            newString += charArray[a];
        }
        System.out.println("limitedString:" + newString);
        return newString;
    }
}

