package Mancala.GUI;

import Mancala.Client.Controller;
import Mancala.Communication.GameState;

import Mancala.GUI.Models.EndGame;
import Mancala.GUI.Models.Pit;
import Mancala.GUI.Models.Store;

import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameBoardPanel extends JPanel {

    private Controller controller;
    private Store yourStore;
    private Store opponentsStore;

    private ImageIcon game_Board_Icon = new ImageIcon(this.getClass().getResource("/mancala_GB_Background.png"));
    private ImageIcon game_Board_Title_Icon = new ImageIcon(this.getClass().getResource("/GB_Title.png"));
    private ImageIcon yourTurnIcon = new ImageIcon(this.getClass().getResource("/Your_Turn.png"));
    private ImageIcon waitingForOpponentIcon = new ImageIcon(this.getClass().getResource("/Waiting_For_Opponet.png"));

    private Pit[] pits = new Pit[12];
    private EndGame endGamePanel;
    private JLabel bgLabel;
    private JLabel yourTurn;
    private JLabel waitingForOpponent;
    private JLabel title;
    private boolean boardActive = false;


    public GameBoardPanel(Controller controller) throws IOException {
        this.controller = controller;
        this.setLayout(null);


        this.title = new JLabel();
        this.title.setBounds(175, 34, 450, 100);
        this.title.setIcon(game_Board_Title_Icon);
        this.add(title);

        this.yourTurn = new JLabel();
        this.yourTurn.setBounds(175, 460, 450, 100);
        this.yourTurn.setIcon(yourTurnIcon);
        this.yourTurn.setVisible(false);
        this.add(yourTurn);


        this.waitingForOpponent = new JLabel();
        this.waitingForOpponent.setBounds(175, 460, 450, 100);
        this.waitingForOpponent.setIcon(waitingForOpponentIcon);
        this.waitingForOpponent.setVisible(true);
        this.add(waitingForOpponent);


        this.pits[0] = new Pit(117, 310, false, controller, 0);
        this.pits[1] = new Pit(214, 310, false, controller, 1);
        this.pits[2] = new Pit(312, 310, false, controller, 2);
        this.pits[3] = new Pit(410, 310, false, controller, 3);
        this.pits[4] = new Pit(508, 310, false, controller, 4);
        this.pits[5] = new Pit(604, 310, false, controller, 5);

        this.pits[6] = new Pit(604, 190, true, controller);
        this.pits[7] = new Pit(508, 190, true, controller);
        this.pits[8] = new Pit(410, 190, true, controller);
        this.pits[9] = new Pit(312, 190, true, controller);
        this.pits[10] = new Pit(214, 190, true, controller);
        this.pits[11] = new Pit(117, 190, true, controller);

        for (Pit pit : this.pits) {
            pit.setVisible(false);
            this.add(pit);
        }

        this.opponentsStore = new Store("", 33, 135, true);
        this.yourStore = new Store("", 690, 135, false);

        this.opponentsStore.setVisible(false);
        this.yourStore.setVisible(false);

        this.add(this.opponentsStore);
        this.add(this.yourStore);

        this.endGamePanel = new EndGame(this.controller);
        this.endGamePanel.setVisible(false);
        this.add(endGamePanel);


        this.bgLabel = new JLabel();
        this.bgLabel.setBounds(0, 0, 800, 600);
        this.bgLabel.setIcon(game_Board_Icon);
        this.add(bgLabel);

    }


    public void updateState(GameState gameState) {
        if (!gameState.isWaitingForOpponent()) {
            this.activateBoard();
        }

        int[] yourPits = gameState.getYourPits();
        int[] opponentsPits = gameState.getOpponentsPits();


        for (int i = 0; i < yourPits.length; i++) {
            this.pits[i].update(yourPits[i]);

            if (!gameState.isYourTurn() || gameState.isGameOver() || yourPits[i] == 0) {
                this.pits[i].disableButton();
            } else {
                this.pits[i].enableButton();
            }
        }

        for (int i = 0; i < opponentsPits.length; i++) {
            this.pits[i + 6].update(opponentsPits[i]);
        }

        this.yourStore.update(gameState.getYourStore(), gameState.getYourLabel());
        this.opponentsStore.update(gameState.getOpponentsStore(), gameState.getOpponentsLabel());

        yourTurn.setVisible(gameState.isYourTurn());

        if (gameState.isGameOver()) {
            this.endGamePanel.setVisible(true);

            if (gameState.isYouWin()) {
                this.endGamePanel.setStatusIcon(0);
            } else {
                this.endGamePanel.setStatusIcon(1);
            }

            if (gameState.isTie()) {
                this.endGamePanel.setStatusIcon(2);
            }

        }
    }

    private void activateBoard() {
        if (!this.boardActive) {
            this.waitingForOpponent.setVisible(false);

            for (Pit pit : this.pits) {
                pit.setVisible(true);
            }

            this.opponentsStore.setVisible(true);
            this.yourStore.setVisible(true);

            this.boardActive = true;
        }
    }
}