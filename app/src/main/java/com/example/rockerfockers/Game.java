package com.example.rockerfockers;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable {
    private final String name;

    public List<Resource> resources;

    public Game() {
        name = "Game";
        resources = new ArrayList<>(3);
        resources.add(new Resource("Turn", 1));
        resources.add(new Resource("Player(s)", 2));
        resources.add(new Resource("Resource", 3));
    }

    public Game(String name, int... args) {
        this.name = name;
        resources = new ArrayList<>(args.length);
        for (int i = 0; i < args.length; i++) {
            resources.add(new Resource("Resource" + i, args[i]));
        }
    }

    public String getName() {
        return name;
    }
}
