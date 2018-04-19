package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import interfaz.GameState;
import singletons.ImageLoader;
import singletons.StateFactory;

public class GameContext {
	private ImageLoader imsLoader= ImageLoader.getImageLoader();
	private BufferedImage bg;
	private StateFactory factory;
	private int x;
	private int y;
	private ArrayList<Integer> correctasPlayer1;
	private ArrayList<Integer> correctasPlayer2;	

	public GameContext() {
		//es de que no he reconocido un click
		x=-1;
		y=-1;

		bg =imsLoader.getImage("background");
		factory = StateFactory.getStateFactory(this);
		//para que se muestre el hud (aqui aun no semuestra)
		correctasPlayer1= new ArrayList<Integer> ();
		correctasPlayer2= new ArrayList<Integer> ();

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
	public ArrayList<Integer> getCorrectasPlayer1() {
		return correctasPlayer1;
	}
	public void setCorrectasPlayer1(ArrayList<Integer> correctasPlayer1) {
		this.correctasPlayer1 = correctasPlayer1;
	}
	public ArrayList<Integer> getCorrectasPlayer2() {
		return correctasPlayer2;
	}
	public void setCorrectasPlayer2(ArrayList<Integer> correctasPlayer2) {
		this.correctasPlayer2 = correctasPlayer2;
	}
}
