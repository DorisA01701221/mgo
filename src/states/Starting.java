package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import interfaz.GameState;
import singletons.ImageLoader;

public class Starting implements GameState {
	private GameContext gc;
	private ImageLoader imsLoader= ImageLoader.getImageLoader();
	private BufferedImage segundo;
	private int segundos;

	public Starting(GameContext gc) {
		this.gc=gc;
		segundos= 6;
	}
	public void  starting() {
		if(segundos < 0) {
			gc.setCurrent(gc.getTurno1());
		}else {
			segundo=imsLoader.getImage("segundo"+ Integer.toString(segundos));
		}
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
		g.drawImage(segundo, 318, 108,null); //114.5
	}

	@Override
	public void update() {
		try {
			Thread.sleep(1000);
		}catch(InterruptedException ex){
			Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		segundos -= 1;
		starting();
	}

}
