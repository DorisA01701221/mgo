package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import interfaz.GameState;
import singletons.ImageLoader;

public class Turno1 implements GameState{
	private GameContext gc;//cambio de estados
	private ImageLoader img;
	private BufferedImage cerdo, jirafa, vaca, ask, c,j,v, avatar1, avatar3, avatar4, avatar5;
	public Turno1(GameContext gc) {
		this.gc=gc;
		//accediendo a imageloader
		img=ImageLoader.getImageLoader();
		cerdo =  img.getImage("cerdo");
		jirafa = img.getImage("jirafa");
		vaca = img.getImage("vaca");
		ask = img.getImage("ask");
		c = img.getImage("c");
		j = img.getImage("j");
		v = img.getImage("v");
		avatar1 = img.getImage("avatar1");
		avatar3 = img.getImage("avatar3");
		avatar4 = img.getImage("avatar4");
		avatar5 = img.getImage("avatar5");
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
		g.drawImage(cerdo, 100, 60,121 , 121, null);
		g.drawImage(jirafa,373, 60,121 , 121,null);
		g.drawImage(vaca, 645, 60, 121 , 121,null);
		g.drawImage(ask, 0, 0, null);
		g.drawImage(c, 0, 0, null);
		g.drawImage(j, 0, 0, null);
		g.drawImage(v, 0, 0, null);
		g.drawImage(avatar1, 0, 0, null);
		g.drawImage(avatar3, 0, 0, null);
		g.drawImage(avatar4, 0, 0, null);
		g.drawImage(avatar5, 0, 0, null);
	}

	@Override
	public void update() {
	//actualiza	
		
	}
	
}
