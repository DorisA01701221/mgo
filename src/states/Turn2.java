package states;
import java.awt.*;
import java.util.logging.*;

import Interfaces.*;
import extras.*;
import singletons.*;

public class Turn2 implements GameState{
	private GameContext gc;
	private ImageLoader img;
	private Hud hud;
	private Collider bugCollider;
	private Collider turtleCollider;
	private Collider rhinoCollider;
	private Collider pause;
	private int selectedLetter;
	private int isCorrect;
	private int lives;
	private boolean isChronoPause;
	private int lastMin, lastSec, lastCent;
	//constructor
	public Turn2(GameContext gc) {
		this.gc = gc;
		initComponents();
	}
	private void initComponents( ) {
		img = ImageLoader.getLoader();
		hud = Hud.getHud();	
		bugCollider = new Collider(100, 60, 121, 121);
		turtleCollider = new Collider(373, 60, 121, 121);
		rhinoCollider = new Collider(645, 60, 121, 121);
		pause = new Collider(412, 385, 41, 41);
		selectedLetter = (int) (Math.random()*2);
		isCorrect = -1;
		lives = 3;
		isChronoPause = false;
	}
	@Override
	public void starting() { }
	@Override
	public void turn1() {
		if(isCorrect == 1) {
			isChronoPause = true;
			lives = 3;
			try {
				Thread.sleep(1000);
			}catch(InterruptedException ex){
				Logger.getLogger(Turn1.class.getName()).log(Level.SEVERE, null, ex);
			}
			isCorrect = -1;
			lastSec = 0;
			lastMin = 0;
			lastCent = 0;
			if(selectedLetter < 2) {
				selectedLetter++;
			} else {
				selectedLetter = 0;
			}
			gc.setCurrent(gc.getTurn1());
		}
		if(lives == 0) {
			lives = 3;
			isCorrect = -1;
			lastSec = gc.getChronometer().getSeconds();
			lastMin = gc.getChronometer().getMinutes();
			lastCent = gc.getChronometer().getHundredths();
			gc.setCurrent(gc.getTurn1());
		}
	}
	@Override
	public void turn2() { }
	@Override
	public void pause() { gc.setCurrent(gc.getPause()); }
	@Override
	public void over() {
		if(gc.getWinner() != -1) {
			gc.setCurrent(gc.getOver());
		}
	}
	@Override
	public void render(Graphics g) {
		hud.render(g);
		g.drawImage(img.getImage("gusano"), 100, 60, 121, 121, null);
		g.drawImage(img.getImage("tortuga"), 373, 60, 121, 121, null);
		g.drawImage(img.getImage("rino"), 645, 60, 121, 121, null);
		g.drawImage(img.getImage("ask"), 213, 201, null);
		switch (selectedLetter) {
		case 0: g.drawImage(img.getImage("g"), 404, 260, 58, 80, null);
		break;
		case 1: g.drawImage(img.getImage("t"), 404, 260, 58, 80, null);
		break;
		case 2: g.drawImage(img.getImage("r"), 404, 260, 58, 80, null);
		break;
		}
		g.drawImage(img.getImage("pausaB"), 412, 385, null);
		if(isCorrect == 1) {
			g.drawImage(img.getImage("avatar3"), 666, 245, 200, 200,null);
		}
		if(isCorrect == 0) {
			g.drawImage(img.getImage("avatar5"), 666, 245,200,200,null);
		}
	}
	@Override
	public void update() {
		if(hud.getRightPlayer2().size() == 3) {
			gc.setWinner(2);
		}
		over();
		hud.update();
		if(isChronoPause) {
			gc.getChronometer().setMinutes(lastMin);
			gc.getChronometer().setSeconds(lastSec);
			gc.getChronometer().setHundredths(lastCent);
			isChronoPause = false;
		}
		if(gc.getX() != -1) {
			if(bugCollider.getCollider().contains(gc.getX(), gc.getY())) {
				if(selectedLetter == 0) {
					hud.getTimesPlayer2().add(gc.getChronometer().toString());
					hud.getRightPlayer2().add(3);
					isCorrect = 1;
				} else {
					isCorrect = 0;
					lives--;
				}
			} 
			if(turtleCollider.getCollider().contains(gc.getX(), gc.getY())) {
				if(selectedLetter == 1) {
					hud.getTimesPlayer2().add(gc.getChronometer().toString());
					hud.getRightPlayer2().add(4);
					isCorrect = 1;
				} else {
					isCorrect = 0;
					lives--;
				}
			} 
			if(rhinoCollider.getCollider().contains(gc.getX(), gc.getY())) {
				if(selectedLetter == 2) {
					hud.getTimesPlayer2().add(gc.getChronometer().toString());
					hud.getRightPlayer2().add(5);
					isCorrect = 1;
				} else {
					isCorrect = 0;
					lives--;
				}
			} 
			if(pause.getCollider().contains(gc.getX(), gc.getY())) {
				lastMin = gc.getChronometer().getMinutes();
				lastSec = gc.getChronometer().getSeconds();
				lastCent = gc.getChronometer().getHundredths();
				gc.setX(-1);
				gc.setY(-1);
				gc.setResume(2);
				isChronoPause = true;
				pause();
			}
			gc.setX(-1);
			gc.setY(-1);
			if(isCorrect == 0) {
				try {
					Thread.sleep(1000);
				}catch(InterruptedException ex){
					Logger.getLogger(Turn2.class.getName()).log(Level.SEVERE, null, ex);
				}
				isCorrect = -1;
			}
			turn1();
		}
	}
}
