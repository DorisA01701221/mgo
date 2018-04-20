package singletons;
import Interfaces.*;
import states.*;

public class StateFactory {
	private static StateFactory states;
	private Starting start;
	private Turn1 turn1;
	private Turn2 turn2;
	private Pause paused;
	private Over overed;
	private GameState current;
	//constructor
	private StateFactory(GameContext gc) {
		start = new Starting (gc);
		turn1= new Turn1(gc);
		turn2= new Turn2(gc);
		paused= new Pause(gc);
		overed= new Over(gc);
		current = start;
	}
	public static StateFactory getStates(GameContext gc) {
		//si estados no existe lo crea
		if(states == null) {
			states = new StateFactory(gc);
		}
		//devuelve estado de factory
		return states;
	}
	//solo getters
	public Starting getStart() { return start; }
	public Turn1 getTurn1() { return turn1; }
	public Turn2 getTurn2() { return turn2; }
	public Pause getPaused() { return paused; }
	public Over getOvered() { return overed; }
	public GameState getCurrent() { return current;	}
	public void setCurrent(GameState current) { this.current = current; }
}
