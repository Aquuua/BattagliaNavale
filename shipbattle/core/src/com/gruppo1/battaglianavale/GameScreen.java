package com.gruppo1.battaglianavale;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gruppo1.battaglianavale.Custom.Coordinata;
import com.gruppo1.battaglianavale.Custom.MapTile;
import com.gruppo1.battaglianavale.Custom.ShipTile;
import javax.swing.*;
import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    private final BattagliaNavale game;
    private final GameLogic gameLogic;
    private final int MAX_MAP_SIZE;

    //probabilmente verrà spostato nella classe GameLogic
    private int ship1, ship2, ship3, ship4, ship5;


    private final Texture mapTexture; //700x700
    private final Texture attackMapTexture;
    private FitViewport viewport;
    private OrthographicCamera camera;

    private boolean held;
    private ShipTile temp;
    protected int multiplier;

    float mapStartingX, mapStartingY;
    ShipTile[][] shipSelectors;
    MapTile[][] mapIcons;
    MapTile[][] mapIconsAttack;
    private Stage gameStage;
    private Image btnReady;
    private Label.LabelStyle lblStyle;

    private Label.LabelStyle lblStyle18;
    private Image bordiMappa;
    private Image bordiMappaAttacco;
    private Label tip;
    private Label fpsCounter;
    private Timer time;
    private Label ipAddr;
    private Label tempo;
    private Label info;
    private Image confermaAttacco;
    private Image btnCambiaMappa;
    private Image schermataFunny;

    private Sound suonoFunny;
    private ArrayList<Image> zoneColpite;

    public GameScreen(BattagliaNavale game) {
        this.game = game;
        this.gameLogic = game.theGame;
        this.mapTexture = new Texture(Gdx.files.internal("textures/mapTexture.png"));
        this.attackMapTexture = new Texture(Gdx.files.internal("textures/mappaAttacco.png"));
        this.schermataFunny = new Image(new Texture(Gdx.files.internal("textures/haiperso.png")));
        this.suonoFunny = Gdx.audio.newSound(Gdx.files.internal("sounds/skillissue.mp3"));



        this.multiplier = mapTexture.getWidth() / 10;
        this.viewport = new FitViewport(1600, 900);

        ship1 = 1;
        ship2 = 1;
        ship3 = 2;
        ship4 = 1;
        ship5 = 1;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, this.viewport.getWorldWidth(), this.viewport.getWorldHeight());
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        this.initComponents();
        //this.initListeners();
        MAX_MAP_SIZE = (int) mapIcons[0][0].getWidth() * 10;
        System.out.println(MAX_MAP_SIZE);
        System.out.println(viewport.getScaling());
    }

    private void initGameMap() {
        shipSelectors = new ShipTile[2][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (ship1 != 0) {
                    shipSelectors[j][i] = (new ShipTile(new Texture(Gdx.files.internal("textures/1.png")), 1));
                    ship1--;
                } else if (ship2 != 0) {
                    shipSelectors[j][i] = (new ShipTile(new Texture(Gdx.files.internal("textures/2.png")), 2));
                    ship2--;
                } else if (ship3 != 0) {
                    shipSelectors[j][i] = (new ShipTile(new Texture(Gdx.files.internal("textures/3.png")), 3));
                    ship3--;
                } else if (ship4 != 0) {
                    shipSelectors[j][i] = (new ShipTile(new Texture(Gdx.files.internal("textures/4.png")), 4));
                    ship4--;
                } else if (ship5 != 0) {
                    shipSelectors[j][i] = (new ShipTile(new Texture(Gdx.files.internal("textures/5.png")), 5));
                    ship5--;
                }
            }
        }

        mapIcons = new MapTile[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mapIcons[i][j] = new MapTile(mapTexture, j * multiplier, (9 - i) * multiplier, multiplier, multiplier);
            }
        }

        mapIconsAttack = new MapTile[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mapIconsAttack[i][j] = new MapTile(mapTexture, j * multiplier, (9 - i) * multiplier, multiplier, multiplier);
            }
        }
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
//        if (gameLogic.isGameReady) {
//            //this.initListeners();
//            //verrà invocato
//
//            info.setPosition(btnReady.getX() - info.getWidth() / 2, gameStage.getHeight() - 150);
//        }

        //
        if (gameLogic.isGameReady) {
            this.initTimer();
            initListeners();
            //info.setText("Aspettando che siate entrambi pronti...");
            gameLogic.isGameReady = false;

        } else {

        }
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
        //gameStage.setDebugAll(true);

        //Aggiunge la mappa allo stage (rende possibile il render)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 && j == 0) {
                    mapStartingX = 400 + (i + 1) * multiplier;
                    mapStartingY = (j + 1) * multiplier;
                    gameStage.addActor(bordiMappa);
                    gameStage.addActor(bordiMappaAttacco);
                    bordiMappa.setPosition(mapStartingX - multiplier, mapStartingY);
                    bordiMappaAttacco.setPosition(mapStartingX-multiplier, mapStartingY);
                }
                mapIcons[j][i].setPosition(400 + (i + 1) * multiplier, (j + 1) * multiplier);
                mapIconsAttack[j][i].setPosition(400 + (i + 1) * multiplier, (j + 1) * multiplier);
                mapIconsAttack[j][i].setVisible(false);
                gameStage.addActor(mapIcons[i][j]);
                gameStage.addActor(mapIconsAttack[i][j]);
            }
        }

        //Posiziona le navi da pigliare e posizionare
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                //TODO fix ship selectors location, it sucks
                float x = shipSelectors[j][i].getWidth() * shipSelectors[j][i].getScaleX();
                float y = shipSelectors[j][i].getHeight() * shipSelectors[j][i].getScaleY();

                shipSelectors[j][i].setPosition(x / 2 + (x * j), gameStage.getHeight() / 1.5f + y - (y * i)); //?????? Non funzionava per 3000 anni ma mettere calcoli matematici totalmente casuali ha funzionato
                shipSelectors[j][i].setInitialX(shipSelectors[j][i].getX());
                shipSelectors[j][i].setInitialY(shipSelectors[j][i].getY());
                gameStage.addActor(shipSelectors[j][i]);
            }
        }
        gameStage.addActor(fpsCounter);
        gameStage.addActor(tip);
        gameStage.addActor(btnReady);
        gameStage.addActor(tempo);
        gameStage.addActor(info);
        gameStage.addActor(ipAddr);
        gameStage.addActor(confermaAttacco);
        gameStage.addActor(btnCambiaMappa);
        gameStage.addActor(schermataFunny);
        tip.setPosition(gameStage.getWidth() / 2 - tip.getWidth() / 2, gameStage.getHeight() - 30);
        ipAddr.setPosition(gameStage.getWidth() / 2 - ipAddr.getWidth() / 2, 20);
        fpsCounter.setPosition(gameStage.getWidth() - 50, gameStage.getHeight() - 15);
        btnReady.setPosition(gameStage.getWidth() - btnReady.getWidth() * btnReady.getScaleX() - tempo.getWidth() / 5, gameStage.getHeight() - 250);
        info.setPosition(btnReady.getX() - 5, gameStage.getHeight() - 150);
        tempo.setPosition(gameStage.getWidth() - tempo.getWidth(), gameStage.getHeight() - 100);
        confermaAttacco.setPosition(gameStage.getWidth() - 200, gameStage.getHeight() - 300);
        btnCambiaMappa.setPosition(gameStage.getWidth() - 100, gameStage.getHeight() - 300);
        schermataFunny.setPosition(0,0);

        schermataFunny.setVisible(false);

    }
    public void haiPerso(){
        schermataFunny.setVisible(true);
        suonoFunny.play();

    }

    private void toggleMap() {
        if (!mapIconsAttack[0][0].isVisible()) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    mapIconsAttack[j][i].setVisible(true);
                    bordiMappaAttacco.setVisible(true);
                    bordiMappa.setVisible(false);
                    mapIcons[j][i].setVisible(false);

                }
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 2; j++) {
                    //TODO fix ship selectors location, it sucks
                    float x = shipSelectors[j][i].getWidth() * shipSelectors[j][i].getScaleX();
                    float y = shipSelectors[j][i].getHeight() * shipSelectors[j][i].getScaleY();

                    shipSelectors[j][i].setVisible(false);

                }
            }
        } else {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {


                    mapIconsAttack[j][i].setVisible(false);
                    bordiMappaAttacco.setVisible(false);
                    bordiMappa.setVisible(true);
                    mapIcons[j][i].setVisible(true);

                }
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 2; j++) {
                    //TODO fix ship selectors location, it sucks
                    float x = shipSelectors[j][i].getWidth() * shipSelectors[j][i].getScaleX();
                    float y = shipSelectors[j][i].getHeight() * shipSelectors[j][i].getScaleY();

                    shipSelectors[j][i].setVisible(true);

                }
            }
        }
    }

    private void initComponents() {
        this.initGameMap();
        lblStyle = new Label.LabelStyle();
        lblStyle.font = game.font12;
        lblStyle18 = new Label.LabelStyle();
        lblStyle18.font = game.font18;
        ipAddr = new Label("IPV4: " + gameLogic.getLocalAddress(), lblStyle18);
        tip = new Label("Premi il tasto R per ruotare la nave mentre la trasporti sulla mappa!", lblStyle18);
        fpsCounter = new Label("Fps: ", lblStyle);
        held = false;
        tempo = new Label("Non pronto! Tempo rimanente: ", lblStyle18);
        btnReady = new Image(new Texture(Gdx.files.internal("textures/pulsantePronto.png")));
        btnReady.setScale(0.3f);
        info = new Label("In attesa del player...", lblStyle);
        btnCambiaMappa = new Image(new Texture(Gdx.files.internal("textures/pulsanteCambia.png")));
        btnCambiaMappa.setScale(0.5f);
        btnCambiaMappa.setVisible(false);
        this.bordiMappa = new Image(new Texture(Gdx.files.internal("textures/bordi.png")));
        this.bordiMappaAttacco = new Image(new Texture(Gdx.files.internal("textures/bordiAttacco.png")));
        this.bordiMappaAttacco.setVisible(false);
        this.confermaAttacco = new Image(new Texture(Gdx.files.internal("textures/pulsanteAttacco.png")));
        confermaAttacco.setVisible(false);
        confermaAttacco.setScale(0.2f);
        time = new Timer();

    }

    private void initTimer() {
        time.schedule(new Timer.Task() {
            private float countdownTime = 120;

            @Override
            public void run() {
                if (!gameLogic.isPlayerReady) {
                    countdownTime -= 1;
                    tempo.setText(String.format("Tempo rimanente: %.0f seconds", countdownTime));
                    if (countdownTime <= 0) {
                        //TODO eventuale metodo che chiuderà il collegamento client-server e tutto il resto
                        showDialog("Non hai messo pronto in tempo.");
                    }
                }
            }
        }, 0, 1);
    }

    private void showDialog(String message) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, message, "Skill issue", JOptionPane.INFORMATION_MESSAGE);

        // Close the game
        Gdx.app.exit();
    }

    //the name is explicit enough
    private boolean areAllShipsPositioned() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                if (!inMap(shipSelectors[j][i])) {
                    return false;
                }
            }
        }
        return true;
    }

    //read the name and you will understand
    private void initListeners() {
        //Inits ship selectors' listeners
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                int finalJ = j;
                int finalI = i;
                shipSelectors[j][i].addListener(new InputListener() {
                    //Quando ci passi sopra con il mouse mette la manina
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        if (!gameLogic.hasGameStarted) //se non si è unito l'altro player non ti fa fare niente
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
                        if (inMap(img)) {
                            int col = (int) ((img.getX() - mapStartingX) / 70);
                            int row = (int) ((img.getY() - mapStartingY) / 70);
                            float xTemp = mapStartingX + col * 70;
                            float yTemp = mapStartingY + row * 70;
                            img.setPosition(xTemp, yTemp);
                        }

                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        held = false;
                        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
//                        System.out.println(img.getWidth());
//                        System.out.println(img.getHeight());
//                        System.out.println(img.getX() + " " +  img.getY());

                        if (inMap(img)) {
                            ArrayList<MapTile> navi;
                            navi = posizioneNave(img);
                            if (navi != null) {

                                for (int k = 0; k < navi.size(); k++) {
                                    navi.get(k).setOccupation(true);
                                    navi.get(k).setNave(img);
                                }
                                img.removeListener(this);
                            } else {
                                info.setText("LAST: Posizione non valida.");

                                img.setPosition(img.getInitialX(), img.getInitialY());
                                img.setRotation(0);
                            }
                        }
                        temp = img;

                    }
                });
            }
        }
        btnReady.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (areAllShipsPositioned()) {
                    //Ready solo se hai posizionato tutte le mappe
                    //Disattiva il timer dei 2 minuti e si rende invisibile

                    time.clear();
                    tempo.remove();
                    btnReady.removeListener(this);
                    btnReady.setVisible(false);
                    btnCambiaMappa.setVisible(true);
                    gameLogic.inizializzaMappa(mapIcons);

                    gameLogic.isPlayerReady = true;

                }

            }
        });

        btnReady.addListener(new InputListener() {
            //Quando ci passi sopra con il mouse mette la manina
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            }

            //Quando il mouse non è più sopra al "pulsante"
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        });

        btnCambiaMappa.addListener(new InputListener() {
            //Quando ci passi sopra con il mouse mette la manina
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            }

            //Quando il mouse non è più sopra al "pulsante"
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        });

        btnCambiaMappa.addListener((new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleMap();

            }
        }));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mapIconsAttack[j][i].addListener(new InputListener() {
                    //Quando ci passi sopra con il mouse mette la manina
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
                    }

                    //Quando il mouse non è più sopra al "pulsante"
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                    }
                });
                int finalJ = j;
                int finalI = i;
                mapIconsAttack[j][i].addListener((new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        if (!gameLogic.attaccoEseguito && !gameLogic.attaccoPianificato) {
                            info.setText("attacco in " + (finalJ+1) + " " + (finalI+1)); //TODO will change, to get adapted Adopted fuck you kid, you are adopted
                            gameLogic.attaccoPianificato = true;
                            gameLogic.coordinataAttacco = new Coordinata(finalI+1, finalJ+1);
                            mapIconsAttack[finalJ][finalI].removeListener(this);
                            mapIconsAttack[finalJ][finalI].setDrawable(new TextureRegionDrawable(new TextureRegion(attackMapTexture, finalI * multiplier, (9 - finalJ) * multiplier, multiplier, multiplier)));
                            confermaAttacco.setVisible(true);
                        }

                    }
                }));
            }
        }

        confermaAttacco.addListener(new InputListener() {
            //Quando ci passi sopra con il mouse mette la manina
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
            }

            //Quando il mouse non è più sopra al "pulsante"
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        });

        confermaAttacco.addListener((new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO mandare al server le coordinate salvate in precedenza.
                if(gameLogic.attaccoPianificato && !gameLogic.attaccoEseguito){

                }
                //TODO attesa dell'attacco avversario
                confermaAttacco.setVisible(false);

            }
        }));

    }

    private ArrayList<MapTile> posizioneNave(ShipTile img) {
        ArrayList<MapTile> tempMappe;
        if (img.getRotation() == 0) {
            int yIniziale = (int) img.getY();
            tempMappe = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    int mapY = (int) mapIcons[j][k].getY();
                    if (mapY == yIniziale && (img.getX() == mapIcons[j][k].getX())) {
                        if (!mapIcons[j][k].getOccupation()) {
                            for (int i = 0; i < img.getSize(); i++) {
                                if (!mapIcons[j + i][k].getOccupation()) {
                                    tempMappe.add(mapIcons[j + i][k]);

                                    int sum = j + i;
                                    //TODO salvare da qualche parte per poi passare al server le coordinate.
                                    info.setText("LAST: nave posizionata in " + sum + ":"+ k);
                                    System.out.println(sum + " ," + k);

                                } else return null;
                            }
                        } else {
                            return null;
                        }
                    }
                }
            }
            return tempMappe;
        } else if (img.getRotation() == 90) {
            int xIniziale = (int) img.getX() - (int) img.getHeight();
            tempMappe = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    int mapX = (int) mapIcons[j][k].getX();
                    if (mapX == xIniziale && (img.getY() == mapIcons[j][k].getY())) {
                        if (!mapIcons[j][k].getOccupation()) {
                            for (int i = 0; i < img.getSize(); i++) {
                                if (!mapIcons[j][k + i].getOccupation()) {
                                    tempMappe.add(mapIcons[j][k + i]);
                                    int sum = k + i;
                                    //TODO anche qui negro
                                    System.out.println(j + " ," + sum);
                                } else return null;

                            }
                        } else {

                            return null;
                        }
                    }
                }
            }
            return tempMappe;
        } else

            return null;
    }

    private boolean inMap(ShipTile img) {
        if (img.getRotation() == 0) {
            if (img.getX() >= mapStartingX
                    && img.getX() <= (mapStartingX + MAX_MAP_SIZE + 70)
                    && img.getY() >= mapStartingY
                    && img.getY() <= (mapStartingY + MAX_MAP_SIZE + 70)
                    && (img.getX() + img.getWidth()) >= mapStartingX
                    && (img.getX() + img.getWidth()) <= (mapStartingX + MAX_MAP_SIZE + 70)
                    && (img.getY() + img.getHeight()) >= mapStartingY
                    && (img.getY() + img.getHeight()) <= (mapStartingY + MAX_MAP_SIZE + 70)) {


                return true;
            } else return false;
        } else {
            if (img.getX() >= mapStartingX
                    && img.getX() <= (mapStartingX + MAX_MAP_SIZE + 70)
                    && img.getY() >= mapStartingY
                    && img.getY() < (mapStartingY + MAX_MAP_SIZE)
                    && (img.getX() - img.getHeight()) >= mapStartingX
                    && (img.getX() - img.getHeight()) <= (mapStartingX + MAX_MAP_SIZE + 70)
                    && (img.getY() + img.getWidth()) >= mapStartingY
                    && (img.getY() - img.getWidth()) < (mapStartingY + MAX_MAP_SIZE)) {
                return true;
            } else return false;
        }

    }

    public void colpitoFuoco(int x, int y) {
        //TODO creare una immagine di fuoco sulle coordinate, ovvero dove sono stato colpito


    }

}


