package states;
import java.awt.*;
import java.util.logging.*;

import Interfaces.*;
import extras.*;
import singletons.*;

public class Turn1 implements GameState{
	private GameContext gc;//cambio de estados
	private ImageLoader img;
	private Hud hud;
	private Collider[] animalColliders;//colisiones//botones
	private Collider pause;
	private int selectedLetter;
	private int isCorrect;
	private int lives;
	private boolean isChronoPause;//
	private int lastMin, lastSec, lastCent;//variables aux
	//constructor	
	public Turn1(GameContext gc) {
		this.gc = gc;
		initComponents();	
	}
	private void initComponents( ) {
		int x=100;
		img = ImageLoader.getLoader();//accediendo a imageloader
		hud = Hud.getHud();
		animalColliders= new Collider[3];
		for(int i=0;i<3;i++) {//crea un colider para cada animal
			animalColliders[i] = new Collider(x,60,121,121);  
			x += 273;
		}
		pause = new Collider(412, 385, 41, 41);
		selectedLetter = (int) (Math.random()*2);// es *2porque elarreglo llega hasta 2
		isCorrect = -1;////variable para saber si laimagen esta correcta
		lives = 3;//va a tener vidas en cada turno
		isChronoPause = false;
	}
	@Override
	public void starting() { }
	@Override
	public void turn1() { }
	@Override
	public void turn2() {
		if(isCorrect == 1) {//saber si hubo una correcta
			isChronoPause = true;//cronometro estara pausado
			lives = 3;
			try {//duerme 1 seg
				Thread.sleep(1000);
			}catch(InterruptedException ex){
				Logger.getLogger(Turn1.class.getName()).log(Level.SEVERE, null, ex);
			}
			isCorrect = -1;//se reinician
			lastSec = 0;//inicializo de nuevo
			lastMin = 0;//inicializo de nuevo
			lastCent = 0;//inicializo de nuevo
			if(selectedLetter < 2) { //para determinar la sig letra
				selectedLetter++;
			} else {
				selectedLetter = 0;//cuando llegue a dos vuelve e inica en cero 
			}
			gc.setCurrent(gc.getTurn2());//cambia turno
		}
		if(lives == 0) {//si ya no te quedan vidas
			lives = 3;//se reinicia
			isCorrect = -1;//se reinicia
			//se toma el tiempo en que cambia de turno
			lastSec = gc.getChronometer().getSeconds();
			lastMin = gc.getChronometer().getMinutes();
			lastCent = gc.getChronometer().getHundredths();
			//cambio turno
			gc.setCurrent(gc.getTurn2());
		}
	}
	@Override
	public void pause() { gc.setCurrent(gc.getPause()); }//cambioestado pause
	@Override
	public void over() {//cambio estado over
		if(gc.getWinner() != -1) {//si hay ganador
			gc.setCurrent(gc.getOver());//cambia turno
		}
	}
	@Override
	public void render(Graphics g) {
		hud.render(g);
		//accede a las imagenes
		g.drawImage(img.getImage("cerdo"), 100, 60, 121, 121, null);
		g.drawImage(img.getImage("jirafa"), 373, 60, 121, 121, null);
		g.drawImage(img.getImage("vaca"), 646, 60, 121, 121, null);
		g.drawImage(img.getImage("ask"), 213, 201, null);//imagen de la pregunta para la letra
		switch (selectedLetter) {//para la letra seleccionada
		case 0: g.drawImage(img.getImage("c"), 404, 260, 58, 80, null);//para cerdo
		break;
		case 1: g.drawImage(img.getImage("j"), 404, 260, 58, 80, null);//para jirafa
		break;
		case 2: g.drawImage(img.getImage("v"), 404, 260, 58, 80, null);//para vaca
		break;
		}
		g.drawImage(img.getImage("pausaB"), 412, 385, null);//para el boton de pausa cuando esta jugando
		if(isCorrect == 1) {//si acierta
			g.drawImage(img.getImage("avatar1"), 0,245, 200, 200,null);//avatar feliz
		}
		if(isCorrect == 0) {//si no acierta
			g.drawImage(img.getImage("avatar4"), 0, 245,200,200,null);//avatar triste
		}
	}
	@Override
	public void update() {
		if(hud.getRightPlayer1().size() == 3) {//si jugador 1 gana
			gc.setWinner(1);
		}
		over();//se termina
		if(isChronoPause) {//si el cronometro pausado
			gc.getChronometer().setMinutes(lastMin);//reinician
			gc.getChronometer().setSeconds(lastSec);
			gc.getChronometer().setHundredths(lastCent);
			isChronoPause = false;//ya no esta pausado
		}
		if(gc.getX() != -1) {//colisiones para los clicks
			for(int i=0;i<3;i++){
				if(animalColliders[i].getCollider().contains(gc.getX(),gc.getY())) {
					if(selectedLetter == i) {
						hud.getTimesPlayer1().add(gc.getChronometer().toString());
						hud.getRightPlayer1().add(i);
						isCorrect = 1;
					}else{
						isCorrect = 0;
						lives--;
					}
				}
			}
			
			if(pause.getCollider().contains(gc.getX(), gc.getY())) {//colisiones de clicks para el boton de pausa cuando esta jugando
				lastMin = gc.getChronometer().getMinutes();
				lastSec = gc.getChronometer().getSeconds();
				lastCent = gc.getChronometer().getHundredths();
				gc.setX(-1);
				gc.setY(-1);
				gc.setResume(1);//regresa a turno1 cuando termine pausa
				isChronoPause = true;//el cronommetro sepausa
				pause();//cambio a pausa
			}
			gc.setX(-1);
			gc.setY(-1);
			if(isCorrect == 0) {//sino es correcto
				try {
					Thread.sleep(1000);//duerme 1 seg
				}catch(InterruptedException ex){
					Logger.getLogger(Turn1.class.getName()).log(Level.SEVERE, null, ex);
				}
				isCorrect = -1;//reinicia
			}
			turn2();//comprueba si se tiene que cambiar
		}
	}
}
