package states;
import java.awt.Graphics;

import extras.Animal;
import interfaz.GameState;
import singletons.ImageLoader;
public class Pause implements GameState{
	private GameContext gc;
	private ImageLoader img;
	private Animal volver, salir;
	
	public Pause(GameContext gc) {
		this.gc=gc;
		img=ImageLoader.getImageLoader();
		volver=new Animal(img.getImage("volverB"),312,100,242,80);
		salir=new Animal(img.getImage("salirB"),312,230,242,120);
	}

	@Override
	public void overrr() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void turno1() {
		gc.setCurrent(gc.getTurno1());
	}

	@Override
	public void turno2() {
		gc.setCurrent(gc.getTurno2());
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img.getImage("pausa"), 0,0, 866,445,null);
		g.drawImage(img.getImage("volverB"),312,100, 242, 80,null);
		g.drawImage(img.getImage("salirB"),312,230,242, 120,null);
	}

	@Override
	public void update() {
		//detecta si hizo click
		if(gc.getX() != -1 && gc.getY() != -1) {
			if(volver.getRec().contains(gc.getX(), gc.getY())) {
				//reiniciando variables delos clicks
				gc.setX(-1);
				gc.setY(-1);
				//regresa depende del turno al que le toca
				if(gc.getResume()==1) {
					turno1();
				}else {
					turno2();
				}
			}
			if(salir.getRec().contains(gc.getX(), gc.getY())) {
				//reiniciando variables delos clicks
				gc.setX(-1);
				gc.setY(-1);
				System.exit(0);
			}
			//reiniciando variables de los clicks
			gc.setX(-1);
			gc.setY(-1);
		}
	}

	
}
