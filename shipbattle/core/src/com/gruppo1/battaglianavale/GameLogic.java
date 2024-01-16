package com.gruppo1.battaglianavale;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class GameLogic {

    //TODO molto TODO molto molto TODO
    private BattagliaNavale game;
    public GameLogic(BattagliaNavale game){

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

    public void posizionaNave(int i, int j){
        //TODO
    }

}
