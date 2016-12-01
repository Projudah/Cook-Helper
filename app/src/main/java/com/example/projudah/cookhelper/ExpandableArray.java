package com.example.projudah.cookhelper;

/**
 * Created by r3xas on 11/29/2016.
 */

public class ExpandableArray<E> {

    private E[] array;
    private int endLocation, itemCount;
    @SuppressWarnings("unchecked")
    public ExpandableArray(int size){
        array = (E[]) new Object[size];
        endLocation = 0;
        itemCount = 0;
    }

    @SuppressWarnings("unchecked")
    public ExpandableArray(){
        array = (E[]) new Object[26];
        endLocation = 0;
        itemCount = 0;
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

    public void add(E item, int location){
        /**
         * This is similar to "E[int] = item" in a normal array, except now you have to specify it
         * by giving the int by "E.get(location, item)"
         *
         * error if "item" type does not match
         * no error if location is outside of index, the array will simply expand
         */

        //check if the location is outside of index
        if (location < (this.array.length)){
            itemCount++;
            if (this.array[location] != null){
                System.out.println("replacing old item with new one");
            }
            array[location] = item;
        } else {
            this.expandArray();
            //copy the item into its location by recursively call the method
            this.add(item,  location);
        }
    }

    public void remove(int location) throws IndexOutOfBoundsException{
        itemCount--;
        if (location >= (array.length)){
            throw new IndexOutOfBoundsException("index out of bound.");
        } else {
            array[location] = null;
        }
    }

    /**
     * NOTE: The could be a better implementation of this program, it is currently an O(n) algorithm
     * @param item
     */
    public void add(E item){
        if (endLocation < array.length){
            if (this.array[endLocation] != null){
                endLocation++;
                this.add(item, endLocation);
            } else {
                this.add(item, endLocation);
            }
        } else {
            this.expandArray();
            this.add(item);
        }
    }

    @SuppressWarnings("unchecked")
    private void expandArray(){
        E[] tmpArray = (E[]) new Object[this.array.length+1];

        //copy the old array into the new array
        for (int a = 0; a < array.length; a++){
            tmpArray[a] = this.array[a];
        }
        this.array = tmpArray;
    }

    public int arraySize() {
        return this.array.length;
    }

    public int itemSize(){
        return this.itemCount;
    }

    public boolean isEmpty(){
        return this.itemCount == 0;
    }

    public String toString(){
        String someString = "";
        for (int a = 0; a < array.length; a++){
            someString += a + " " + array[a] + '\n';
        }
        return someString;
    }
}

