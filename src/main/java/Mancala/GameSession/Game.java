package Mancala.GameSession;

import Mancala.Communication.GameState;
import Mancala.Server.Controller;

import java.io.EOFException;
import java.io.IOException;


public class Game implements Runnable {
    private final int gameId;
    private Player player1;
    private Player player2;

    public Game(int gameId, Player player1, Player player2) {
        this.gameId = gameId;
        this.player1 = player1;
        this.player2 = player2;

        this.player1.setController(this);
        this.player2.setController(this);
    }

    public void run() {
        boolean keepGameActive = true;

        this.logMessage("Starting Game:");

        this.player1.getState().setWaitingForOpponent(false);
        this.player2.getState().setWaitingForOpponent(false);

        // Start Game
        try {
            this.player1.setTurn(true);
            this.player1.sendState();
            this.player2.sendState();
        } catch (IOException e) {
            keepGameActive = false;
            this.resetConnections();
        }


        while (keepGameActive) {
            try {

                while (this.player1.isTurn()) {
                    this.logMessage("Waiting for player 1 to go");
                    keepGameActive = this.playersTurn(this.player1, this.player2);
                }
                if (!keepGameActive) {
                    continue;
                }

                while (this.player2.isTurn()) {
                    this.logMessage("Waiting for player 2 to go");
                    keepGameActive = this.playersTurn(this.player2, this.player1);
                }
                if (!keepGameActive) {
                    continue;
                }

                keepGameActive = (this.player1.isConnected() && this.player2.isConnected());

            } catch (EOFException e) {
                this.logMessage("User disconnected");
                this.resetConnections();
                keepGameActive = false;
            } catch (IOException e) {
                this.resetConnections();
                keepGameActive = false;

            } catch (ClassNotFoundException e) {

            } catch (Exception e) {
            }
        }

        this.logMessage("Game Over, disconnecting");

        try {
            this.player1.disconnect();
            this.player2.disconnect();
        } catch (IOException e) {
        }

        Controller.clearSession(this.gameId);
    }


    private boolean playersTurn(Player player, Player opponent) throws IOException, ClassNotFoundException {
        if (player.isConnected()) {
            if (opponent.isConnected()) {
                GameState opponentsState = player.waitForMove();

                player.sendState();


                if (opponent.isConnected()) {
                    opponent.setState(opponentsState)
                            .sendState();
                }
            } else {
                player.sendOpponentLeftState();
                return false;
            }
        }
        return true;
    }


    public void resetConnections() {
        try {
            this.player1.sendOpponentLeftState();
        } catch (IOException e1) {
        }

        try {
            this.player2.sendOpponentLeftState();
        } catch (IOException e1) {
        }
    }

    public void logMessage(String msg) {
        System.out.println("   [game " + this.gameId + "] " + msg);
    }

}
