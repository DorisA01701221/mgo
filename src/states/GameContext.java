package states;

import interfaz.GameState;
import singletons.StateFactory;

public class GameContext {
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
	//poner update y render
}
