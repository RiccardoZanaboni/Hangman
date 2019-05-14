package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectedClient  extends Thread  {

        private Socket socket ;
        private BufferedReader in ;
        private BufferedReader console ;
        private PrintWriter out ;
        private String s;
        private String a;

    public ConnectedClient() throws IOException{
        this.socket = new Socket("10.87.219.185", 8888);;
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.console = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public static void main(String[] args) {
        try{
            Thread t1=new ConnectedClient();
            t1.run();
        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public void run() {
        try {
            do {
                switch (a = console.readLine()) {
                    case ("FAILED"):
                        printBanner("Hai perso!  La parola da indovinare era '" +
                                console.readLine() + "'");
                        break;
                    case ("SOLVED"):
                        printBanner("Hai indovinato!   (" + console.readLine() + ")");
                        break;
                    case ("OPEN"):
                        System.out.print("\n" + console.readLine() + " tentativi rimasti\n");
                        System.out.println(gameRepresentation(Integer.parseInt(console.readLine())));
                        System.out.println(console.readLine());
                        System.out.print("Inserisci una lettera: ");
                        s = in.readLine();
                        out.println(s);
                        break;
                }
            } while (!a.equals("FAILED") && !a.equals("SOLVED"));
            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String gameRepresentation(int a) {
        String s = "   ___________\n  /       |   \n  |       ";
        s += (a == 0 ? "\n" : "O\n");
        s += "  |     " + (a == 0 ? "\n" : (a < 5
                ? "  +\n"
                : (a == 5 ? "--+\n" : "--+--\n")));
        s += "  |       " + (a < 2 ? "\n" : "|\n");
        s += "  |      " + (a < 3 ? "\n" : (a == 3 ? "/\n" : "/ \\\n"));
        s += "  |\n================\n";
        return s;
    }

    private static void printBanner(String message) {
        System.out.println("");
        for (int i = 0; i < 80; i++)
            System.out.print("*");
        System.out.println("\n***  " + message);
        for (int i = 0; i < 80; i++)
            System.out.print("*");
        System.out.println("\n");
    }
}

