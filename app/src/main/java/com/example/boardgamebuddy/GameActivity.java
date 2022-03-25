
/*
 * Created by BoardGameBuddies
 * Copyright (c) 2022. All rights reserved.
 * Last modified 1/2/22, 3:34 PM
 */

package com.example.boardgamebuddy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.List;

// Handles the view of a single game
public class GameActivity extends AppCompatActivity {
    private Game game;
    private RecyclerView rvGame;
    private GameAdapter adapter;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        List<Resource> loadResources = toLoad();

        rvGame = findViewById(R.id.gameId);

        if (loadResources != null) {
            Log.i("SAVE", "Game loaded");
            game = new Game(loadResources);
        } else {
            game = new Game();
            for (int i = 0; i < game.resources.size(); i++) {
                game.resources.get(i).setIcon(R.drawable.ic_genres);
                game.resources.get(i).setTag(R.drawable.ic_genres);
            }
        }

        adapter = new GameAdapter(this, game);

        rvGame.setAdapter(adapter);

        rvGame.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);
        return true;
    }

    // Adds a new resource to the resources list.
    public void addResource(View view) {
        game.resources.add(new Resource(R.drawable.ic_genres, 2));
        adapter.notifyItemChanged(game.resources.size() - 1);
    }

    // Confirmation triggers saving all changes to the resources.
    public void onClickConfirm(View view) {
        adapter.updateEditable();
        findViewById(R.id.miCompose).setEnabled(true);
        Button addButton = findViewById(R.id.addResId);
        addButton.setVisibility(adapter.isEditable() ? View.VISIBLE : View.GONE);
        Button confirmButton = findViewById(R.id.confirmId);
        confirmButton.setVisibility(adapter.isEditable() ? View.VISIBLE : View.GONE);
        Button cancelButton = findViewById(R.id.cancelId);
        cancelButton.setVisibility(adapter.isEditable() ? View.GONE : View.GONE);
        ImageButton iconButton = findViewById(R.id.ic_resId);
        iconButton.setEnabled(adapter.isEditable());
        adapter.notifyDataSetChanged();
    }

    public void onClickCancel(View view) {
        // Future Implementation
        // For undoing the resource list changes.
    }

    // Edit view of the GameAdapter allowing changes made to the resources.
    public void onEditAction(MenuItem mi) {
        if (!adapter.isEditable()) {
            adapter.updateEditable();
            findViewById(R.id.miCompose).setEnabled(false);
            Button addButton = findViewById(R.id.addResId);
            addButton.setVisibility(adapter.isEditable() ? View.VISIBLE : View.GONE);
            Button confirmButton = findViewById(R.id.confirmId);
            confirmButton.setVisibility(adapter.isEditable() ? View.VISIBLE : View.GONE);
            Button cancelButton = findViewById(R.id.cancelId);
            cancelButton.setVisibility(adapter.isEditable() ? View.GONE : View.GONE);
            ImageButton iconButton = findViewById(R.id.ic_resId);
            iconButton.setEnabled(adapter.isEditable());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop() {
        toSave();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        toSave();
        super.onDestroy();
    }

    private void toSave() {
        SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);;
        SharedPreferences.Editor prefEditor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(game.resources);
        prefEditor.putString("resources", json);
        prefEditor.commit();
        Log.i("Saved", json);
    }

    private List<Resource> toLoad() {
        SharedPreferences pref = getSharedPreferences("test", MODE_PRIVATE);;
        Gson gson = new Gson();
        String json = pref.getString("resources", "");
        Type type = new TypeToken<List<Resource>>(){}.getType();
        List<Resource> res = gson.fromJson(json, type);
        return res;
    }
}