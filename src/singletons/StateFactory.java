package singletons;
import Interfaces.*;
import states.*;

public class StateFactory {
	private static StateFactory states;
	private GameContext gc;
	//constructor
	private StateFactory(GameContext gc) {
		this.gc =gc;
	}
	public static StateFactory getStates(GameContext gc) {
		//si estados no existe lo crea
		if(states == null) {
			states = new StateFactory(gc);
		}
		//devuelve estado de factory
		return states;
	}
	public GameState createState(int state) {
		switch(state) {
			case 0: return new Starting(gc);
			case 1:	return new Turn1(gc);
			case 2: return new Turn2(gc);
			case 3: return new Pause(gc);
			case 4: return new Over(gc);
		}
		return null;
	}
	
}
