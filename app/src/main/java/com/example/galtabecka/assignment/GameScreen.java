package com.example.galtabecka.assignment;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Vector;

public class GameScreen extends AppCompatActivity {
    //Declaring variables.
    private MediaPlayer mp;
    private final static int DELAY_TIME = 2000;
    private static Card card1 = null;
    private static Card card2 = null;
    private static int counter = 0;
    private static int[] moviesPictArr = {R.drawable.almost_famous, R.drawable.almost_famous, R.drawable.good_will_hunting,
            R.drawable.good_will_hunting, R.drawable.inside_out, R.drawable.inside_out, R.drawable.interstellar,
            R.drawable.interstellar, R.drawable.the_lord_of_the_rings, R.drawable.the_lord_of_the_rings, R.drawable.the_dark_knight, R.drawable.the_dark_knight, R.drawable.saving_private_ryan,
            R.drawable.saving_private_ryan, R.drawable.wall_e, R.drawable.wall_e};
    private Vector<Card> cardVector = new Vector<>();
    private static Handler handler = new Handler();
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.game_screen);
        mp = MediaPlayer.create(this, R.raw.background_music);
        mp.setLooping(true);
        mp.start();


        //Putting data from Intent in a Bundle.
        Bundle userData = getIntent().getExtras();
        if (userData == null)
            return;

        //Initializing texts.
        final String name = userData.getString("name");
        TextView nameTv = findViewById(R.id.nameTv);
        nameTv.setText(name);

        final TextView timerTv = findViewById(R.id.timerTv);
        int milliseconds = userData.getInt("millis");
        countDownTimer = new CountDownTimer(milliseconds, 1000) {

            //Update time each second.
            public void onTick(long millisUntilFinished) {
                timerTv.setText(getString(R.string.time) + " " + millisUntilFinished / 1000);
            }

            //If time is up give the user an appropriate message.
            public void onFinish() {
                Toast.makeText(GameScreen.this, "GAME OVER!", Toast.LENGTH_SHORT).show();
                mp.stop();
                freeCards(false);
                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();
                    }
                }, DELAY_TIME);
            }
        }.start();

        int rows = userData.getInt("rows");
        int cols = userData.getInt("cols");

        //Building the grid from extracted input provided by the player.
        GridLayout gridLayout = findViewById(R.id.gameGrid);
        gridLayout.setColumnCount(rows);
        gridLayout.setRowCount(cols);

        Vector<Integer> picVector = new Vector<>();

        //Initializing the picture vector.
        for (int i = 0; i < rows * cols; i++) {
            picVector.add(i, moviesPictArr[i]);
        }

        //Shuffling the pictures.
        Collections.shuffle(picVector);
        //A loop to initialize the cards vector and add them to the grid.
        for (int i = 0; i < picVector.size(); i++) {
            Card bt = new Card(this, picVector.get(i));
            cardVector.add(i, bt);
            gridLayout.addView(cardVector.get(i));
            cardVector.get(i).setOnClickListener(new MyOnClickListener(cardVector.get(i)));

        }

    }

    //A listener to handle a card being clicked.
    class MyOnClickListener implements View.OnClickListener {
        private Card card;

        public MyOnClickListener(Card card) {
            this.card = card;
        }

        @Override
        public void onClick(View view) {
            if (!card.getIsTurned()) {
                counter++;
                card.turn();
            }
            checkEqual(card);

        }

        // A method to check if there's a match between two cards.
        private void checkEqual(Card card) {
            //If counter is odd - it's the first card.
            if (counter % 2 != 0) {
                card1 = card;
                //else - it's the second.
            } else
                card2 = card;
            if (card1 != null && card2 != null) {
                freeCards(false);

                if (card1.getIsTurned() && card2.getIsTurned()) {
                    // If there's a match - disable any click options and check if the game is over.
                    if (card1.equals(card2)) {
                        card1.setIsMatch(true);
                        card2.setIsMatch(true);
                        card1.setEnabled(false);
                        card2.setEnabled(false);
                        card1.setClickable(false);
                        card2.setClickable(false);
                        card1 = null;
                        card2 = null;
                        checkGameOver();
                        freeCards(true);
                        // If there's no match - turn the cards over and enable clicking.
                    } else {
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                card1.turn();
                                card2.turn();
                                card1 = null;
                                card2 = null;
                                freeCards(true);

                            }
                        }, DELAY_TIME / 2);

                    }
                }
            }
        }

        // A method to check if the game is done.
        private void checkGameOver() {
            for (int i = 0; i < cardVector.size(); i++) {
                if (!cardVector.get(i).getIsMatch())
                    return;
            }
            countDownTimer.cancel();
            // Message for the player if the game is won.
            Toast.makeText(GameScreen.this, "GREAT JOB!", Toast.LENGTH_SHORT).show();
            freeCards(false);
            handler.postDelayed(new Runnable() {
                public void run() {
                    mp.stop();
                    finish();
                }
            }, DELAY_TIME);
        }
    }

    // A method to handle the case whether to enable clicking on the cards.
    private void freeCards(boolean value) {
        for (int i = 0; i < cardVector.size(); i++) {
            if (!cardVector.get(i).getIsMatch()) {
                cardVector.get(i).setEnabled(value);
                cardVector.get(i).setClickable(value);
            }

        }
    }

    // A method to handle the case where the player presses the back button.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
        mp.stop();
        finish();
    }
}
