package com.gruppo1.battaglianavale;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gruppo1.battaglianavale.Custom.MapTile;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    private final BattagliaNavale game;
    private final GameLogic gameLogic;
    private final Texture mapTexture; //700x700


    protected int multiplier;


    Image[][] shipSelectors;

    MapTile[][] mapIcons;
    private Stage gameStage;

    public GameScreen(BattagliaNavale game) {
        this.game = game;
        this.gameLogic = game.theGame;
        this.mapTexture = new Texture(Gdx.files.internal("textures/mapTexture.png"));

        this.multiplier = mapTexture.getWidth()/10;






        shipSelectors = new Image[2][5];
        for(int i = 0; i<5; i++){
            for(int j = 0; j<2; j++){
                shipSelectors[j][i] = (new Image(new Texture(Gdx.files.internal("textures/placeholder.png"))));
                shipSelectors[j][i].setScale(0.15f);
            }

        }

        mapIcons = new MapTile[10][10];
        for(int i = 0; i<10; i++){
            for(int j = 0; j<10; j++){
                mapIcons[i][j] = new MapTile(mapTexture, i*multiplier+5, j*multiplier+5, (i+1)*multiplier, (j+1)*multiplier);
            }
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

        for(int i = 0; i<1; i++){
            for(int j = 0; j<1; j++){
                mapIcons[i][j].setPosition((i+1)*multiplier, (j+1)*multiplier);
                gameStage.addActor(mapIcons[i][j]);
            }
        }

        //Genera le navi da pigliare e posizionare
        for(int i = 0; i<5; i++){
            for(int j = 0; j<2; j++){
                float x = shipSelectors[j][i].getWidth()*shipSelectors[j][i].getScaleX();
                System.out.println(x);
                float y = shipSelectors[j][i].getHeight()*shipSelectors[j][i].getScaleY();
                System.out.println(gameStage.getHeight()+y-(y*i));
                shipSelectors[j][i].setPosition(x/2+(x*j), gameStage.getHeight()/1.5f+y-(y*i)); //?????? Non funzionava per 3000 anni ma mettere calcoli matematici totalmente casuali ha funzionato
                gameStage.addActor(shipSelectors[j][i]);
            }

        }

    }
}
