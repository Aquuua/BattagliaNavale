package com.gruppo1.battaglianavale;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gruppo1.battaglianavale.Custom.DigitFilter;

public class MenuScreen extends ScreenAdapter {

    private BattagliaNavale game;

    private Label.LabelStyle lblStyle;

    private Label.LabelStyle lblStyle18;

    private Label.LabelStyle lblStyleTitle;
    private Label fpsCounter;
    private Label ipAddr;
    private Label title;
    private Label indicazioni;

    private TextField ipField;

    private Image btnStartNewGame;
    private Image btnEnterGame;

    private Stage mainMenu;


    public MenuScreen(BattagliaNavale game){
        this.game = game;
        //Crea il pulsante Start
        btnStartNewGame = new Image(new Texture(Gdx.files.internal("textures/pulsanteCrea.png")));
        btnStartNewGame.setScale(0.3f);
        btnEnterGame = new Image(new Texture(Gdx.files.internal("textures/pulsanteEntra.png")));
        btnEnterGame.setScale(0.3f);





        //DA FINIRE, ora è inutile lol
        ipField = new TextField("", game.defaultSkin);
        ipField.setSize(310, 40);
        ipField.setAlignment(1);
        ipField.setMaxLength(16);
        ipField.setMessageText("Indirizzo IP");
        ipField.setTextFieldFilter(new DigitFilter());


        lblStyle = new Label.LabelStyle();
        lblStyle.font = game.font12;

        lblStyle18 = new Label.LabelStyle();
        lblStyle18.font = game.font18;

        lblStyleTitle = new Label.LabelStyle();
        lblStyleTitle.font = game.fontTitle;
        fpsCounter = new Label("Fps: ", lblStyle);
        ipAddr = new Label("IP: "+game.theGame.getLocalAddress(), lblStyle18);

        title = new Label("Battaglia Navale", lblStyleTitle);
        indicazioni = new Label("Oppure inserisci l'IPV4 dell'host!", lblStyle18);

        this.InitListeners();
    }

    @Override
    public void show() {
        super.show();
        mainMenu = new Stage();
        Gdx.input.setInputProcessor(mainMenu);
        mainMenu.addActor(fpsCounter);
        mainMenu.addActor(ipAddr);
        mainMenu.addActor(ipField);
        mainMenu.addActor(btnStartNewGame);
        mainMenu.addActor(title);
        mainMenu.addActor(btnEnterGame);
        mainMenu.addActor(indicazioni);

        //Posizioni degli attori (oggetti) su schermo
        fpsCounter.setPosition(mainMenu.getWidth()- 50, mainMenu.getHeight() - 15);

        ipAddr.setPosition(mainMenu.getWidth()/2 - ipAddr.getWidth()/2, 20);

        ipField.setPosition(mainMenu.getWidth()/2 - ipField.getWidth()/2, mainMenu.getHeight()-400);

        btnStartNewGame.setPosition(mainMenu.getWidth()/2-btnStartNewGame.getWidth()*btnStartNewGame.getScaleX()/2, mainMenu.getHeight()-300);
        btnEnterGame.setPosition(mainMenu.getWidth()/2-btnEnterGame.getWidth()*btnEnterGame.getScaleX()/2, mainMenu.getHeight()-500);
        indicazioni.setPosition(mainMenu.getWidth()/2 - indicazioni.getWidth()/2, mainMenu.getHeight()-350);
        title.setPosition(mainMenu.getWidth()/2-title.getWidth()/2, mainMenu.getHeight()-50);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(Color.WHITE);
        fpsCounter.setText("Fps: "+ Gdx.graphics.getFramesPerSecond());

        game.batch.begin();
        game.batch.end();


        mainMenu.act();
        mainMenu.draw();

    }

    @Override
    public void dispose(){

        mainMenu.dispose();
    }

    public void InitListeners(){
        btnStartNewGame.addListener(new InputListener() {
            //Quando ci passi sopra con il mouse mette la manina
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);

            }
            //Quando il mouse non è più sopra al "pulsante"
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        });
        btnStartNewGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.theGame.initGame();//in realtà fa tutto lì
                game.setScreen(game.gameScreen);
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                mainMenu.dispose();
            }
        });

        btnEnterGame.addListener(new InputListener() {
            //Quando ci passi sopra con il mouse mette la manina
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);

            }
            //Quando il mouse non è più sopra al "pulsante"
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        });
        btnEnterGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String ip= "127.0.0.1";
                if(!ipField.getText().equals("")){

                    ip = ipField.getText();
                    if(game.theGame.entraNellaPartita(ip)){
                        if(game.theGame.enterGame(ip, GameLogic.getDefaultPort())){
                            game.setScreen(game.gameScreen);
                            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                            mainMenu.dispose();
                        }


                    }
                }



//                game.setScreen(game.gameScreen);
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
//                mainMenu.dispose();
            }
        });

        ipField.addListener(new InputListener() {
            //Quando ci passi sopra con il mouse mette la manina
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Ibeam);

            }
            //Quando il mouse non è più sopra al "pulsante"
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
            }
        });


    }



}
