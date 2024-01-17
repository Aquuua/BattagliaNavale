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
import com.badlogic.gdx.math.Vector2;
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
import sun.security.util.ArrayUtil;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    private final BattagliaNavale game;
    private final GameLogic gameLogic;
    private final int MAX_MAP_SIZE;
    private final Texture mapTexture; //700x700
    private FitViewport viewport;
    private OrthographicCamera camera;
    private boolean held;
    private ShipTile temp;
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
        MAX_MAP_SIZE = (int)mapIcons[0][0].getWidth()*10;
        System.out.println(MAX_MAP_SIZE);
        System.out.println(viewport.getScaling());
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
                shipSelectors[j][i].setInitialX(shipSelectors[j][i].getX());
                shipSelectors[j][i].setInitialY(shipSelectors[j][i].getY());
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
                shipSelectors[j][i] = (new ShipTile(new Texture(Gdx.files.internal("textures/3.png")), 3));


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
                        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);

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
                        if(inMap(img)){
                            int col = (int) ((img.getX()-mapStartingX)/70);
                            int row = (int) ((img.getY()-mapStartingY)/70);
                            float xTemp = mapStartingX + col *70;
                            float yTemp = mapStartingY + row*70;
                            img.setPosition(xTemp, yTemp);
                        }

                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        held = false;
                        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                        System.out.println(img.getWidth());
                        System.out.println(img.getHeight());
                        System.out.println(img.getX() + " " +  img.getY());

                        if(inMap(img)){
                            ArrayList<MapTile> navi;
                            navi = posizioneNave(img);
                            if(navi != null){
                                for (int k = 0; k < navi.size(); k++) {
                                    navi.get(k).setOccupation(true);
                                }
                                img.removeListener(this);
                            }else {
                                System.out.println("POSIZIONE INVALIDAaAAAAAAAS");
                                img.setPosition(img.getInitialX(), img.getInitialY());
                                img.setRotation(0);
                            }

                        }
                        temp = img;

                    }
                });
            }
        }

    }
    private ArrayList<MapTile> posizioneNave(ShipTile img){
        ArrayList<MapTile> tempMappe;
        if(img.getRotation() == 0){
            int yIniziale = (int)img.getY();
            tempMappe = new ArrayList<>();


                for(int j = 0; j<10; j++){
                    for(int k = 0; k<10; k++){
                        int mapY = (int)mapIcons[j][k].getY();
                        if(mapY==yIniziale && (img.getX() == mapIcons[j][k].getX())){
                            if(!mapIcons[j][k].getOccupation()){
                                for(int i = 0; i<img.getSize(); i++) {
                                    if(!mapIcons[j+i][k].getOccupation()){
                                        tempMappe.add(mapIcons[j+i][k]);

                                        int sum = j+i;
                                        System.out.println(sum + " ," + k);
                                    }else return null;

                                }
                            }
                            else
                            {

                                return null;
                            }

                        }
                    }
                }
            return tempMappe;
        }else if(img.getRotation() == 90)
        {
            int xIniziale = (int)img.getX()-(int)img.getHeight();
            tempMappe = new ArrayList<>();

            for(int j = 0; j<10; j++){
                for(int k = 0; k<10; k++){
                    int mapX = (int)mapIcons[j][k].getX();
                    if(mapX==xIniziale && (img.getY() == mapIcons[j][k].getY())){
                        if(!mapIcons[j][k].getOccupation()){
                            for(int i = 0; i<img.getSize(); i++) {
                                if(!mapIcons[j][k+i].getOccupation()){
                                    tempMappe.add(mapIcons[j][k+i]);
                                    int sum = k+i;
                                    System.out.println(j + " ," + sum);
                                }else return null;

                            }
                        }else{

                            return null;
                        }
                    }
                }
            }
            return tempMappe;
        }
        else

        return null;
    }

    private boolean inMap(ShipTile img) {
        if(img.getRotation() == 0){
            if (img.getX() >= mapStartingX
                    && img.getX() <= (mapStartingX + MAX_MAP_SIZE+70)
                    && img.getY() >= mapStartingY
                    && img.getY() <= (mapStartingY + MAX_MAP_SIZE+70)
                    && (img.getX() + img.getWidth()) >= mapStartingX
                    && (img.getX() + img.getWidth()) <= (mapStartingX + MAX_MAP_SIZE+70)
                    && (img.getY() + img.getHeight()) >=mapStartingY
                    && (img.getY() + img.getHeight()) <= (mapStartingY + MAX_MAP_SIZE+70))
            {


                return true;
            } else return false;
        }else
        {
            if (img.getX()  >= mapStartingX
                    && img.getX()  <= (mapStartingX + MAX_MAP_SIZE+70)
                    && img.getY()  >= mapStartingY
                    && img.getY() < (mapStartingY + MAX_MAP_SIZE)
                    && (img.getX() - img.getHeight()) >= mapStartingX
                    && (img.getX() - img.getHeight()) <= (mapStartingX + MAX_MAP_SIZE+70)
                    && (img.getY() + img.getWidth()) >=mapStartingY
                    && (img.getY() - img.getWidth()) < (mapStartingY + MAX_MAP_SIZE))
            {


                return true;
            } else return false;
        }

    }



}


