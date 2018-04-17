package singletons;

import interfaz.GameState;
import states.GameContext;
import states.Overrr;
import states.Pause;
import states.Starting;
import states.Turno1;
import states.Turno2;

public class StateFactory {
	private Starting start;
	private Turno1 turno1;
	private Turno2 turno2;
	private Pause paused;
	private Overrr overed;
	private GameState current;
	
	private static StateFactory factory;
	
	private StateFactory(GameContext gc) {
		start = new Starting (gc);
		turno1= new Turno1(gc);
		turno2= new Turno2(gc);
		paused= new Pause(gc);
		overed= new Overrr(gc);
		current = start;
	}
	public static StateFactory getStateFactory(GameContext gc) {
		if (factory == null) {
			factory= new StateFactory(gc);
		} return factory;
	}
	public static StateFactory getStateFactory() {
		return factory;
	}
	public Starting getStart() {
		return start;
	}

	public Turno1 getTurno1() {
		return turno1;
	}

	public Turno2 getTurno2() {
		return turno2;
	}

	public Pause getPaused() {
		return paused;
	}

	public Overrr getOvered() {
		return overed;
	}

	public void setCurrent(GameState gs) {
		this.current=gs;
	}
	public GameState getCurrent() {
		return current;
	}
	
}
