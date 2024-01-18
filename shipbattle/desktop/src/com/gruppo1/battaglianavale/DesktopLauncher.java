package com.gruppo1.battaglianavale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.gruppo1.battaglianavale.BattagliaNavale;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		Graphics.DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setWindowIcon("textures/icon.png");

		config.setWindowedMode(dm.width/12*10,dm.height/12*10);

		config.setForegroundFPS(dm.refreshRate);
		config.setTitle("BattagliaNavale");
		config.setResizable(false);

		new Lwjgl3Application(new BattagliaNavale(), config);
	}
}
