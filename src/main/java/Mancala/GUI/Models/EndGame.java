package Mancala.GUI.Models;

import Mancala.Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndGame extends JPanel {

    private ImageIcon background_Icon = new ImageIcon(this.getClass().getResource("/End_Panel_BG.png"));
    private ImageIcon win_Icon = new ImageIcon(this.getClass().getResource("/You_Win.png"));
    private ImageIcon lose_Icon = new ImageIcon(this.getClass().getResource("/You_Lose.png"));
    private ImageIcon tie_Icon = new ImageIcon(this.getClass().getResource("/You_Tied.png"));
    private ImageIcon play_Again_Icon = new ImageIcon(this.getClass().getResource("/Play_Again.png"));
    private ImageIcon play_Again_Hover_Icon = new ImageIcon(this.getClass().getResource("/Play_Again_Hover.png"));
    private ImageIcon exit_Icon = new ImageIcon(this.getClass().getResource("/Final_Exit_Image.png"));
    private ImageIcon exit_Hover_Icon = new ImageIcon(this.getClass().getResource("/Final_Exit_Image_Hover.png"));

    private Controller controller;


    private JLabel winLoseLabel;
    public JButton playAgainButton;

    public EndGame(Controller ctr) {
        this.controller = ctr;

        this.setBounds(0, 423, 800, 177);
        this.setLayout(null);
        this.setOpaque(false);


        this.winLoseLabel = new JLabel();
        this.winLoseLabel.setBounds(230, 15, 341, 75);
        this.winLoseLabel.setIcon(win_Icon);
        add(winLoseLabel);

        this.playAgainButton = new JButton();
        this.playAgainButton.setBounds(300, 90, 202, 46);
        this.playAgainButton.setBackground(null);
        this.playAgainButton.setContentAreaFilled(false);
        this.playAgainButton.setBorderPainted(false);
        this.playAgainButton.setOpaque(false);
        this.playAgainButton.setRolloverIcon(play_Again_Hover_Icon);
        this.playAgainButton.setIcon(play_Again_Icon);
        add(playAgainButton);

        this.playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.playAgain();
            }
        });


    }

    public void setStatusIcon(int winLoseTie) {
        if (winLoseTie == 0) {

            this.winLoseLabel.setIcon(win_Icon);

        } else if (winLoseTie == 1) {

            this.winLoseLabel.setIcon(lose_Icon);

        } else {

            this.winLoseLabel.setIcon(tie_Icon);

        }
    }
}
