package com.gruppo1.battaglianavale.Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    private Socket playerSocket;
    private BufferedReader input;
    private PrintWriter output;

    public PlayerHandler(Socket socket) {
        this.playerSocket = socket;
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
                System.out.println("Messaggio dal giocatore: " + message);

                // Esempio di risposta al giocatore
                output.println("Risposta dal server: Messaggio ricevuto.");
            }
            // Implementa la logica di gestione della connessione del giocatore qui
            // Ad esempio, ricevi e invia messaggi tra i giocatori per coordinare la partita
            // Puoi implementare la logica del gioco della battaglia navale qui

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
}
