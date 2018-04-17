package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import interfaz.GameState;
import singletons.ImageLoader;

public class Turno1 implements GameState{
	private GameContext gc;//cambio de estados
	private ImageLoader img;
	private BufferedImage cerdo, jirafa, vaca;
	public Turno1(GameContext gc) {
		this.gc=gc;
		//accediendo a imageloader
		img=ImageLoader.getImageLoader();
		cerdo =  img.getImage("cerdo");
		jirafa = img.getImage("jirafa");
		vaca = img.getImage("vaca");
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
	//dibuja la imagen si hay algo que no se dibujó no se renderisa	
		g.drawImage(cerdo, 50, 108,242 , 242, null);
		g.drawImage(jirafa, 312, 108,242 , 242,null);
		g.drawImage(vaca, 574, 108, 242 , 242,null);
	}

	@Override
	public void update() {
	//actualiza	
		
	}
	
}
