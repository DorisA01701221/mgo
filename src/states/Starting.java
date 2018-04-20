package states;
import java.awt.*;
import java.util.logging.*;

import Interfaces.*;
import singletons.*;

public class Starting implements GameState {
	private ImageLoader img;
	private GameContext gc;
	private int seconds;//el primer contador de 5..4...3..2..1..0
	//constructor
	public Starting(GameContext gc) {
		this.gc = gc;
		img = ImageLoader.getLoader();
		seconds = 5;
	}
	@Override
	public void starting() { }
	@Override
	public void turn1() {
		if(seconds < 0) {
			gc.setCurrent(gc.getTurn1());
		}
	}
	@Override
	public void turn2() { }
	@Override
	public void pause() { }
	@Override
	public void over() { }
	@Override
	public void render(Graphics g) {
		g.drawImage(img.getImage("segundo" + Integer.toString(seconds)), 318, 108, null);
	}
	@Override
	public void update() {
		try {
			Thread.sleep(1000);
		}catch(InterruptedException ex){
			Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
		}
		seconds--;
		turn1();
	}
}
