package com.gruppo1.battaglianavale.Communication;

import com.gruppo1.battaglianavale.GameLogic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
//TODO RENDILO TRID
    private static final int PORT = 5959;
    private static int playerCount = 0;



    public Server(){

    }

    public void esegui() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server avviato. In attesa di giocatori...");

            while (true) {
                Socket playerSocket = serverSocket.accept();
                System.out.println("Giocatore connesso.");

                // Crea un thread separato per gestire la connessione del giocatore
                PlayerHandler playerHandler = new PlayerHandler(playerSocket);
                Thread thread = new Thread(playerHandler);
                thread.start();

                playerCount++;

                if (playerCount >= 2) {
                    System.out.println("Due giocatori sono connessi. Inizio della partita.");

                    break;
                }
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        esegui();
    }
}