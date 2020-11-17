package com.example.hw1_war_roeeaviran316492644;

public class WarCard{// Class for implementing card ID and value
    // Variables
    final private int card_ID;
    final private int card_Value;

    // Constructor
    public WarCard(int card_ID, int card_Value) {
        this.card_ID = card_ID;
        this.card_Value = card_Value;
    }

    public int getCard_ID() {
        return card_ID;
    }// Get card ID

    public int getCard_Value() {
        return card_Value;
    }// Get card value
}
