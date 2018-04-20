package singletons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import states.Starting;

public class Hud {
	private static Hud isHud;//para el hud
	private ImageLoader loader;
	private ArrayList<Integer> correctasPlayer1;//para saber que imagen iba a dibujar de correctas en elhud
	private ArrayList<Integer> correctasPlayer2;
	private int relog;//este ira cambiandomis imagenes
	private ArrayList<String> tiempoPlayer1;//saber el tiempo en elegir imagen
	private ArrayList<String> tiempoPlayer2;//saber el tiempo en elegir imagen

	private Hud() {
		loader= ImageLoader.getImageLoader();
		//para que se muestre el hud (aqui aun no semuestra)
		correctasPlayer1= new ArrayList<Integer> ();
		correctasPlayer2= new ArrayList<Integer> ();
		relog=1;
		tiempoPlayer1= new ArrayList<String> ();
		tiempoPlayer2= new ArrayList<String> ();
	}
	public static Hud getHud() {
		if (isHud == null) {
			isHud= new Hud();
		} return isHud;
	}
	public ArrayList<Integer> getCorrectasPlayer1() {
		return correctasPlayer1;
	}
	public void setCorrectasPlayer1(ArrayList<Integer> correctasPlayer1) {
		this.correctasPlayer1 = correctasPlayer1;
	}
	public ArrayList<Integer> getCorrectasPlayer2() {
		return correctasPlayer2;
	}
	public void setCorrectasPlayer2(ArrayList<Integer> correctasPlayer2) {
		this.correctasPlayer2 = correctasPlayer2;
	}
	
	public ArrayList<String> getTimepoPlayer1() {
		return tiempoPlayer1;
	}
	public void setTimepoPlayer1(ArrayList<String> timepoPlayer1) {
		this.tiempoPlayer1 = timepoPlayer1;
	}
	public ArrayList<String> getTimepoPlayer2() {
		return tiempoPlayer2;
	}
	public void setTimepoPlayer2(ArrayList<String> timepoPlayer2) {
		this.tiempoPlayer2 = timepoPlayer2;
	}
	//render
	public void render(Graphics g) {
		//aqui aparece el hud
		g.drawImage(loader.getImage("hud"),5,5,856,55,null);
		//pone la imagen de reloj
		g.drawImage(loader.getImage("relog"+Integer.toString(relog)),406,5,55,55,null);

		//para correctas de player1
		if(!correctasPlayer1.isEmpty()) {
			Iterator<Integer> i = correctasPlayer1.iterator();
			int x=130;//para que la imagen ue ponga en el hud se recorra
			while(i.hasNext()) {
				//recuperalo que tiene guardado en correctas 
				int aux=i.next();
				switch(aux) {
				case 0: g.drawImage(loader.getImage("cerdo"),x,5,50,50, null);
				break;
				case 1: g.drawImage(loader.getImage("jirafa"),x,5,50,50, null);
				break;
				case 2: g.drawImage(loader.getImage("vaca"),x,5,50,50,null);
				break;
				}

				x+=110;//para que la imagen ue ponga en el hud se recorra
			}
		}
		if(!tiempoPlayer1.isEmpty()) {
			Iterator<String> s= tiempoPlayer1.iterator();
			int x = 80;
			while(s.hasNext()) {
				String aux= s.next();
				
				g.setColor(Color.BLACK);
				g.setFont(new Font ("Tahoma", Font.BOLD,15));
				g.drawString(aux, x, 50);
				
				x += 110;
			}
		}
		//ver las de tu contrincante#####
		if(!correctasPlayer2.isEmpty()) {
			Iterator<Integer> i = correctasPlayer2.iterator();
			int x=690;//para que la imagen ue ponga en el hud se recorra
			while(i.hasNext()) {
				//recuperalo que tiene guardado en correctas 
				int aux=i.next();
				switch(aux) {
				case 3: g.drawImage(loader.getImage("gusano"),x,5,50,50, null);
				break;
				case 4: g.drawImage(loader.getImage("tortuga"),x,5,50,50, null);
				break;
				case 5: g.drawImage(loader.getImage("rino"),x,5,50,50,null);
				break;
				}

				x-=110;//para que la imagen ue ponga en el hud se recorra
			}
		}
		
		if(!tiempoPlayer2.isEmpty()) {
			Iterator<String> s= tiempoPlayer2.iterator();
			int x = 740;
			while(s.hasNext()) {
				String aux= s.next();
				
				g.setColor(Color.BLACK);
				g.setFont(new Font ("Tahoma", Font.BOLD,15));
				g.drawString(aux, x, 50);
				
				x -= 110;
			}
		}
	}
	public void update() {
		try {
			Thread.sleep(500);
		}catch(InterruptedException ex){
			Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
		}
		if(relog<4) {
			relog++;
		}else {
			relog=1;
		}
	}
}
