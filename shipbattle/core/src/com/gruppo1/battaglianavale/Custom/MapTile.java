package com.gruppo1.battaglianavale.Custom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MapTile extends Image {

    private boolean isOccupied;

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
}
