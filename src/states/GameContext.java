package states;
import java.awt.*;

import Interfaces.*;
import singletons.*;

public class GameContext {
	private ImageLoader img;
	private StateFactory states;
	private Chronometer chronometer;
	private int x;
	private int y;
	private int winner;//saber que jugador gano
	private int resume;
	//constructor
	public GameContext() {
		img = ImageLoader.getLoader();//recupera el loader
		states = StateFactory.getStates(this);//obtiene el estado
		chronometer = Chronometer.getChronometer();//recupero el cronometro
		winner = -1;//inicio en -1 porque no se sabe aun quien haganado
		resume = -1;//saber a que turno regresar
	}
	//getters & setters
	public int getX(){ return x; }
	public void setX(int x){ this.x = x; }
	public int getY(){ return y; }
	public void setY(int y){ this.y = y; }
	public int getWinner(){ return winner; }
	public void setWinner(int winner){ this.winner = winner; }
	public int getResume(){ return resume; }
	public void setResume(int resume){ this.resume = resume; }
	public Chronometer getChronometer(){ return chronometer; }
	public void setCurrent(GameState gs){ states.setCurrent(gs); }
	public GameState getStart(){ return states.getStart(); }
	public GameState getTurn1(){ return states.getTurn1(); }
	public GameState getTurn2(){ return states.getTurn2(); }
	public GameState getPause(){ return states.getPaused(); }
	public GameState getOver(){ return states.getOvered(); }
	public void render(Graphics g) {//depende del estado
		g.drawImage(img.getImage("background"), 0, 0, null);
		states.getCurrent().render(g);
	}
	public void update() {//depende del estado
		states.getCurrent().update();
	}
}
