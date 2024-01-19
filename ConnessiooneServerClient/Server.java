import java.io.*;
import java.net.*;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private int port;
    private Socket g1, g2;
    private BufferedReader input1, input2;
    private PrintWriter output1, output2 ;
    

    public Server() {
        serverSocket = null;
        port = 5555;
    }

    public void run() {
        try {
            Connessione();

        } catch (Exception e) {
        }
    }

    public void Connessione() {
        try {
            // Porta di connessione

            // Crea il server socket
            serverSocket = new ServerSocket(port);
            System.out.println("Server avviato. In attesa di connessioni...");

            while (true) {
                g1 = serverSocket.accept();
                input1 = new BufferedReader(new InputStreamReader(g1.getInputStream()));
                output1 = new PrintWriter(g1.getOutputStream(), true);

                
                
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
    /*
     * public static void main(String[] args) {
     * ServerSocket serverSocket = null;
     * 
     * try {
     * int port = 5555; // Porta di connessione
     * 
     * // Crea il server socket
     * serverSocket = new ServerSocket(port);
     * System.out.println("Server avviato. In attesa di connessioni...");
     * 
     * while (true) {
     * // Accetta connessioni dai client e avvia un thread per gestire la
     * connessione di ciascun giocatore
     * Socket playerSocket1 = serverSocket.accept();
     * System.out.println("Giocatore 1 connesso.");
     * new Thread(new GestorePlayer(playerSocket1, 1)).start();
     * 
     * 
     * Socket playerSocket2 = serverSocket.accept();
     * System.out.println("Giocatore 2 connesso.");
     * new Thread(new GestorePlayer(playerSocket2, 2)).start();
     * 
     * }
     * } catch (IOException e) {
     * e.printStackTrace();
     * } finally {
     * try {
     * if (serverSocket != null) {
     * serverSocket.close();
     * }
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     * }
     */
}
