package Interfaces;
import java.awt.Graphics;

public interface GameState {
	public void starting();
	public void turn1();
	public void turn2();
	public void pause();
	public void over();
	
	public void render(Graphics g);
	public void update();
}
