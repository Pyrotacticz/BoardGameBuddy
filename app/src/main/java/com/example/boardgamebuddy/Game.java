
/*
 * Created by BoardGameBuddies
 * Copyright (c) 2022. All rights reserved.
 * Last modified 1/2/22, 5:24 PM
 */

package com.example.boardgamebuddy;

import java.io.Serializable;
import java.util.*;

/* Game is a class that handles a single game with many resources.
 * Standard constructors and getters.
 */
public class Game implements Serializable {
    private final String name;

    public List<Resource> resources;

    // Default constructor
    public Game() {
        name = "Game";
        resources = new ArrayList<>(3);
        resources.add(new Resource("Turn", 1));
        resources.add(new Resource("Player(s)", 2));
        resources.add(new Resource("Resource", 3));
    }

    // Constructor
    /* Params
     * @name: name of the game.
     * @args: initial number of resources.
     */
    public Game(String name, int... args) {
        this.name = name;
        resources = new ArrayList<>(args.length);
        for (int i = 0; i < args.length; i++) {
            resources.add(new Resource("Resource" + i, args[i]));
        }
    }

    // Returns the name of the game.
    public String getName() {
        return name;
    }
}
