package com.ephemerality.aphelion.spawn.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.ephemerality.aphelion.graphics.LoadManager;
import com.ephemerality.aphelion.graphics.ScreenManager;
import com.ephemerality.aphelion.input.Save;
import com.ephemerality.aphelion.spawn.entities.nob.Environment;
import com.ephemerality.aphelion.spawn.entities.player.Player;
import com.ephemerality.aphelion.spawn.world.MapManager;
import com.ephemerality.aphelion.spawn.world.QuadTree;

public class EntityManager {
	
	public Vector2 deltaOffset;
	public List<Entity> entities;
//	public List<ParticleSpawner> particles;
	public List<Object> removed;
	
	public QuadTree quad;
	public Player player;
	public MapManager map;
	
	/**
	 * Constructor for usage of the editor
	 * @param map
	 */
	public EntityManager(MapManager map) {
		this.map = map;
		deltaOffset = new Vector2();
		entities = new ArrayList<>();
		removed = new ArrayList<>();
		quad = new QuadTree(map);
		initEnvNobs();
	}
	
	public EntityManager(ScreenManager screen, LoadManager assets, MapManager map) {
		this.map = map;
		deltaOffset = new Vector2();
		entities = new ArrayList<>();
		removed = new ArrayList<>();
		quad = new QuadTree(map);
		player = new Player(screen, assets, 1000, 1000);
		initEnvNobs();
		addEntity(player);
	}
	public EntityManager(ScreenManager screen, LoadManager assets, MapManager map, Save save) {
		this.map = map;
		deltaOffset = new Vector2();	
		entities = new ArrayList<>();
		removed = new ArrayList<>();
		quad = new QuadTree(map);
		player = new Player(screen, assets, save.player);
		initEnvNobs();
	}
	public void initEnvNobs() {
		Iterator<Vector2> vIter = map.level.env.keySet().iterator();
		while(vIter.hasNext()) {
			Vector2 vect = vIter.next();
			addEntity(Environment.instantiateEnvNob(vect.x, vect.y, map.level.env.get(vect)));
		}
	}
	public void resetMapInitEnvs() {
		entities = new ArrayList<>();
		removed = new ArrayList<>();
		quad = new QuadTree(map);
		Iterator<Vector2> vIter = map.level.env.keySet().iterator();
		while(vIter.hasNext()) {
			Vector2 vect = vIter.next();
			addEntity(Environment.instantiateEnvNob(vect.x, vect.y, map.level.env.get(vect)));
		}
	}
	public void refreshQuad() {
		quad = new QuadTree(map);
		for(Entity e : entities) 
			quad.addEntity(e);
	}
		
	public void update() {
		for(Entity e : entities)
			e.update();
		
		for(Entity e : entities)
			if(e.isRemoved)
				removed.add(e);
		
		entities.removeAll(removed);
		removed.clear();
		quad.update();
		
//		for(ParticleSpawner ps : particles) 
//			ps.update();
		Collections.sort(entities);
	}
	
//	TODO: give conditionals to entities for scripts...etc
	public void addEntity(Entity e) {
		quad.addEntity(e);
		entities.add(e);
	}
	
	public void render(ScreenManager screen) {
//		quad.render(screen);
		for(Entity e : entities) 
			e.render(screen);
//		for(ParticleSpawner ps : particles) 
//			ps.render(screen);
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
