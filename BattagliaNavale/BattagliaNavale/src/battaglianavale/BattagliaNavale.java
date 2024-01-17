/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package battaglianavale;

/**
 *
 * @author Alessandro Bai
 */
public class BattagliaNavale {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Giocatore giocatore1 = new Giocatore("Giocatore 1");
        Giocatore giocatore2 = new Giocatore("Giocatore 2");

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
