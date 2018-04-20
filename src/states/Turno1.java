package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import extras.Animal;
import extras.Cronometro;
import interfaz.GameState;
import singletons.Hud;
import singletons.ImageLoader;

public class Turno1 implements GameState{
	private GameContext gc;//cambio de estados
	private ImageLoader img;
	private BufferedImage  ask, avatar1, avatar4, hud;
	private BufferedImage[] letras;
	private Animal cerdo, jirafa, vaca, pause;//botones y colisiones
	private int selectLetra;
	private int correct;
	private int vidas, minutosTotales,segundosTotales;
	private Cronometro time;
	private int lastMin, lastSec, lastCent;//variables aux
	private Hud myHud;
	
	public Turno1(GameContext gc) {
		this.gc=gc;
		//accediendo a imageloader
		img=ImageLoader.getImageLoader();
		cerdo =  new Animal(img.getImage("cerdo"),100, 60,121 , 121);
		jirafa = new Animal(img.getImage("jirafa") ,373, 60,121 , 121 );
		vaca = new Animal(img.getImage("vaca"),645, 60, 121 , 121);
		//va a tener 3 vidas
		vidas= 3;
		pause= new Animal(img.getImage("pausaB"),412,385,41,41);
		//timer
		minutosTotales=0;
		segundosTotales=0;
		//inicializo el hud
		myHud= Hud.getHud();
		ask = img.getImage("ask");
		//poner avatar feliz y triste del pony
		avatar1 = img.getImage("avatar1");
		avatar4 = img.getImage("avatar4");
		//random de letra 
		letras= new BufferedImage[3];
		letras[0]=img.getImage("c");
		letras[1]=img.getImage("j");
		letras[2]=img.getImage("v");
		// es *2porque elarreglo llega hasta 2
		selectLetra = (int)(Math.random()*2);

		//variable para saber si laimagen esta correcta
		correct=-1;
		//crear time como nuevo
		time = new Cronometro();
		//inicialice como una bandera
		lastMin = -1;
		lastSec = -1;
		lastCent = -1;
	}

	@Override
	public void overrr() {
	
	if(myHud.getCorrectasPlayer1().size()== 3 || myHud.getCorrectasPlayer2().size()==3) {
		gc.setCurrent(gc.getOvered());
	}
	}

	@Override
	public void pause() {
		gc.setCurrent(gc.getPaused());
	}

	@Override
	public void turno1() {


	}

	@Override
	public void turno2() {
		gc.setCurrent(gc.getTurno2());
	}

	@Override
	public void render(Graphics g) {
		myHud.render(g);
		
		//dibuja la imagen si hay algo que no se dibujó no se renderisa
		g.drawImage(cerdo.getImgAnimal(), cerdo.getX(), cerdo.getY(), cerdo.getWidth(), cerdo.getHeight(), null);
		g.drawImage(jirafa.getImgAnimal(),jirafa.getX(),jirafa.getY(), jirafa.getWidth(), jirafa.getHeight(), null);
		g.drawImage(vaca.getImgAnimal(), vaca.getX(), vaca.getY(), vaca.getWidth(), vaca.getHeight(),null);
		g.drawImage(ask, 213, 201, null);
		//se muestra el boton de pausa
		g.drawImage(img.getImage("pausaB"), 412, 385, null);
		//aqui dibuja la letra
		g.drawImage(letras[selectLetra], 404, 260, 58, 80, null);
		//si acierta, avatar feliz 
		
		if(correct == 1) {
			g.drawImage(avatar1, 0,245, 200, 200,null);
		}
		//si no aciertaavatar triste
		if(correct == 0) {
			g.drawImage(avatar4, 0, 245,200,200,null);
		}
	}

	@Override
	public void update() {
		//hace el update del reloj
		myHud.update();
		//checar si las banderas estan en -1
		if(lastSec != -1 && lastMin != -1 && lastCent != -1) {
			//si no son -1 asignamos el valor al thread(con set)
			time.setMin(lastMin);
			time.setSec(lastSec);
			time.setCent(lastSec);
			//inicializo de nuevo
			lastMin = -1;
			lastSec = -1;
			lastCent = -1;
		}
		
		overrr();
		//iniciar timer
		
		if(gc.getX() != -1 && gc.getY()!=-1) {
			//si esta en cerdo
			if(cerdo.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 0) {
					//recupera el string del tiempo
					myHud.getTimepoPlayer1().add(time.toString());
					//cuando dan click a letra, verifica el momento (en tiempo)
					lastSec = 0;
					lastMin = 0;
					lastCent = 0;
					
					vidas=3;
					correct = 1;
					//agregando  C si estan correctas y lo guarda en correctas
					myHud.getCorrectasPlayer1().add(0);
					
					gc.setX(-1);
					gc.setY(-1);
					//se duerma unos segundos
					try {
						Thread.sleep(1000);
					}catch(InterruptedException ex){
						Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
					}
					//la imagen aparezca y desaparezca
					correct=-1;
					selectLetra++;//pasar a la siguiente letra
					//pasa turno
					turno2();
				}else {
					correct= 0;
					//quitar vidas
					vidas--;
				}
			}
			if(jirafa.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 1) {
					//recupera el string del tiempo
					myHud.getTimepoPlayer1().add(time.toString());
					
					lastSec = 0;
					lastMin = 0;
					lastCent = 0;
					
					vidas=3;
					correct = 1;
					//agregando  J si estan correctas
					myHud.getCorrectasPlayer1().add(1);
					
					gc.setX(-1);
					gc.setY(-1);
					//se duerme unos segundos
					try {
						Thread.sleep(1000);
					}catch(InterruptedException ex){
						Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
					}
					correct=-1;
					selectLetra++;//pasar a la siguiente letra
					//pasa turno
					turno2();
				}else {
					correct= 0;
					vidas--;//quitar vidas
				}
			}
			if(vaca.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 2) {
					//recupera el string del tiempo
					myHud.getTimepoPlayer1().add(time.toString());
					
					lastSec = 0;
					lastMin = 0;
					lastCent = 0;
					
					vidas=3;
					correct = 1;
					//agregando  V si estan correctas
					myHud.getCorrectasPlayer1().add(2);
					gc.setX(-1);
					gc.setY(-1);
					//se duerme unos segundos
					try {
						Thread.sleep(1000);
					}catch(InterruptedException ex){
						Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
					}
					correct=-1;
					selectLetra=0;//pasar a la siguiente letra
					//pasa turno
					turno2();
				} else {
					correct= 0;
					vidas--;//quitar vidas
				}
			}
			//detectar colisiones del boton pausa
			if(pause.getRec().contains(gc.getX(),gc.getY())) {
				//recupera en pausa
				lastSec = time.getSec();
				lastMin = time.getMin();
				lastCent = time.getCent();
				
				gc.setX(-1);
				gc.setY(-1);
				gc.setResume(1);
				pause();
			}
			
			//reinicia context a que no se ha oprimido nada
			gc.setX(-1);
			gc.setY(-1);
			//sleep para que nose confunda cada que es erroneo
			try {
				Thread.sleep(1000);
			}catch(InterruptedException ex){
				Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
			}
			correct=-1;
		}
		if(vidas<1) {
			//recuperas el tiempo
			lastSec = time.getSec();
			lastMin = time.getMin();
			lastCent = time.getCent();
			vidas=3;
			//se duerme unos segundos
			try {
				Thread.sleep(1000);
			}catch(InterruptedException ex){
				Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
			}
			//pasa turno
			turno2();
		}
	}

}
