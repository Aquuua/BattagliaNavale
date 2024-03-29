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

    private BattagliaNavale game;

    private static final int DEFAULT_PORT = 5959;

    boolean hasGameStarted;//TRUE se entrambi i player hanno messo Pronto


    boolean isGameReady; //TRUE se sono entrati entrambi i player,


    boolean isPlayerReady;// TRUE se il client ha messo pronto

    boolean mappaClient[][];
    boolean attaccoEseguito;
    boolean attaccoPianificato;

    Coordinata coordinataAttacco;

    public GameLogic(BattagliaNavale game) {

        this.game = game;
        hasGameStarted = false;
        isGameReady = false;
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




    //J è la x, I è la y
    public void inizializzaMappa(MapTile mapTile[][]){
        for (int i = 0; i<10; i++) {

            for (int j = 0; j<10; j++) {
                mappaClient[i][j] = mapTile[9-i][j].getOccupation();
            }
        }
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

    public boolean haPerso(){
        if(!haNavi()){
            game.gameScreen.haiPerso();
            return true;
        }else return false;
    }

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

    public boolean enterGame(String ipAddress, int port){
        try{
            client = new Client(ipAddress, port, this);
            new Thread(client).start();
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public void iniziaPartita(){
        //game.gameScreen.setInfo("Partita iniziata! Posiziona le tue navi");
        this.hasGameStarted = true;
    }

    public void iniziaTimer(){
        game.gameScreen.setInfo("Posiziona le tue navi!");
        this.isGameReady= true;


    }

    public void attacca(){
        int x = coordinataAttacco.getX(), y = coordinataAttacco.getY();
        client.attack(x,y);

    }
    public void colpito(int x1, int y1){
        int x = x1-1;
        int y = y1-1;
        if(mappaClient[9-y][x]){
            mappaClient[9-y][x] = false;
            game.gameScreen.colpitoFuoco(x,y);
        }

        coordinataAttacco = null;
        attaccoEseguito = false;
        attaccoPianificato = false;
        if(haPerso()){
            client.closeConnection();
        }else{
            game.gameScreen.setInfo("Ti hanno sparato in: " + x + "-"+y+" Attacca!");
        }


    }
    //autoesplicativo
    public void vittoria(){
        game.gameScreen.haiVinto();
    }
    //autoesplicativo
    public void chiusura(){
        client.closeConnection();

    }

    //autoesplicativo
    public void pronto(){
        client.ready();
    }

}
