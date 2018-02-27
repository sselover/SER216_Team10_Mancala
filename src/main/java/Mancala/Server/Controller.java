package Mancala.Server;

import Mancala.Communication.GameState;
import Mancala.GameSession.Game;
import Mancala.GameSession.Player;
import Mancala.Utilites.ServerSocket;

import java.io.IOException;
import java.util.*;



public class Controller {

    private static Map<Integer, Thread> gameSessions = new HashMap<Integer, Thread>();

    public Controller() {
        int sessionCount = 0;
        try {

            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);

            // Ready to create a session for every two players
            while (true) {
                System.out.println("Waiting for two players");

                // Connect to player 1
                Player player1 = serverSocket.acceptPlayer();
                player1.setState(new GameState("Player 1", "Player 2"));
                player1.sendState();
                player1.monitorConnection();

                // Connect to player 2
                Player player2 = serverSocket.acceptPlayer();
                player2.setState(new GameState("Player 2", "Player 1"));
                player2.sendState();
                player2.monitorConnection();


                if (player1.isConnected() && player2.isConnected()) {
                    sessionCount++;
                    int sessionId = sessionCount;
                    System.out.println("Creating new game session [" + sessionId + "]");
                    // Create a new thread for this session of two players
                    Thread game = new Thread(new Game(sessionId, player1, player2));
                    gameSessions.put(sessionId, game);

                    // Start the new thread
                    game.start();
                } else if (!player1.isConnected() && player2.isConnected()) {
                    player2.sendOpponentLeftState();
                } else if (!player2.isConnected() && player1.isConnected()) {
                    player1.sendOpponentLeftState();
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public static void clearSession(int sessionId) {
        Controller.gameSessions.remove(sessionId);
    }
}
