package Mancala.Communication;

import java.io.Serializable;


public class GameState implements Serializable {
    private String yourLabel = "You";
    private String opponentsLabel = "Opponent";
    private int yourStore = 0;
    private int[] yourPits = {4, 4, 4, 4, 4, 4};
    private int opponentsStore = 0;
    private int[] opponentsPits = {4, 4, 4, 4, 4, 4};
    private boolean youWin = false;
    private boolean yourTurn = false;
    private boolean gameOver = false;
    private boolean opponentLeft = false;
    private boolean isTie = false;
    private boolean waitingForOpponent = true;

    public GameState(String yourLabel, String opponentsLabel) {
        this.yourLabel = yourLabel;
        this.opponentsLabel = opponentsLabel;
    }

    public GameState(String yourLabel, int yourStore, int[] yourPits, String opponentsLabel, int opponentsStore, int[] opponentsPits) {
        this(yourLabel, opponentsLabel);
        this.yourStore = yourStore;
        this.yourPits = yourPits;
        this.opponentsStore = opponentsStore;
        this.opponentsPits = opponentsPits;
    }

    public GameState setOpponentLeft(boolean opponentLeft) {
        this.opponentLeft = opponentLeft;
        return this;
    }

    public GameState setIsTie(boolean isTie) {
        this.isTie = isTie;
        return this;
    }

    public GameState setGameOver() {
        this.setYourTurn(false);
        this.gameOver = true;
        return this;
    }

    public GameState setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
        return this;
    }

    public GameState setYouWin(boolean yourTurn) {
        this.youWin = yourTurn;
        return this;
    }

    public GameState setYourStore(int yourStore) {
        this.yourStore = yourStore;
        return this;
    }

    public GameState setYourPits(int[] yourPits) {
        this.yourPits = yourPits;
        return this;
    }

    public GameState setOpponentsPits(int[] opponentsPits) {
        this.opponentsPits = opponentsPits;
        return this;
    }

    public GameState setOpponentsStore(int opponentsStore) {
        this.opponentsStore = opponentsStore;
        return this;
    }

    public GameState setWaitingForOpponent(boolean waitingForOpponent) {
        this.waitingForOpponent = waitingForOpponent;
        return this;
    }

    public String getYourLabel() {
        return this.yourLabel;
    }

    public String getOpponentsLabel() {
        return this.opponentsLabel;
    }

    public int getYourStore() {
        return this.yourStore;
    }

    public int[] getYourPits() {
        return this.yourPits;
    }

    public int getOpponentsStore() {
        return this.opponentsStore;
    }

    public int[] getOpponentsPits() {
        return this.opponentsPits;
    }

    public boolean isYouWin() {
        return this.youWin;
    }

    public boolean isYourTurn() {
        return this.yourTurn;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public boolean isTie() {
        return this.isTie;
    }

    public boolean isOpponentLeft() {
        return this.opponentLeft;
    }

    public boolean isWaitingForOpponent() {
        return this.waitingForOpponent;
    }


    public GameState generateOpponentsState() {
        return new GameState(this.getOpponentsLabel(), this.opponentsStore, this.opponentsPits, this.getYourLabel(), this.yourStore, this.yourPits);
    }

}
