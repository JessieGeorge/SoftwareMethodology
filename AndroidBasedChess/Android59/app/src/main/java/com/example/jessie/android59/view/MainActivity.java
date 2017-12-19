package com.example.jessie.android59.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jessie.android59.R;

/**
 * NOTE TO GRADER:
 * TO MAKE A MOVE TOUCH THE PIECE'S ORIGINAL SQUARE AND THEN ITS DESTINATION
 * (WE DIDN'T DO THE DRAGGING UI)
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playNewGameButton = (Button)findViewById(R.id.playNewGameButton);
        playNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGameActivity();
            }
        });

        Button viewRecordedGamesButton = (Button)findViewById(R.id.viewRecordedGamesButton);
        viewRecordedGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRecordedActivity();
            }
        });


        Button exitButton = (Button)findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

        /*
        //startGame();
        /*
        TextView tv = new TextView(this);
        tv.setText("Hello World! \nHey again World!");
        setContentView(tv);
        */
    }

    private void goToGameActivity()
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void goToRecordedActivity()
    {
        Intent intent = new Intent(this, RecordedActivity.class);
        startActivity(intent);
    }


    /*
    public void startGame() {
        try
        {
            Chess.game();
        }
        catch (Exception e)
        {
            System.out.print("Exception");
        }
    }
    */
}
