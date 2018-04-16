package com.example.galtabecka.assignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    public void onClick(View view) {
        //Declaring a new Intent linked to the following screen.
        Intent intent = new Intent(this, SecondScreen.class);

        //Two edit texts extracted from the xml file.
        final EditText nameText = findViewById(R.id.nameEt);
        final EditText ageText = findViewById(R.id.ageEt);

        //Getting the name and age of the player.
        String firstName = nameText.getText().toString();
        String lastName = ageText.getText().toString();

        //Connecting the values to the Intent.
        intent.putExtra("name", firstName);
        intent.putExtra("age", lastName);

        //Starting activity using the Intent.
        startActivity(intent);

    }
}
