package com.example.galtabecka.assignment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondScreen extends AppCompatActivity {
    //Declaring variables.
    private Intent intent;
    private final static int HARD_MILLIS = 60000;
    private final static int MEDIUM_MILLIS = 45000;
    private final static int EASY_MILLIS = 30000;
    private final static int EASY = 2;
    private final static int MEDIUM_ROW = 4;
    private final static int MEDIUM_COL = 3;
    private final static int HARD = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.second_screen);

        Bundle userData = getIntent().getExtras();
        if (userData == null)
            return;

        //Getting values from Intent.
        final String name = userData.getString("name");
        final String age = userData.getString("age");
        final TextView nameText = findViewById(R.id.nameTv);

        //Setting player's name and age.
        nameText.setText(name + ", " + age);

        //Three buttons for each difficulty level.
        Button easyBtn = findViewById(R.id.easyBtn);
        Button mediumBtn = findViewById(R.id.mediumBtn);
        Button hardBtn = findViewById(R.id.hardBtn);


        //Three options for the difficulty levels.
        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SecondScreen.this, GameScreen.class);
                intent.putExtra("name", name);
                intent.putExtra("rows", EASY);
                intent.putExtra("cols", EASY);
                intent.putExtra("millis", EASY_MILLIS);
                startActivity(intent);
            }
        });

        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SecondScreen.this, GameScreen.class);
                intent.putExtra("name", name);
                intent.putExtra("rows", MEDIUM_ROW);
                intent.putExtra("cols", MEDIUM_COL);
                intent.putExtra("millis", MEDIUM_MILLIS);
                startActivity(intent);
            }
        });

        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(SecondScreen.this, GameScreen.class);
                intent.putExtra("name", name);
                intent.putExtra("rows", HARD);
                intent.putExtra("cols", HARD);
                intent.putExtra("millis", HARD_MILLIS);
                startActivity(intent);
            }
        });
    }
}