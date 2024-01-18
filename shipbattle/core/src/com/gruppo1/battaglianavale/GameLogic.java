package com.gruppo1.battaglianavale;


import java.net.*;


public class GameLogic //extends Thread
{

    //TODO implementazione Client in GameLogic
    //TODO molto TODO molto molto TODO
    private BattagliaNavale game;

    boolean hasGameStarted;//TRUE se entrambi i player hanno messo Pronto
    //TODO variabile che verrà modificata dal SERVER con un BROADCAST ai CLIENT (SPERO)

    boolean isGameReady; //TRUE se sono entrati entrambi i player,
    //TODO variabile che verrà modificata dal SERVER con un BROADCAST ai CLIENT (SPERO)

    boolean isPlayerReady;// TRUE se il client ha messo pronto


    boolean attaccoEseguito;

    public GameLogic(BattagliaNavale game) {
        hasGameStarted = false;
        isGameReady = true; //true TEMPORANEI sto aspettando il client e server quindi TODO ANCHE QUESTOOO
        isPlayerReady = false;

    }

    public String getLocalAddress() {
        String addr;
        try {
            addr = String.valueOf(Inet4Address.getLocalHost());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return addr;
    }

    public void posizionaNave(int i, int j) {
        //TODO dal visuale al server attraverso il client
    }
//    public void run(){
//        while true
//    }
    //Controlla se una stringa è un vero indirizzo IP
    private boolean checkDots(String ip) {

        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }

            String[] parts = ip.split("\\.");
            if (parts.length != 4) {
                return false;
            }

            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
            if (ip.endsWith(".")) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public boolean entraNellaPartita(String ip) {
        if (this.checkDots(ip))
            return true;
        return false;
    }

}
