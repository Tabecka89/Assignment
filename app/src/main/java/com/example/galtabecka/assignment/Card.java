package com.example.galtabecka.assignment;

import android.content.Context;

public class Card extends android.support.v7.widget.AppCompatImageButton {

    //Declaring variables.
    private int cardId = 0;
    private boolean isTurned = false;
    private boolean isMatch = false;

    // Card contructor.
    public Card(Context context, int picId) {
        super(context);
        this.cardId = picId;
        super.setImageResource(R.drawable.back_of_card);
    }

    // A method to handle a card being turned.
    public void turn() {
        isTurned = !isTurned;
        if (!isTurned)
            setImageResource(R.drawable.back_of_card);
        else
            setImageResource(cardId);
    }

    public boolean getIsTurned() {
        return isTurned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return cardId == card.cardId;
    }

    public void setIsMatch(boolean isMatch) {
        this.isMatch = isMatch;
    }

    public boolean getIsMatch() {
        return isMatch;
    }
}