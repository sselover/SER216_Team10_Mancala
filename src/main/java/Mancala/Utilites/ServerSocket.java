package Mancala.Utilites;

import Mancala.GameSession.Player;

import java.io.IOException;


public class ServerSocket extends java.net.ServerSocket {

    public ServerSocket(int i) throws IOException {
        super(i);
    }

    public Player acceptPlayer() throws IOException {
        Player socket = new Player();
        this.implAccept(socket);
        socket.createStreams();
        return socket;
    }
}
