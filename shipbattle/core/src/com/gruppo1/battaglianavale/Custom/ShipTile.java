package com.gruppo1.battaglianavale.Custom;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ShipTile extends Image {
    private final int size;

    public ShipTile(Texture texture, int size) {
        super(texture);
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
