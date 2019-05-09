package net;

import hangman.Game;
import hangman.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

    public class NetworkPlayer extends Player {
        BufferedReader console;
        Socket socket;

        public NetworkPlayer(Socket socket) throws IOException {
            this.socket=socket;
            console = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }

        @Override
        public char chooseLetter(Game game) {
            for (;;) {
                String line = null;
                try {
                    line = console.readLine().trim();
                } catch (IOException e) {
                    line = "";
                }
                if (line.length() == 1 && Character.isLetter(line.charAt(0))) {
                    return line.charAt(0);
                } else {
                    char i='.';
                    return i;
                }
            }
        }

        @Override
        public void update(Game game) throws IOException {
            System.out.println("provato update");
            PrintWriter outToClient = new PrintWriter(this.socket.getOutputStream(), true);
            switch(game.getResult()) {
                case FAILED:
                    outToClient.println(game.getResult());
                    outToClient.println(game.getSecretWord());
                    break;
                case SOLVED:
                    outToClient.println(game.getResult());
                    outToClient.println(game.getSecretWord());
                    break;
                case OPEN:
                    outToClient.println(game.getResult());
                    int tentativiRimasti = Game.MAX_FAILED_ATTEMPTS - game.countFailedAttempts();
                    outToClient.println(tentativiRimasti);
                    outToClient.println(game.countFailedAttempts()); // poi il client fa il disegno dell'impiccato
                    outToClient.println(game.getKnownLetters());
                    break;
            }
        }

    }
