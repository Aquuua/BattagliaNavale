package com.gruppo1.battaglianavale.Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler implements Runnable {
//Classe che gestisce la comunicazione tra client e server
    private Socket playerSocket;
    private BufferedReader input;
    private PrintWriter output;
    private Server server;

    public PlayerHandler(Socket socket, Server server) {
        this.playerSocket = socket;
        this.server = server;
        try {
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = input.readLine()) != null) {
                // Processa il messaggio ricevuto
                switch (message){
                    case "pronto":
                        giocatorePronto();
                        break;
                    default:
                        attacco(message);
                        break;

                }
                System.out.println("Messaggio dal giocatore: " + message);

                // Esempio di risposta al giocatore

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                playerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //comunica con un solo client
    public void tellClient(String message){
        output.println(message);
    }
    public void giocatorePronto(){
        server.pronto = server.pronto + 1;
        if(server.pronto ==2){
            server.broadcastMessage("inizia");

        }
    }

    public void attacco(String message){
        server.singleMessage(message,this);
    }
}
