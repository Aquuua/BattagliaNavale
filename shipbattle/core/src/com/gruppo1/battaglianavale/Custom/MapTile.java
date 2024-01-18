package com.gruppo1.battaglianavale.Custom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MapTile extends Image {

    private boolean isOccupied;
    private int grandezzaNave;
    //oppure ancora meglio
    private ShipTile nave;

    public MapTile(Texture texture, int x, int y, int width, int height) {
        super(new TextureRegion(texture, x, y, width, height));
        isOccupied = false;
    }

    public boolean getOccupation(){
        return isOccupied;
    }
    public void setOccupation(boolean sas){
        this.isOccupied = sas;
    }

    public int getGrandezzaNave() {
        return grandezzaNave;
    }

    public void setGrandezzaNave(int grandezzaNave) {
        this.grandezzaNave = grandezzaNave;
    }

    public ShipTile getNave() {
        return nave;
    }

    public void setNave(ShipTile nave) {
        this.nave = nave;
    }
}
