package com.example.projudah.cookhelper;

/**
 * Created by r3xas on 11/29/2016.
 */

public class ExpandableArray<E> {

    E[] array;
    private int endLocation;

    @SuppressWarnings("unchecked")
    public ExpandableArray(int size){
        array = (E[]) new Object[size];
        endLocation = 0;
    }

    @SuppressWarnings("unchecked")
    public ExpandableArray(){
        array = (E[]) new Object[10];
    }

    public E get(int location){
        /**
         * This is similar to "E[int]" in a normal array, except now you have to specify it
         * by giving the int by "E.get(location)"
         *
         * error if "int" is outside of index
         */
        return array[location];
    }

    @SuppressWarnings("unchecked")
    public void add(E item, int location){
        /**
         * This is similar to "E[int] = item" in a normal array, except now you have to specify it
         * by giving the int by "E.get(location, item)"
         *
         * error if "item" type does not match
         * no error if location is outside of index, the array will simply expand
         */

        //check if the location is outside of index
        if (location < array.length){
            array[location] = item;
        } else {
            E[] tmpArray = (E[]) new Object[array.length*2];

            //copy the old array into the new array
            for (int a = 0; a < array.length; a++){
                tmpArray[a] = array[a];
            }
            array = tmpArray;
            //copy the item into its location by recursively call the method
            this.add(item,  location);
        }
    }


    /**
     * NOTE: The could be a better implementation of this program, it is currently an O(n) algorithm
     * @param item
     */
    public void add(E item){
        if (array[endLocation] != null){
            endLocation++;
            this.add(item);
        } else {
            array[endLocation] = item;
        }
    }

    public int size() {
        return array.length;
    }
}
