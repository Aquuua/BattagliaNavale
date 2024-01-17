/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battaglianavale;

import java.util.Scanner;

/**
 *
 * @author Alessandro Bai
 */
public class Giocatore {
    String nome;
    TabellaGioco tabella;
    boolean visualizzaTabellaAvversario;

    public Giocatore(String nome) {
        this.nome = nome;
        this.tabella = new TabellaGioco();
        this.visualizzaTabellaAvversario = false;
    }

    public void posizionaNavi() {
        System.out.println(nome + ", posiziona le tue navi:");
        posizionaNave(5, "Portaerei", 'P');
        posizionaNave(4, "Corazzata", 'C');
        posizionaNave(3, "Fregate", 'F');
        posizionaNave(3, "Fregate", 'F');
        posizionaNave(2, "Torpediniera", 'T');
        posizionaNave(1, "Sottomarino", 'S');
        System.out.println(nome + ", hai posizionato tutte le navi.");
    }

    private void posizionaNave(int lunghezza, String tipoNave, char simboloNave) {
        System.out.println("Posiziona " + tipoNave + " di lunghezza " + lunghezza + " con simbolo '" + simboloNave + "':");
        Scanner scanner = new Scanner(System.in);

        int x, y;
        char direzione = ' ';
        if (lunghezza != 1) {
            do {
                System.out.print("Inserisci la coordinata x: ");
                x = scanner.nextInt();
                System.out.print("Inserisci la coordinata y: ");
                y = scanner.nextInt();
                System.out.print("Vuoi posizionare la nave in orizzontale (o) o verticale (v): ");
                direzione = scanner.next().charAt(0);
            } while (!tabella.posizionaNave(y - 1, x - 1, direzione, lunghezza, simboloNave));
        } else {
            do {
                System.out.print("Inserisci la coordinata x per il sottomarino: ");
                x = scanner.nextInt();
                System.out.print("Inserisci la coordinata y per il sottomarino: ");
                y = scanner.nextInt();
            } while (!tabella.posizionaNave(y - 1, x - 1, direzione, lunghezza, simboloNave));
        }

        visualizzaTabella();
    }

    public void attacca(Giocatore avversario) {
        System.out.println(nome + ", è il tuo turno di attaccare!");
        if (visualizzaTabellaAvversario) {
            avversario.tabella.visualizzaTabella();
        }

        int x, y;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Inserisci la coordinata x per l'attacco: ");
            x = scanner.nextInt();
            System.out.print("Inserisci la coordinata y per l'attacco: ");
            y = scanner.nextInt();
        } while (!avversario.tabella.attacca(y - 1, x - 1));

        avversario.tabella.visualizzaTabella();
    }

    public boolean haPerso() {
        return tabella.tutteNaviAffondate();
    }

    private void visualizzaTabella() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(tabella.tabella[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}