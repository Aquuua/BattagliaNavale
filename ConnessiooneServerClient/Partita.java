public class Partita implements Runnable{
    
    private Giocatore giocatore1;
    private Giocatore giocatore2;

    public Partita (Giocatore g1, Giocatore g2){
        giocatore1 = g1;
        giocatore2 = g2;
    }

    @Override
    public void run() {
        giocatore1.posizionaNavi();
        giocatore2.posizionaNavi();

        while (true) {
            giocatore1.attacca(giocatore2);
            if (giocatore2.haPerso()) {
                System.out.println(giocatore1.nome + " ha vinto!");
                break;
            }

            giocatore2.attacca(giocatore1);
            if (giocatore1.haPerso()) {
                System.out.println(giocatore2.nome + " ha vinto!");
                break;
            }
        }
    }

}
