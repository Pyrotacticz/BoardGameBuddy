package com.example.rockerfockers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {
    private Game game;
    private RecyclerView rvGame;
    private GameAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        rvGame = findViewById(R.id.gameId);

        game = new Game();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Board Game Manager");

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

    public void onClickConfirm(View view) {
        adapter.updateEditable();
        findViewById(R.id.miCompose).setEnabled(true);
        Button addButton = findViewById(R.id.addResId);
        addButton.setVisibility(adapter.isEditable() ? View.VISIBLE : View.GONE);
        Button confirmButton = findViewById(R.id.confirmId);
        confirmButton.setVisibility(adapter.isEditable() ? View.VISIBLE : View.GONE);
        Button cancelButton = findViewById(R.id.cancelId);
        cancelButton.setVisibility(adapter.isEditable() ? View.GONE : View.GONE);
        adapter.notifyDataSetChanged();
    }

    public void onClickCancel(View view) {
        // Future Implementation
    }

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
            adapter.notifyDataSetChanged();
        }
    }
}