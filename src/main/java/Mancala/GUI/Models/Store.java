package Mancala.GUI.Models;

import javax.swing.*;
import java.awt.*;


public class Store extends JPanel {

    private static ImageIcon[] stoneImages = {
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_00.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_01.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_02.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_03.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_04.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_05.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_06.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_07.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_08.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_09.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_10.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_11.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_12.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_13.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_14.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_15.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_16.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_17.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_18.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_19.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_20.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_21.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_22.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_23.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_24.png")),
            new ImageIcon(Pit.class.getResource("/Player_Mancala_Stones_25.png")),
    };
    private JLabel labelNote;
    private JLabel label;
    private JLabel score;
    private JLabel img;
    private Font fontLabel;

    public Store(String name, int x, int y, boolean leftJustify) {
        super();
        this.setBounds(x, y, 75, 328);
        this.setLayout(null);
        this.setOpaque(false);

        this.label = new JLabel(name);
        this.label.setOpaque(false);
        this.label.setForeground(new Color(96, 48, 22));
        this.label.setHorizontalAlignment(SwingConstants.CENTER);
        this.label.setVerticalAlignment(SwingConstants.CENTER);
        this.label.setFont(new Font("Times New Roman", Font.BOLD, 20));


        this.score = new JLabel("0");
        this.score.setOpaque(false);
        this.score.setForeground(Color.WHITE);
        this.score.setBackground(null);
        this.score.setHorizontalAlignment(SwingConstants.CENTER);
        this.score.setVerticalAlignment(SwingConstants.CENTER);


        this.img = new JLabel();
        this.img.setOpaque(false);
        this.img.setBackground(null);

        if (leftJustify) {
            this.label.setBounds(0, 20, 75, 32);
            this.img.setBounds(0, 56, 75, 200);
            this.score.setBounds(53, 55, 20, 15);
        } else {

            this.labelNote = new JLabel("(You)");
            this.labelNote.setOpaque(false);
            this.labelNote.setForeground(new Color(96, 48, 22));
            this.labelNote.setHorizontalAlignment(SwingConstants.CENTER);
            this.labelNote.setVerticalAlignment(SwingConstants.CENTER);
            this.labelNote.setFont(new Font("Times New Roman", Font.BOLD, 14));
            this.labelNote.setBounds(0, 300, 75, 32);
            this.add(this.labelNote);

            this.label.setBounds(0, 280, 75, 32);
            this.img.setBounds(0, 56, 75, 200);
            this.score.setBounds(2, 260, 20, 15);
        }

        this.update(0, name);

        this.add(this.label);
        this.add(this.img);
        this.add(this.score);
    }

    public void update(int score, String name) {
        this.label.setText(name);
        this.score.setText(score + "");
        ImageIcon img;

        if (score >= stoneImages.length) {
            img = stoneImages[stoneImages.length - 1];
        } else {
            img = stoneImages[score];
        }

        if (img == null) {
            this.img.setVisible(false);
        } else {
            this.img.setIcon(img);
            this.img.setVisible(true);
        }
    }
}
