import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Indirizzo IP del server
        int port = 5555; // Porta di connessione

        try {
            // Connessione al server
            Socket socket = new Socket(serverAddress, port);

            // Lettura e scrittura dei dati
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // Leggi dati dal server
            String response = input.readLine();
            System.out.println("Server: " + response);

            // Invia dati al server (se necessario)
            //output.println(dati_da_inviare);

            // Chiudi la connessione
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
