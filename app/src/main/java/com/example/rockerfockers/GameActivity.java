package com.example.rockerfockers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
        getSupportActionBar().setTitle(game.getName());

        adapter = new GameAdapter(game);

        rvGame.setAdapter(adapter);

        rvGame.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addResource(View view) {
        game.resources.add(new Resource());
        adapter.notifyItemChanged(game.resources.size() - 1);
    }
}