package Mancala.Communication;

import java.io.Serializable;


public class GameEvent implements Serializable {
    public int buttonPressed;

    public GameEvent(int buttonId) {
        this.buttonPressed = buttonId;
    }
}
