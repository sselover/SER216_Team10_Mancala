package Mancala.GUI.Models;

import Mancala.Client.Controller;
import Mancala.Communication.GameEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Pit extends JPanel {

    private static ImageIcon[] stoneImages = {
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_00.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_01.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_02.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_03.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_04.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_05.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_06.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_07.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_08.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_09.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_10.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_11.png")),
            new ImageIcon(Pit.class.getResource("/Mancala_Stones_12.png")),
    };
    private Controller controller;
    private int buttonId;


    private JLabel label;
    private JButton button = null;

    public Pit(int x, int y, boolean labelBellow, Controller c, int bid) {
        this(x, y, labelBellow, c);
        this.buttonId = bid;

        this.button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.sendToServer(new GameEvent(buttonId));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public Pit(int x, int y, boolean labelBellow, Controller controller) {
        this.controller = controller;
        this.setBounds(x, y, 80, 109);
        this.setLayout(null);
        this.setOpaque(false);

        this.button = new JButton();
        this.button.setLayout(null);
        this.button.setBackground(null);
        this.button.setContentAreaFilled(false);
        this.button.setBorderPainted(false);
        this.button.setOpaque(false);
        this.button.setEnabled(false);

        this.label = new JLabel("0");
        this.label.setLayout(null);
        this.label.setBackground(null);
        this.label.setOpaque(false);
        this.label.setForeground(Color.WHITE);

        if (labelBellow) {
            this.button.setBounds(0, 0, 80, 80);
            this.label.setBounds(30, 85, 20, 15);
        } else {
            this.button.setBounds(0, 23, 80, 80);
            this.label.setBounds(30, 3, 20, 15);
        }


        add(this.button);
        add(this.label);
        this.update(0);
    }


    public void update(int num) {
        this.label.setText(num + "");
        ImageIcon img;

        if (num >= stoneImages.length) {
            img = stoneImages[stoneImages.length-1];
        } else {
            img = stoneImages[num];
        }

        if (img == null) {
            this.button.setVisible(false);
        } else {
            this.button.setIcon(img);
            this.button.setDisabledIcon(img);
            this.button.setVisible(true);
        }

    }

    public void enableButton() {
        this.button.setEnabled(true);
    }

    public void disableButton() {
        this.button.setEnabled(false);
    }
}
