package com.gruppo1.battaglianavale;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen extends ScreenAdapter {
    final BattagliaNavale game;
    public GameScreen(BattagliaNavale game) {
        this.game = game;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.BLUE);
    }

    @Override
    public void show() {
        super.show();
    }
}
