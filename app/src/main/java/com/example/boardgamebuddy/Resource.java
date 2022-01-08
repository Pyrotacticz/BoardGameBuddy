
/*
 * Created by BoardGameBuddies
 * Copyright (c) 2022. All rights reserved.
 * Last modified 1/2/22, 5:24 PM
 */

package com.example.boardgamebuddy;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/* Resource is a class that handles a single resource of a board game.
* Standard constructors, setters, getters, and keeps track of the
* Resource with adding and subtracting.
*/
public class Resource implements Serializable {
    private static int instance = 0;
    private int id;
    private String rName;
    private int count;
    private Drawable icon;

    // Default constructor
    public Resource() {
        id = instance++;
        rName = "Resource ";
        count = 0;
    }

    // Constructor
    /* Params
    * @name: name of the resource.
    * @count: initial count of the resource.
    */
    public Resource(String name, int count) {
        id = instance++;
        rName = name;
        this.count = count;
        icon = null;
    }

    // Constructor
    /* Params
     * @icon: sets this resource with an icon.
     */
    public Resource(Drawable icon) {
        this();
        this.icon = icon;
    }

    // Returns the resource's instance number
    public int getInstance() {
        return instance;
    }

    // Returns the name of the resource.
    public String getName() {
        return rName;
    }

    // Sets the name of the resource.
    /* Params
    * @name: name of the resource.
    */
    public void setName(String name) {
        rName = name;
    }

    // Returns of the total number of the resource.
    public int getCount() {
        return count;
    }

    // Sets the total number of the resource.
    /* Params
     * @num: number of resource,
     */
    public void setCount(int num) {
        count = num;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    // Adds one of the resource.
    public void add() {
        this.add(1);
    }

    // Adds num to the number of resource.
    /* Params
     * @num: number of resource.
     */
    public void add(int num) {
        count += num;
    }

    // Subtracts one of the resource.
    public void subtract() {
        this.subtract(1);
    }

    // Subtracts num to the number of resource.
    // Unable to subtract negative numbers or reduce past zero.
    /* Params
     * @num: number of resource.
     */
    public void subtract(int num) {
        count -= Math.min(count, Math.max(num, 0));
    }

    // Returns true if resources is not zero else false.
    public boolean nonzero() {
        return count != 0;
    }
}
