public class AvvioGioco {
    public static void main(String[] args) {
        Server server = new Server();
        Client client1 = new Client("Giocatore 1");
        Client client2 = new Client("Giocatore 2");

        server.run();
        client1.run();
        client2.run();
    }
}
