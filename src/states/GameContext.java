package states;
import java.awt.*;

import Interfaces.*;
import singletons.*;

public class GameContext {
	private ImageLoader img;
	private StateFactory statesFactory;
	private Chronometer chronometer;
	private GameState[] states;
	private GameState current;
	private int x;
	private int y;
	private int winner;//saber que jugador gano
	private int resume;
	//constructor
	public GameContext() {
		img = ImageLoader.getLoader();//recupera el loader
		statesFactory = StateFactory.getStates(this);//obtiene el estado
		chronometer = Chronometer.getChronometer();//recupero el cronometro
		states= new GameState[5];
		for(int i=0;i<5;i++) {
			states[i] = statesFactory.createState(i);
			System.out.println(states[i].getClass());
		}
		current = states[0];
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
	public void setCurrent(GameState gs){ current = gs; }
	public GameState getStart(){ return states[0]; }
	public GameState getTurn1(){ return states[1]; }
	public GameState getTurn2(){ return states[2]; }
	public GameState getPause(){ return states[3]; }
	public GameState getOver(){ return states[4]; }
	public void render(Graphics g) {//depende del estado
		g.drawImage(img.getImage("background"), 0, 0, null);
		current.render(g);
	}
	public void update() {//depende del estado
		current.update();
	}
}
