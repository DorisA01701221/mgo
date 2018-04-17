package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import interfaz.GameState;
import singletons.ImageLoader;
import singletons.StateFactory;

public class GameContext {
	private ImageLoader imsLoader= ImageLoader.getImageLoader();
	private BufferedImage bg =imsLoader.getImage("background");
	private StateFactory factory;
		
	public GameContext() {
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
		factory.getCurrent().render(g);
		
	}
}
