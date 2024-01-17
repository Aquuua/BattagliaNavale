public class TabellaGioco {
    char[][] tabella;

    public TabellaGioco() {
        tabella = new char[10][10];
        inizializzaTabella();
    }

    // Metodo che inizializza la tabella per ogni giocatore
    private void inizializzaTabella() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tabella[i][j] = ' ';
            }
        }
    }

    // Metodo che stampa la tabella
    public void visualizzaTabella() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < 10; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(tabella[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Metodo che contiene i controlli del posizionamento delle navi
    public boolean posizionaNave(int x, int y, char direzione, int lunghezza, char simboloNave) {
        if (x < 0 || x >= 10 || y < 0 || y >= 10) {
            System.out.println("Errore: Coordinate non valide. Riprova.");
            return false;
        }

        if (direzione == 'o') { // orizzontale
            if (y + lunghezza > 10) {
                System.out.println("Errore: La nave esce dalla tabella. Riprova.");
                return false;
            }
            for (int j = y; j < y + lunghezza; j++) {
                if (tabella[x][j] != ' ') {
                    System.out.println("Errore: Posizione già occupata. Riprova.");
                    return false;
                }
            }
            for (int j = y; j < y + lunghezza; j++) {
                tabella[x][j] = simboloNave;
            }
        } else if (direzione == 'v') { // verticale
            if (x + lunghezza > 10) {
                System.out.println("Errore: La nave esce dalla tabella. Riprova.");
                return false;
            }
            for (int i = x; i < x + lunghezza; i++) {
                if (tabella[i][y] != ' ') {
                    System.out.println("Errore: Posizione già occupata. Riprova.");
                    return false;
                }
            }
            for (int i = x; i < x + lunghezza; i++) {
                tabella[i][y] = simboloNave;
            }
        } else {
            if (tabella[x][y] != ' ') {
                System.out.println("Errore: Posizione già occupata. Riprova.");
                return false;
            }
            tabella[x][y] = simboloNave;
        }

        return true;
    }

    // Metodo che controlla se l'attacco ha colpito oppure no
    public boolean attacca(int x, int y) {
        if (x < 0 || x >= 10 || y < 0 || y >= 10) {
            System.out.println("Errore: Attacco non valido. Riprova.");
            return false;
        }

        if (tabella[x][y] != ' ') {
            System.out.println("Hai colpito una nave!");
            tabella[x][y] = 'X';
        } else {
            System.out.println("Hai mancato!");
            tabella[x][y] = 'O';
        }

        return true;
    }

    // Metodo che controlla se tutte le navi sono state affondate
    public boolean tutteNaviAffondate() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tabella[i][j] != ' ' && tabella[i][j] != 'O' && tabella[i][j] != 'X') {
                    return false;
                }
            }
        }
        return true;
    }
}