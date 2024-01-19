package com.gruppo1.battaglianavale.Communication;

import com.gruppo1.battaglianavale.GameLogic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
//TODO RENDILO TRID
    private static final int PORT = 5959;

    private ArrayList<PlayerHandler> clients = new ArrayList<>();

    private boolean gameStarted;

    int pronto;



    public Server(){
        this.gameStarted = false;
    }

    public void esegui() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server avviato. In attesa di giocatori...");

            while (true) {
                Socket playerSocket = serverSocket.accept();
                System.out.println("Giocatore connesso.");

                // Crea un thread separato per gestire la connessione del giocatore
                PlayerHandler playerHandler = new PlayerHandler(playerSocket,this);
                Thread thread = new Thread(playerHandler);
                thread.start();

                clients.add(playerHandler);

                if (clients.size() >= 2) {
                    System.out.println("Due giocatori sono connessi. Inizio della partita.");
                    this.broadcastMessage("entrati");

                    break;
                }
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message){
        for (PlayerHandler client : clients) {
            client.tellClient(message);
        }
    }

    public void singleMessage(String message, PlayerHandler playerHandler){
        for (PlayerHandler client : clients) {
            if(client != playerHandler){
                client.tellClient(message);
            }
        }
    }
    @Override
    public void run() {
        esegui();
    }


}