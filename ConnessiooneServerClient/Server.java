import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            int port = 5555; // Porta di connessione

            // Crea il server socket
            serverSocket = new ServerSocket(port);
            System.out.println("Server avviato. In attesa di connessioni...");

            while (true) {
                // Accetta connessioni dai client e avvia un thread per gestire la connessione di ciascun giocatore
                Socket playerSocket1 = serverSocket.accept();
                System.out.println("Giocatore 1 connesso.");
                new Thread(new GestorePlayer(playerSocket1, 1)).start();


                Socket playerSocket2 = serverSocket.accept();
                System.out.println("Giocatore 2 connesso.");
                new Thread(new GestorePlayer(playerSocket2, 2)).start();
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
