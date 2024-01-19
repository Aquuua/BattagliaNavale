import java.io.*;
import java.net.*;

class GestorePlayer implements Runnable {
    private Socket playerSocket;
    private int playerNumber;

    public GestorePlayer(Socket socket, int playerNumber) {
        this.playerSocket = socket;
        this.playerNumber = playerNumber;
    }

    public void run() {
        try {
            // Codice per gestire la connessione del giocatore
            // Implementa la logica del gioco qui
            

            // Esempio: invia e ricevi dati tra i giocatori
            BufferedReader input = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
            PrintWriter output = new PrintWriter(playerSocket.getOutputStream(), true);

            if (playerNumber == 1) {
                output.println("Sei il giocatore 1. Attendi il secondo giocatore.");
            } else {
                output.println("Sei il giocatore 2. Il gioco può iniziare!");
            }

            // Esempio di lettura dati da un giocatore
            String data;
            while ((data = input.readLine()) != null) {
                // Processa i dati ricevuti e invia risposte ai giocatori
                // output.println(dati_da_inviare);
            }

            // Chiudi la connessione con il giocatore
            playerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
