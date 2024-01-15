package com.gruppo1.battaglianavale;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gruppo1.battaglianavale.Custom.MapTile;
import com.gruppo1.battaglianavale.Custom.ShipTile;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    private final BattagliaNavale game;
    private final GameLogic gameLogic;
    private final Texture mapTexture; //700x700
    private FitViewport viewport;
    private OrthographicCamera camera;
    private boolean held;
    protected int multiplier;

    float mapStartingX, mapStartingY;
    ShipTile[][] shipSelectors;
    MapTile[][] mapIcons;
    private Stage gameStage;
    private Label.LabelStyle lblStyle;
    private Label fpsCounter;

    public GameScreen(BattagliaNavale game) {
        this.game = game;
        this.gameLogic = game.theGame;
        this.mapTexture = new Texture(Gdx.files.internal("textures/mapTexture.png"));
        this.multiplier = mapTexture.getWidth() / 10;
        this.viewport = new FitViewport(1600, 900);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, this.viewport.getWorldWidth(), this.viewport.getWorldHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        this.initComponents();
        this.initListeners();


    }

    @Override
    public void resize(int width, int height) {
        this.gameStage.getViewport().update(width, height);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.WHITE); //Sfondo bianco, will probably change
        fpsCounter.setText("Fps: " + Gdx.graphics.getFramesPerSecond());
        game.batch.begin();
        game.batch.end();
        //
        gameStage.act();

        gameStage.draw();
    }

    @Override
    public void show() {
        super.show();
        this.gameStage = new Stage();
        this.gameStage.setViewport(viewport);
        this.gameStage.getViewport().setCamera(camera);

        Gdx.input.setInputProcessor(gameStage);

        gameStage.setDebugAll(true);

        //Aggiunge la mappa allo stage (rende possibile il render)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 && j == 0) {
                    mapStartingX = 400 + (i + 1) * multiplier;
                    mapStartingY = (j + 1) * multiplier;
                }
                mapIcons[j][i].setPosition(400 + (i + 1) * multiplier, (j + 1) * multiplier);
                gameStage.addActor(mapIcons[i][j]);
            }
        }

        //Posiziona le navi da pigliare e posizionare
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {

                float x = shipSelectors[j][i].getWidth() * shipSelectors[j][i].getScaleX();
                float y = shipSelectors[j][i].getHeight() * shipSelectors[j][i].getScaleY();
                shipSelectors[j][i].setPosition(x / 2 + (x * j), gameStage.getHeight() / 1.5f + y - (y * i)); //?????? Non funzionava per 3000 anni ma mettere calcoli matematici totalmente casuali ha funzionato
                gameStage.addActor(shipSelectors[j][i]);
            }
        }
        gameStage.addActor(fpsCounter);
        fpsCounter.setPosition(gameStage.getWidth() - 50, gameStage.getHeight() - 15);

    }

    private void initComponents() {
        this.initGameMap();
        lblStyle = new Label.LabelStyle();
        lblStyle.font = game.font12;
        fpsCounter = new Label("Fps: ", lblStyle);
        held = false;
    }

    private void initGameMap() {
        shipSelectors = new ShipTile[2][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                shipSelectors[j][i] = (new ShipTile(new Texture(Gdx.files.internal("textures/2.png")), 2));

            }
        }

        mapIcons = new MapTile[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mapIcons[i][j] = new MapTile(mapTexture, j * multiplier, (9 - i) * multiplier, multiplier, multiplier);
            }
        }
    }

    private void initListeners() {
        //Inits ship selectors' listeners
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                int finalJ = j;
                int finalI = i;
                shipSelectors[j][i].addListener(new InputListener() {
                    //Quando ci passi sopra con il mouse mette la manina
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);

                    }

                    //Quando il mouse non è più sopra al "pulsante"
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                    }
                });
                shipSelectors[j][i].addListener(new InputListener() {
                    ShipTile img = shipSelectors[finalJ][finalI];

                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        held = true;
                        System.out.println("Mouse down on the image!");
                        return true;
                    }

                    @Override
                    public void touchDragged(InputEvent event, float x, float y, int pointer) {
                        float changeX = 0, changeY = 0;

                        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                            System.out.println(img.getRotation());
                            if ((int) img.getRotation() == 0)
                                img.setRotation(90);
                            else if ((int) img.getRotation() == 90)
                                img.setRotation(0);
                        }
                        switch ((int) img.getRotation()) {
                            case 0:
                                changeX = ((img.getImageWidth() * img.getScaleX()) / -2);
                                changeY = ((img.getImageHeight() * img.getScaleY()) / -2) * -1;
                                break;
                            case 90:
                                changeX = ((img.getImageWidth()));
                                changeY = img.getImageHeight() / 4;
                                break;
                        }
                        img.setPosition(Gdx.input.getX() + changeX, gameStage.getHeight() - Gdx.input.getY() - changeY);
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        held = false;
                        System.out.println(img.getWidth());
                        System.out.println(img.getHeight());
                        if (inMap(img)) {
                            img.removeListener(this);
                        }
                    }
                });
            }
        }


    }

    private boolean inMap(ShipTile img) {
        if (img.getRotation() == 90) {
            
        } else {

        }
        if (img.getX() > mapStartingX
                && img.getX() < mapStartingX + 700
                && img.getY() > mapStartingY
                && img.getY() < mapStartingY + 700
                && img.getX() + img.getWidth() > mapStartingX
                && img.getX() + img.getWidth() < mapStartingX + 700
                && img.getY() + img.getHeight() > mapStartingY
                && img.getY() + img.getHeight() < mapStartingY + 700) {
            int x1, x2, y1, y2;


            switch (img.getSize()) {
                case 1:


                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;

            }

            return true;
        } else return false;
    }

    private boolean posizionaNave(int x1, int y1, int x2, int y2) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int xMap1 = ((int) mapIcons[i][j].getX());
                int xMap2 = ((int) mapIcons[i][j].getX() + (int) mapIcons[i][j].getImageWidth());
                int yMap1 = ((int) mapIcons[i][j].getY());
                int yMap2 = ((int) mapIcons[i][j].getY() + (int) mapIcons[i][j].getImageHeight());

                int distance = (int) Math.hypot(xMap1 - x1, yMap1 - y1);
                int distance2 = (int) Math.hypot(xMap2 - x2, yMap2 - y2);
                if (distance > distance2) {
                    System.out.println("Posizionata in " + i + ", " + j);
                    return true;
                }


            }
        }
        return false;
    }

}


