package states;
import java.awt.*;
import Interfaces.*;
import singletons.*;

public class Over implements GameState{
	private GameContext gc;
	private ImageLoader img;
	//constructor
	public Over(GameContext gc) {
		this.gc = gc;
		img = ImageLoader.getLoader();
	}
	@Override
	public void starting() { }
	@Override
	public void turn1() { }
	@Override
	public void turn2() { }
	@Override
	public void pause() { }
	@Override
	public void over() { }
	@Override
	public void render(Graphics g) {
		if(gc.getWinner() == 1) {
			g.drawImage(img.getImage("overU"),0,0,866,445, null);//imprime el over para el avatar de unicornio 
		}
		else {
			g.drawImage(img.getImage("overE"), 0, 0,866,445,null);//sino, imprime el over para el avatar de emoji
		}
	}
	@Override//update
	public void update() { }
}
