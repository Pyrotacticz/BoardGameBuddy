package com.example.rockerfockers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    private Game game;
    private RecyclerView rvGame;
    private GameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        rvGame = (RecyclerView) findViewById(R.id.gameId);

        game = new Game();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(game.getName());

        adapter = new GameAdapter(game);

        rvGame.setAdapter(adapter);

        rvGame.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    public void addResource(View view) {
        game.resources.add(new Resource());
        adapter.notifyItemChanged(game.resources.size() - 1);
    }

    public void onEditAction(MenuItem mi) {
        //TODO
    }
}