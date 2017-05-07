package com.ephemerality.aphelion.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.ephemerality.aphelion.util.Direction;

public class ScreenManager {
	
	SpriteBatch sb;
	ShapeRenderer sr;
	OrthographicCamera oc;
	Rectangle bounds;
	public static Color color;
	
	
	public ScreenManager() {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		oc = new OrthographicCamera();
		bounds = new Rectangle();
		resize();
		color = new Color(0f, 0f, 0f, 1f);
	}
	
	public void resize() {
		oc.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		oc.viewportHeight = Gdx.graphics.getWidth();
		oc.viewportWidth = Gdx.graphics.getHeight();
		bounds.setWidth(Gdx.graphics.getWidth());
		bounds.setHeight(Gdx.graphics.getHeight());
		update();
	}
	public void resize(int width, int height) {
		oc.viewportHeight = height;
		oc.viewportWidth = width;
		oc.setToOrtho(false, width, height);
		bounds.setWidth(width);
		bounds.setHeight(height);
		update();
	}
	public void setPosition(float x, float y) {
		oc.position.set(x, y, 0f);
		float xb = (float) (x - Math.floor(bounds.width / 2));
		float yb =  (float) (y - Math.floor(bounds.height / 2));
		bounds.setPosition(xb, yb);
		update();
	}
	
	public void start() {
		Gdx.gl.glClearColor(color.r, color.b, color.g, color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		sb.enableBlending();
		sb.begin();
	}
	
	public void finish() {
		Rectangle scissors = new Rectangle();
		ScissorStack.calculateScissors(oc, sb.getTransformMatrix(), bounds, scissors);
		ScissorStack.pushScissors(scissors);
		sb.end();
	}
	
	public void update() {
		oc.update();
		sr.setProjectionMatrix(oc.combined);
		sb.setProjectionMatrix(oc.combined);
	}
	public void translate(float x, float y) {
		oc.translate(x, y);
		bounds.setPosition(bounds.x + x, bounds.y + y);
		
	}
	public void translate(Direction dir) {
		if(dir == Direction.NORTH) {
			translate(0, 1);
		}else if(dir == Direction.SOUTH) {
			translate(0, -1);
		}else if(dir == Direction.WEST) {
			translate(-1, 0);
		}else if(dir == Direction.EAST){
			translate(1, 0);		
		}else {
			System.out.println("Unhandeled direction");
		}
	}
	public void render(TextureRegion texture, float x, float y) {
		sb.draw(texture, x, y);
	}
	public void render(Texture texture, float x, float y, float w, float h) {
		sb.draw(texture, x, y, w, h);
	}
	public void render(Texture texture, Rectangle body) {
		sb.draw(texture, body.x, body.y, body.width, body.height);
	}
	public void render(TextureRegion texture, Rectangle body) {
		sb.draw(texture, body.x, body.y, body.width, body.height);
	}
	public void renderFixed(Texture texture, Rectangle body) {
		sb.draw(texture, bounds.x + body.x, bounds.y + body.y, body.width, body.height);
	}
	public void renderFixed(TextureRegion texture, Rectangle body) {
		sb.draw(texture, bounds.x + body.x, bounds.y + body.y, body.width, body.height);
	}
	public void renderFixed(Texture texture, float x, float y, float scale) {
//		TODO: verify this draw call as accurate
		sb.draw(texture, x, y, texture.getWidth() * scale, texture.getHeight() * scale);
	}
	public void renderFixed(TextureRegion texture, float x, float y, float scale) {
//		TODO: verify this draw call as accurate
		sb.draw(texture, x, y, texture.getRegionWidth() * scale, texture.getRegionHeight() * scale);
	}
	
	public void dispose() {
		sb.dispose();
		sr.dispose();
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public SpriteBatch getSpriteBatch() {
		return sb;
	}
	
	public ShapeRenderer getShapeRenderer() {
		return sr;
	}
	
	public OrthographicCamera getOrthographicCamera() {
		return oc;
	}
}
