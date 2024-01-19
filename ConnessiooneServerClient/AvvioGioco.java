public class AvvioGioco {
    public static void main(String[] args) {
        Server server = new Server();
        Client client1 = new Client("Giocatore 1");

        server.start();
        client1.start();
    }
}
