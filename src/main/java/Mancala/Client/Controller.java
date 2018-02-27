package Mancala.Client;

import Mancala.Communication.*;
import Mancala.GUI.*;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Controller implements Runnable {

    private JFrame frame;
    private GameBoardPanel gbp;
    private StartPanel panelStart;
    private InstructionsPanel ip;
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    private boolean reconnect = false;
    private boolean disconnect = false;
    private String disconnectMsg;
    private Socket socket;

    public Controller() {
        try {
            // ------------------------ GUI STUFF ------------------------------//

            this.frame = new JFrame("Mancala");
            this.frame.setLocationRelativeTo(null);
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //-------- Adding components to the JFrame ------------//

            // Adds the MenuBar to the frame
            this.frame.setSize(800, 600);
            this.frame.setLocationRelativeTo(null);
            this.frame.setResizable(false);


            // Contains all the JFrame attributes.
            this.panelStart = new StartPanel(this);
            this.gbp = new GameBoardPanel(this);
            this.ip = new InstructionsPanel(this);

            // ----------------------//

            //Adds the StartPanel to the frame
            this.frame.getContentPane().add(panelStart);
            this.frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // -----------------------------------------------------------------//
    }

    public JFrame getFrame() {
        return this.frame;
    }


    private void showPanel(JPanel panel) {
        this.getFrame().getContentPane().removeAll();
        this.getFrame().getContentPane().add(panel);
        this.getFrame().invalidate();
        this.getFrame().validate();
        this.getFrame().repaint();
    }

    public void showGameBoard() {
        this.showPanel(this.gbp);
    }

    public void showStartScreen() {
        this.panelStart.removeError();
        this.showPanel(this.panelStart);
    }

    public void showStartScreen(String errorMsg) {
        this.showStartScreen();
        this.panelStart.showError(errorMsg);
    }

    public void showStartScreen(int errorMsg) {
        this.showStartScreen();
        this.panelStart.showError(errorMsg);
    }

    public void showInstructionScreen() {
        this.showPanel(this.ip);
    }

    public boolean connectToServer() {
        try {
            socket = new Socket("localhost", 8000);

            this.fromServer = new ObjectInputStream(socket.getInputStream());
            this.toServer = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("connected");

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    try {
                        fromServer.close();
                        toServer.close();
                        socket.close();
                        System.out.println("Closed Connection");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }));

            Thread thread = new Thread(this);
            thread.start();

            return true;

        } catch (EOFException e) {

        } catch (IOException e) {

        } catch (Exception ex) {

        }

        return false;
    }

    public void sendToServer(GameEvent event) throws IOException {
        System.out.println(" -- Sending info to server");
        this.toServer.reset();
        this.toServer.writeObject(event);
    }

    public void run() {
        this.disconnectMsg = null;
        boolean keepPlaying = true;
        while (keepPlaying) {
            // listen for server info
            try {
//                System.out.println(" -- Waiting for server to send info");
                GameState gameState = (GameState) this.fromServer.readObject();
//
//                System.out.println(" - New State:");
//                System.out.print("  * Your Label: ");
//                System.out.println(gameState.getYourLabel());
//
//                System.out.print("  * Your Store: ");
//                System.out.println(gameState.getYourStore());
//
//                System.out.print("  * Your Pits: ");
//                System.out.println(Arrays.toString(gameState.getYourPits()));
//
//                System.out.print("  * Opponents Label: ");
//                System.out.println(gameState.getOpponentsLabel());
//
//                System.out.print("  * Opponents Store: ");
//                System.out.println(gameState.getOpponentsStore());
//
//                System.out.print("  * Opponents Pits: ");
//                System.out.println(Arrays.toString(gameState.getOpponentsPits()));
//
//                System.out.print("  * Your Turn: ");
//                System.out.println(gameState.isYourTurn());
//
//                System.out.print("  * Is Game Over: ");
//                System.out.println(gameState.isGameOver());
//
//                System.out.print("  * Opponent Left: ");
//                System.out.println(gameState.isOpponentLeft());
//
//                System.out.print("  * You Won: ");
//                System.out.println(gameState.isYouWin());

                keepPlaying = this.updateState(gameState);
            } catch (EOFException e) {
                reconnect = true;
                keepPlaying = false;
            } catch (IOException e) {
                reconnect = true;
                keepPlaying = false;
            } catch (ClassNotFoundException e) {
            }
        }

        try {
            this.fromServer.close();
            this.toServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (reconnect) {
            reconnect = false;
            this.playAgain();
        } else if (disconnect) {
            disconnect = false;
            if (this.disconnectMsg != null) {
                this.showStartScreen(this.disconnectMsg);
            } else {
                this.showStartScreen(0);
            }
            this.resetBoard();
        }
    }

    public void resetBoard() {
        try {
            this.gbp = new GameBoardPanel(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playAgain() {
        this.resetBoard();
        if (this.connectToServer()) {
            this.showGameBoard();
        } else {
            this.showStartScreen(0);
        }
    }

    private boolean updateState(GameState gameState) {
        boolean keepPlaying = true;

        if (gameState.isOpponentLeft()) {
            keepPlaying = false;
            disconnect = true;
            disconnectMsg = "Your opponent left the game. Click 'Start' to play someone else";
        } else {
            if (gameState.isGameOver()) {
                keepPlaying = false;
            }

            this.gbp.updateState(gameState);
            this.frame.validate();
            this.frame.repaint();
        }


        return keepPlaying;
    }

}
