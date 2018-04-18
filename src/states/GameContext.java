package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import interfaz.GameState;
import singletons.ImageLoader;
import singletons.StateFactory;

public class GameContext {
	private ImageLoader imsLoader= ImageLoader.getImageLoader();
	private BufferedImage bg;
	private BufferedImage HUDcorrecto;
	private StateFactory factory;
	private int x;
	private int y;
		
	public GameContext() {
		//es de que no he reconocido un click
		x=-1;
		y=-1;
		bg =imsLoader.getImage("background");
		HUDcorrecto= imsLoader.getImage("HUD");
		factory = StateFactory.getStateFactory(this);
		
	}
	public GameState getStart() {
		return factory.getStart();
	}
	public GameState getTurno1() {
		return factory.getTurno1();
	}
	public GameState getTurno2() {
		return factory.getTurno2();
	}
	public GameState getPaused() {
		return factory.getPaused();
	}
	public GameState getOvered() {
		return factory.getOvered();
	}
	public void setCurrent(GameState gs) {
		factory.setCurrent(gs);
	}
	public void update() {
		factory.getCurrent().update();
	}
	public void render(Graphics g){
		g.drawImage(bg, 0, 0,null);
		//aqui aparece el HUD
		//g.drawImage(HUDcorrecto,5,5,856,42, null);
		factory.getCurrent().render(g);
		
		
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
