package Mancala.GUI;

import Mancala.Client.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Contains all the content for the start Screen. 


public class StartPanel extends JPanel {

    private ImageIcon title_Panel_Icon = new ImageIcon(this.getClass().getResource("/Title_Panel_BG_01.png"));
    private ImageIcon start_Icon = new ImageIcon(this.getClass().getResource("/Start_Button.png"));
    private ImageIcon exit_Icon = new ImageIcon(this.getClass().getResource("/Exit_Button.png"));
    private ImageIcon instructions_Icon = new ImageIcon(this.getClass().getResource("/Instructions_Button.png"));

    private ImageIcon start_Rollover_Icon = new ImageIcon(this.getClass().getResource("/Start_Rollover_Button.png"));
    private ImageIcon instructions_Rollover_Icon = new ImageIcon(this.getClass().getResource("/Instructions_Rollover_Button.png"));
    private ImageIcon exit_Rollover_Icon = new ImageIcon(this.getClass().getResource("/Exit_Rollover_Button.png"));


    private static final long serialVersionUID = 1L;

    public static JButton startButton;
    public static JLabel startBGLabel;
    public static JButton instructionsButton;
    public static JButton exitButton;
    private final Controller controller;
    public String errorMsg;
    private JLabel errorLabel;
    


    public StartPanel(Controller c) {
        final StartPanel panel = this;
        this.controller = c;
        this.setLayout(null);

        startButton = new JButton("Start");
        startButton.setOpaque(true);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setBounds(265, 220, 300, 75);
        startButton.setIcon(start_Icon);
        startButton.setRolloverIcon(start_Rollover_Icon);
        add(startButton);

        instructionsButton = new JButton("Instructions");
        instructionsButton.setOpaque(true);
        instructionsButton.setContentAreaFilled(false);
        instructionsButton.setBorderPainted(false);
        instructionsButton.setBounds(170, 320, 500, 75);
        instructionsButton.setIcon(instructions_Icon);
        instructionsButton.setRolloverIcon(instructions_Rollover_Icon);

        add(instructionsButton);


        exitButton = new JButton("Exit");
        exitButton.setOpaque(true);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setBounds(265, 420, 300, 75);
        exitButton.setIcon(exit_Icon);
        exitButton.setRolloverIcon(exit_Rollover_Icon);
        add(exitButton);


        Image title_Panel_Scaled = title_Panel_Icon.getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT);
        ImageIcon title_Panel_Icon = new ImageIcon(title_Panel_Scaled);


        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        startBGLabel = new JLabel();
        startBGLabel.setBounds(0, 0, 800, 600);
        startBGLabel.setIcon(title_Panel_Icon);
        add(startBGLabel);

        
        

        //This provides the action for the start button.
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Removes the startFrame and replaces it to the Game board.
                if (controller.connectToServer()) {
                    removeError();
                    controller.showGameBoard();
                } else {
                    controller.showStartScreen(0);
                }
            }
        });

        instructionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Removes the startFrame and replaces it to the InstructionsPanel.
                removeError();
                controller.showInstructionScreen();
            }
        });
        

    }

    public void removeError() {
        if (this.errorLabel != null) {
            remove(this.errorLabel);
            this.controller.getFrame().repaint();
        }
    }

    public void showError(String errorMsg) {
        if (this.errorLabel != null) {
            this.remove(this.errorLabel);
        }

        this.errorLabel = new JLabel(errorMsg);
        this.errorLabel.setOpaque(true);
        this.errorLabel.setBackground(new Color(0, 0, 0, 190));
        this.errorLabel.setForeground(new Color(255, 96, 109));
        this.errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.errorLabel.setVerticalAlignment(SwingConstants.CENTER);
        this.errorLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        this.errorLabel.setBounds(0, 190, 800, 32);
        this.add(this.errorLabel);
        this.setComponentZOrder(this.errorLabel, 0);

        controller.getFrame().repaint();

    }

    public void showError(int errorMsg) {
        switch (errorMsg) {
            case 0:
                showError("The server is not responding, please try again later");
                break;
            default:
                showError("Error [" + errorMsg + "]");
                break;
        }
    }
}