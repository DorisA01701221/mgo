package states;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.*;
import javax.swing.Timer;
import Interfaces.*;
import singletons.*;

public class Starting implements GameState {
	private ImageLoader img;
	private GameContext gc;
	private int seconds;//el primer contador de 5..4...3..2..1..0
	private Timer t; 
	private ActionListener action; //detectar que ha pasado un seg
	//constructor
	public Starting(GameContext gc) {
		this.gc = gc;
		img = ImageLoader.getLoader();
		seconds = 5;
		action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				seconds--;
			}
		};
		t=new Timer(1000, action);
		t.start();
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
	public void render(Graphics g) { g.drawImage(img.getImage("segundo" + Integer.toString(seconds)), 318, 108, null);}
	@Override
	public void update() { turn1(); }
}
