package com.gruppo1.battaglianavale.Communication;

import com.gruppo1.battaglianavale.GameLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {



    private Socket socket;
    private GameLogic gameLogic;
    private BufferedReader input;
    private PrintWriter output;

    public Client(String SERVER_ADDRESS, int SERVER_PORT, GameLogic gameLogic) {
        // Inizializza la connessione al server nel costruttore se necessario
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
                // Leggi l'input del giocatore
                System.out.print("Inserisci un messaggio per il server: ");
                userInputMessage = userInput.readLine();

                // Invia il messaggio al server
                output.println(userInputMessage);

                // Ricevi e visualizza la risposta dal server
                String messageFromServer = input.readLine();
                System.out.println("Messaggio dal server: " + messageFromServer);

                // Aggiungi la logica del gioco qui, ad esempio, verifica se il gioco è finito

                // Esci dal loop se necessario
                if ("exit".equalsIgnoreCase(userInputMessage)) {
                    break;
                }
            }
        } catch (IOException e) {
            // Gestisci l'eccezione IOException
            e.printStackTrace();
        }
    }



    // Metodo per chiudere la connessione al server
    public void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
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
}
