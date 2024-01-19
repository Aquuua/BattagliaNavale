package com.gruppo1.battaglianavale;


import com.badlogic.gdx.Gdx;
import com.gruppo1.battaglianavale.Communication.Client;
import com.gruppo1.battaglianavale.Communication.Server;
import com.gruppo1.battaglianavale.Custom.Coordinata;
import com.gruppo1.battaglianavale.Custom.MapTile;

import java.net.*;


public class GameLogic //extends Thread
{
    private Server hosting;
    private Client client;
    //TODO implementazione Client in GameLogic
    //TODO molto TODO molto molto TODO
    private BattagliaNavale game;

    private static final int DEFAULT_PORT = 5959;

    boolean hasGameStarted;//TRUE se entrambi i player hanno messo Pronto
    //TODO variabile che verrà modificata dal SERVER con un BROADCAST ai CLIENT (SPERO)

    boolean isGameReady; //TRUE se sono entrati entrambi i player,
    //TODO variabile che verrà modificata dal SERVER con un BROADCAST ai CLIENT (SPERO)

    boolean isPlayerReady;// TRUE se il client ha messo pronto

    boolean mappaClient[][];
    boolean attaccoEseguito;
    boolean attaccoPianificato;

    Coordinata coordinataAttacco;

    public GameLogic(BattagliaNavale game) {
        hasGameStarted = false;
        isGameReady = false; //true TEMPORANEI sto aspettando il client e server quindi TODO ANCHE QUESTOOO
        isPlayerReady = false;

        mappaClient = new boolean[10][10];
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

    public void posizionaNave(int x, int y) {
        //TODO dal visuale al server attraverso il client

    }

    //J è la x, I è la y
    public void inizializzaMappa(MapTile mapTile[][]){
        for (int i = 0; i<10; i++) {

            for (int j = 0; j<10; j++) {
                mappaClient[i][j] = mapTile[9-i][j].getOccupation(); //???????????????????????????????????????????????????????????
            }
        }
        for (int i = 0; i<10; i++) {
            System.out.println();
            for (int j = 0; j<10; j++) {
                if(mappaClient[i][j]){
                    System.out.print("0");
                }else System.out.print("1");
            }
        }


        //SAS
    }

    public boolean haNavi(){
        for (int i = 0; i<10; i++) {

            for (int j = 0; j<10; j++) {
                if(mappaClient[i][j]){
                    return true;
                }
            }
        }

        return false;
    }

//    public boolean haPerso(){
//        if(!haNavi()){
//            //TODO
//
//        }
//    }
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

    public static int getDefaultPort(){
        return GameLogic.DEFAULT_PORT;
    }

    public boolean entraNellaPartita(String ip) {
        if (this.checkDots(ip))
            return true;
        return false;
    }

    public void initGame(){
        hosting = new Server();
        new Thread(hosting).start();
        client = new Client("localhost", GameLogic.getDefaultPort(), this);

        new Thread(client).start();
    }

    public void enterGame(String ipAddress, int port){
        client = new Client(ipAddress, port, this);
        new Thread(client).start();
    }

    public void iniziaPartita(){
        this.hasGameStarted = true;
    }

    public void iniziaTimer(){
        this.isGameReady= true;

    }

    public void colpito(int x, int y){

    }

}
