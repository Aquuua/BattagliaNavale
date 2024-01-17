package com.gruppo1.battaglianavale.Custom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ShipTile extends Image {
    private final int size;

    private float initialX;
    private float initialY;
    public ShipTile(Texture texture, int size) {
        super(texture);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setInitialX(float x)
    {
        this.initialX = x;
    }
    public void setInitialY(float y)
    {
        this.initialY = y;
    }

    public float getInitialX() {
        return initialX;
    }

    public float getInitialY() {
        return initialY;
    }
}
