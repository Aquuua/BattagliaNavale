package com.gruppo1.battaglianavale.Communication;

import com.badlogic.gdx.Game;
import com.gruppo1.battaglianavale.GameLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {


    private String name;
    private Socket socket;
    private GameLogic gameLogic;
    private BufferedReader input;
    private PrintWriter output;

    // Inizializza la connessione al server nel costruttore se necessario
    public Client(String SERVER_ADDRESS, int SERVER_PORT, GameLogic gameLogic) {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connesso al server.");

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            this.gameLogic = gameLogic;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo per interagire con il server (es. inviare e ricevere messaggi)
    private void interactWithServer() {
        try {
            // Implementa la logica del giocatore qui
            // Ad esempio, invia e ricevi messaggi per coordinare le mosse del giocatore

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String userInputMessage;

            while (true) {
//                // Leggi l'input del giocatore
//                System.out.print("Inserisci un messaggio per il server: ");
//                userInputMessage = userInput.readLine();
//
//                // Invia il messaggio al server
//                output.println(userInputMessage);

                // Ricevi e visualizza la risposta dal server
                String messageFromServer = input.readLine();

                switch (messageFromServer){
                    case "inizia":
                        gameLogic.iniziaPartita();
                        break;
                    case "entrati":
                        gameLogic.iniziaTimer();
                        break;
                    case "perso":
                        gameLogic.vittoria();
                        break;
                    default:
                        if(messageFromServer.contains("attacco")){
                            // Dividere la stringa in base agli spazi
                            String[] parti = messageFromServer.split(" ");

                            // Estrarre i due valori come stringhe
                            String valore1Stringa = parti[1];
                            String valore2Stringa = parti[2];

                            // Convertire le stringhe in interi
                            int x = Integer.parseInt(valore1Stringa);
                            int y = Integer.parseInt(valore2Stringa);
                            System.out.println("Sei stato attaccato.");
                            gameLogic.colpito(x,y);

                        }
                        break;
                }
                System.out.println("Messaggio dal server: " + messageFromServer);

                // Esci dal loop se necessario
//                if ("exit".equalsIgnoreCase(userInputMessage)) {
//                    break;
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Metodo per chiudere la connessione al server
    public void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                output.println("perso");
                socket.close();

                System.out.println("Connessione al server chiusa.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        interactWithServer();
    }

    public void ready(){
        output.println("pronto");
    }

    public void attack(int x, int y){
        output.println("attacco "+x+" "+y);
        System.out.println("Stai attaccando");
    }



}
