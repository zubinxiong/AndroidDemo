package me.ewriter.chapter13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.ewriter.chapter13.game2048.Game;
import me.ewriter.chapter13.puzzle.XPuzzleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void BtnXPuzzle(View view) {
        startActivity(new Intent(this, XPuzzleActivity.class));
    }

    public void Btn2048(View view) {
        startActivity(new Intent(this, Game.class));
    }
}
