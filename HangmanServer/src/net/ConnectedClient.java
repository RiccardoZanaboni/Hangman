package net;

import hangman.Hangman;
import hangman.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectedClient  extends Thread  {

        private Socket socket ;
    public ConnectedClient(Socket socket) throws IOException{
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            Hangman game = new Hangman();
            Player player = new NetworkPlayer(socket);
            game.playGame(player);
        }catch (Exception e){
            e.getMessage();
        }
    }

}

