package com.ephemerality.aphelion.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.ephemerality.aphelion.graphics.ScreenManager;

public class Overlay {
	
	private Pixmap healthBar;
	private Texture health;
	private Vector2 resolution;
	private double fill;
	
	public Overlay() {
		resolution = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		healthBar = new Pixmap(200, 10, Pixmap.Format.RGB888);
		healthBar.setColor(1f, 0, 0, 0.75f);
		healthBar.fillRectangle(0, 0, 200, 10);
		health = new Texture(healthBar);
	}
	
	
	public void render(ScreenManager screen) {
		int x = 10;
		int y = (int) resolution.y - 30;
		screen.render(health, x, y, (int)(200 * fill), 10);
		
	}
	
}