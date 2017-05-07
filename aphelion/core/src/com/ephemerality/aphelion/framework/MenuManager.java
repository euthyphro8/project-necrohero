package com.ephemerality.aphelion.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.ephemerality.aphelion.graphics.LoadManager;
import com.ephemerality.aphelion.graphics.ScreenManager;
import com.ephemerality.aphelion.util.debug.Debug;

public class MenuManager {

	private BitmapFont one, two, three, four, five;
	private Color regular, hover;
	private float topinset, leftinset;
	private int selection = -1;
	private int FONT_SIZE = 60;
	
	//0 for main, 1 for start game, 2 for load game, 3 for options
	private states state = states.main;
	private enum states {main, start, load, options};
	private int max_selection;
	
	public MenuManager() {
		regular = new Color(0.9f, 0.9f, 0.8f, 1.0f);
		hover = new Color(0.5f, 0.5f, 0.4f, 1.0f);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/captain-falcon_mecha/Mecha_Condensed_Bold.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = FONT_SIZE;
		parameter.color = regular;
		one = generator.generateFont(parameter);
		two = generator.generateFont(parameter);
		three = generator.generateFont(parameter);
		four = generator.generateFont(parameter);
		five = generator.generateFont(parameter);
		generator.dispose();

		one.setColor(regular);
		two.setColor(regular);
		three.setColor(regular);
		four.setColor(regular);
		five.setColor(regular);
		
		topinset = (Gdx.graphics.getHeight() * 0.8f);
		leftinset = (Gdx.graphics.getWidth() * 0.45f);
		
		max_selection = 2;
	}
	
	
	public void update() {
		boolean up = Gdx.input.isKeyJustPressed(Input.Keys.UP) | Gdx.input.isKeyJustPressed(Input.Keys.W) | Gdx.input.isKeyJustPressed(Input.Keys.DPAD_UP);
		boolean down = Gdx.input.isKeyJustPressed(Input.Keys.DOWN) | Gdx.input.isKeyJustPressed(Input.Keys.S) | Gdx.input.isKeyJustPressed(Input.Keys.DPAD_DOWN);
		boolean enter = Gdx.input.isKeyJustPressed(Input.Keys.ENTER);
		if(!(up && down)) {
			if(down && selection < max_selection) {
				selection++;
				setFontColors();
			}else if(up && selection > 0) {
				selection--;
				setFontColors();
			}			
		}
		if(enter && !Debug.active) {
			if(state == states.main) {
				if(selection == 0) {
					//Start
					setState(states.start);
				}else if(selection == 1) {
					//Options
					setState(states.options);
				}else if(selection == 2) {
					//Quit
					Master.setState(-1);
				}				
			}
			else if(state == states.start) {	
				if(selection == 0) {
					//New
					Master.setState(2);
				}else if(selection == 1) {
					//Load
					setState(states.load);
				}else if(selection == 2) {
					//Back
					Master.setState(-1);
				}				
			}
			else if(state == states.load) {	
				if(selection == 0) {
					Master.setState(2);
				}else if(selection == 1) {
					Master.setState(2);
				}else if(selection == 2) {
					Master.setState(2);
				}				
			}
			else if(state == states.options) {	
				if(selection == 0) {
					//Controls
				}else if(selection == 1) {
					//Graphics
				}else if(selection == 2) {
					//Sound
				}else if(selection == 3) {
					//Back
					setState(states.main);
				}							
			}
		}
	}
	public void setState(states state) {
		if(state == states.main || state == states.start) max_selection = 2;
		else if(state == states.options || state == states.load) max_selection = 3;
		this.state = state;
		selection = -1;
		setFontColors();		
	}
	
	public void setFontColors() {
		one.setColor(regular);
		two.setColor(regular);
		three.setColor(regular);
		four.setColor(regular);
		five.setColor(regular);
		
		if(selection == 0) one.setColor(hover);
		else if(selection == 1) two.setColor(hover);
		else if(selection == 2) three.setColor(hover);
		else if(selection == 3) four.setColor(hover);
		else if(selection == 4) five.setColor(hover);
		
	}
	
	public void render(ScreenManager screen, LoadManager assets) {
//		screen.render(assets.getTexture(assets.MENU_FRAME), 100, 100, 50f, 50f);
		
		//Main
		if(state == states.main) {
			one.draw(screen.getSpriteBatch(), "Play", leftinset, topinset);
			two.draw(screen.getSpriteBatch(), "Options", leftinset, topinset - 100);
			three.draw(screen.getSpriteBatch(), "Quit", leftinset, topinset - 200);			
		}
		//Start
		else if(state == states.start) {
			one.draw(screen.getSpriteBatch(), "New Game", leftinset, topinset);
			two.draw(screen.getSpriteBatch(), "Load Game", leftinset, topinset - 100);
			three.draw(screen.getSpriteBatch(), "Back", leftinset, topinset - 200);			
		}
		//Load
		else if(state == states.load) {	
			one.draw(screen.getSpriteBatch(), "Selection One", leftinset, topinset);
			two.draw(screen.getSpriteBatch(), "Selection Two", leftinset, topinset - 100);
			three.draw(screen.getSpriteBatch(), "Selection Three", leftinset, topinset - 200);
			four.draw(screen.getSpriteBatch(), "Back", leftinset, topinset - 300);		
		}
		//Options
		else if(state == states.options) {	
			one.draw(screen.getSpriteBatch(), "Controls", leftinset, topinset);
			two.draw(screen.getSpriteBatch(), "Graphics", leftinset, topinset - 100);
			three.draw(screen.getSpriteBatch(), "Sound", leftinset, topinset - 200);
			four.draw(screen.getSpriteBatch(), "Back", leftinset, topinset - 300);		
		}
	}
	
	public void dispose() {
		one.dispose();
		two.dispose();
		three.dispose();
	}
	
}
