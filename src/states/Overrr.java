package states;

import java.awt.Graphics;

import interfaz.GameState;
import singletons.ImageLoader;

public class Overrr implements GameState{
	private GameContext gc;
	private ImageLoader img;

	public Overrr (GameContext gc) {
		this.gc=gc;
		img = ImageLoader.getImageLoader();
	}


	@Override
	public void overrr() {


	}

	@Override
	public void pause() {


	}

	@Override
	public void turno1() {


	}

	@Override
	public void turno2() {


	}

	@Override
	public void render(Graphics g) {
		if(gc.getCorrectasPlayer1().size() == 3) {
			g.drawImage(img.getImage("overU"),0,0,866,445, null);
		}
		else {
			g.drawImage(img.getImage("overE"), 0, 0,866,445,null);
		}

	}

	@Override
	public void update() {

	}

}
