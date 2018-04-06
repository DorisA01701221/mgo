package interfaz;

import java.awt.Graphics;

public interface GameState {
	public void overrr();
	public void pause();
	public void turno1();
	public void turno2();
	
	public void render(Graphics g);
	public void update();
}
