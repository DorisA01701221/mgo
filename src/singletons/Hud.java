package singletons;
import java.awt.*;
import java.util.*;
import java.util.logging.*;

public class Hud {
	private static Hud hud;
	private ImageLoader img;
	private ArrayList<Integer> rightPlayer1;
	private ArrayList<Integer> rightPlayer2;
	private ArrayList<String> timesPlayer1;
	private ArrayList<String> timesPlayer2;
	private int clock; //la imagen//este ira cambiandomis imagenes
	//constructor
	private Hud() {
		img = ImageLoader.getLoader();
		rightPlayer1 = new ArrayList<Integer> ();
		rightPlayer2 = new ArrayList<Integer> ();
		timesPlayer1 = new ArrayList<String> ();
		timesPlayer2 = new ArrayList<String> ();
		clock = 1;
	}
	public static Hud getHud() {
		if(hud == null) {
			hud = new Hud();
		}
		return hud;
	}
	//getters: uso el mismo que ya existe
	public ArrayList<Integer> getRightPlayer1() { return rightPlayer1; }
	public ArrayList<Integer> getRightPlayer2() { return rightPlayer2; }
	public ArrayList<String> getTimesPlayer1() { return timesPlayer1; }
	public ArrayList<String> getTimesPlayer2() { return timesPlayer2; }
	public void render(Graphics g) {
		//color y fuente del tiempo que se imprime encada correcta
		g.setColor(Color.BLACK);
		g.setFont(new Font ("Tahoma", Font.BOLD,15));
		//se dibuja el hud y el reloj que ira cambiando las 4 imagenes
		g.drawImage(img.getImage("hud"),5,5,856,55,null);
		g.drawImage(img.getImage("relog"+Integer.toString(clock)),406,5,55,55,null);
		//para correctas de player 1
		if(!rightPlayer1.isEmpty()) {
			Iterator<Integer> itr = rightPlayer1.iterator();
			int x = 130;//para que la imagen ue ponga en el hud se recorra
			while(itr.hasNext()) {
				int aux = itr.next();
				//recuperalo que tiene guardado en correctas
				switch (aux) {
				case 0: g.drawImage(img.getImage("cerdo"),x,5,50,50, null);
				break;
				case 1: g.drawImage(img.getImage("jirafa"),x,5,50,50, null);
				break;
				case 2: g.drawImage(img.getImage("vaca"),x,5,50,50,null);
				break;
				}
				x += 110;//para que la imagen ue ponga en el hud se recorra
			}
		}
		if(!timesPlayer1.isEmpty()) {
			Iterator<String> itr = timesPlayer1.iterator();
			int x = 80;
			while(itr.hasNext()) {
				String aux = itr.next();
				g.drawString(aux, x, 50);
				x += 110;//  se recorre de posicion el tiempo
			}
		}
		//ver las de tu contrincante#####
		if(!rightPlayer2.isEmpty()) {
			Iterator<Integer> itr = rightPlayer2.iterator();
			int x = 690;//para que la imagen ue ponga en el hud se recorra
			while(itr.hasNext()) {
				//recuperalo que tiene guardado en correctas
				int aux = itr.next();
				switch (aux) {
				case 3: g.drawImage(img.getImage("gusano"),x,5,50,50, null);
				break;
				case 4: g.drawImage(img.getImage("tortuga"),x,5,50,50, null);
				break;
				case 5: g.drawImage(img.getImage("rino"),x,5,50,50,null);
				break;
				}
				x -= 110;//para que la imagen ue ponga en el hud se recorra
			}
		}
		if(!timesPlayer2.isEmpty()) {
			Iterator<String> itr = timesPlayer2.iterator();
			int x = 740;
			while(itr.hasNext()) {
				String aux = itr.next();
				g.drawString(aux, x, 50);
				x -= 110;
			}
		}
	}
	public void update() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Logger.getLogger(Hud.class.getName()).log(Level.SEVERE, null, e);
		}
		if(clock < 4) {
			clock++;
		} else {
			clock = 1;
		}
	}
}
