import java.io.*;
import java.net.*;

public class Client extends Thread {

    private String serverAddress;
    private int port;
    private String nome;

    public Client(String n) {
        serverAddress = "127.0.0.1"; // Indirizzo IP del server
        port = 5555; // Porta di connessione
        nome = n;
    }

    public void run() {
        try {
            Connessione();
        } catch (Exception e) {
        }
    }

    public void Connessione() {
        try {
            // Connessione al server
            Socket socket = new Socket(serverAddress, port);

            // Lettura e scrittura dei dati
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Leggi dati dal server
            String response = input.readLine();
            System.out.println(nome + "Collegato al server: " + response);

            // Invia dati al server (se necessario)
            // output.println(dati_da_inviare);

            // Chiudi la connessione
            //socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * public static void main(String[] args) {
     * String serverAddress = "127.0.0.1"; // Indirizzo IP del server
     * int port = 5555; // Porta di connessione
     * 
     * try {
     * // Connessione al server
     * Socket socket = new Socket(serverAddress, port);
     * 
     * // Lettura e scrittura dei dati
     * BufferedReader input = new BufferedReader(new
     * InputStreamReader(socket.getInputStream()));
     * PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
     * 
     * // Leggi dati dal server
     * String response = input.readLine();
     * System.out.println("Server: " + response);
     * 
     * // Invia dati al server (se necessario)
     * //output.println(dati_da_inviare);
     * 
     * // Chiudi la connessione
     * socket.close();
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     */
}
