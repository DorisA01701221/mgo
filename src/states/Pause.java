package states;
import java.awt.*;

import Interfaces.*;
import extras.*;
import singletons.*;

public class Pause implements GameState {
	private GameContext gc;
	private ImageLoader img;
	private Collider back, exitGame;
	//constructor
	public Pause(GameContext gc) {
		this.gc = gc;
		img=ImageLoader.getLoader();
		back = new Collider(312, 100, 242, 80);
		exitGame = new Collider(312, 230, 242, 120);
	}
	@Override
	public void starting() { }
	@Override
	public void turn1() { gc.setCurrent(gc.getTurn1()); }
	@Override
	public void turn2() { gc.setCurrent(gc.getTurn2()); }
	@Override
	public void pause() { }
	@Override
	public void over() { }
	@Override
	public void render(Graphics g) {
		g.drawImage(img.getImage("pausa"), 0,0, 866,445,null);//imagen de pantalla de pausa
		g.drawImage(img.getImage("volverB"),312,100, 242, 80,null);//imagen de boton de volver
		g.drawImage(img.getImage("salirB"),312,230,242, 120,null);//imagen de boton de salir
	}
	@Override
	public void update() {
		if(gc.getX() != -1) {//detecta si hizo click
			if(back.getCollider().contains(gc.getX(), gc.getY())) {
				gc.setX(-1);//reiniciando variables delos clicks
				gc.setY(-1);//reiniciando variables delos clicks
				if(gc.getResume() == 1) {//regresa depende del turno al que le toca
					turn1();
				} else {
					turn2();
				}
			}
			if(exitGame.getCollider().contains(gc.getX(), gc.getY())) {
				gc.setX(-1);//reiniciando variables delos clicks
				gc.setY(-1);//reiniciando variables delos clicks
				System.exit(0);//sale del juego
			}
			gc.setX(-1);//reiniciando variables delos clicks
			gc.setY(-1);//reiniciando variables delos clicks
		}
	}
}
