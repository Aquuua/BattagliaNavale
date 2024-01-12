package com.gruppo1.battaglianavale;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    private final BattagliaNavale game;
    private final GameLogic gameLogic;
    final ArrayList<Image> shipSelectors;
    private Stage gameStage;

    public GameScreen(BattagliaNavale game) {
        this.game = game;
        this.gameLogic = game.theGame;


        shipSelectors = new ArrayList<>();
        for(int i = 0; i<10; i++){
            shipSelectors.add(new Image(new Texture(Gdx.files.internal("textures/placeholder.png"))));
            shipSelectors.get(i).setScale(0.2f);

        }


    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.WHITE);
        game.batch.begin();
        game.batch.end();

        gameStage.act();
        gameStage.draw();
    }

    @Override
    public void show() {
        super.show();
        this.gameStage = new Stage();
        Gdx.input.setInputProcessor(gameStage);

        for(int i = 0; i<5; i++){
            for(int j = 0; j<2; j++){
                shipSelectors.get(i+j).setPosition(200+(200*j), gameStage.getHeight()-200+(200*i));
                gameStage.addActor(shipSelectors.get(i+j));
            }

        }
    }
}
