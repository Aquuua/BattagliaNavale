package com.gruppo1.battaglianavale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen extends ScreenAdapter {

    private BattagliaNavale game;



    private Label.LabelStyle lblStyle;
    private Label fpsCounter;

    private Stage mainMenu;


    public MenuScreen(BattagliaNavale game){
        this.game = game;


        lblStyle = new Label.LabelStyle();
        lblStyle.font = game.font12;


        fpsCounter = new Label("Fps: ", lblStyle);



    }

    @Override
    public void show() {
        super.show();
        mainMenu = new Stage();
        mainMenu.addActor(fpsCounter);
        fpsCounter.setPosition(mainMenu.getWidth()- 50, mainMenu.getHeight() - 15);

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


}
