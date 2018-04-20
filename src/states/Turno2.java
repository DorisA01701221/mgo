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

public class Turno2 implements GameState{
	private GameContext gc;//cambio de estados
	private ImageLoader img;
	private BufferedImage  ask, avatar3, avatar5, hud;
	private BufferedImage[] letras;
	private Animal gusano, tortuga, rino, pause;//botones y colisiones
	private int selectLetra;
	private int correct;
	private int vidas, minutosTotales,segundosTotales;
	private Cronometro time;
	private int lastMin, lastSec, lastCent;//variables aux
	private Hud myHud;
	
	public Turno2(GameContext gc) {
		this.gc=gc;
		//accediendo a imageloader
		img=ImageLoader.getImageLoader();
		gusano =  new Animal(img.getImage("gusano"),100, 60,121 , 121);
		tortuga = new Animal(img.getImage("tortuga") ,373, 60,121 , 121 );
		rino = new Animal(img.getImage("rino"),645, 60, 121 , 121);
		vidas=3; //va a tener 3 vidas
		pause= new Animal(img.getImage("pausaB"),412,385,41,41);
		//timer
				minutosTotales=0;
				segundosTotales=0;

		ask = img.getImage("ask");//accediendo a la pregunta
		//inicializo el hud
				myHud= Hud.getHud();
		//poner avatar feliz y triste del pony
		avatar3 = img.getImage("avatar3");
		avatar5 = img.getImage("avatar5");
		//random de letra 
		letras= new BufferedImage[3];
		letras[0]=img.getImage("g");
		letras[1]=img.getImage("t");
		letras[2]=img.getImage("r");
		selectLetra = (int)(Math.random()*2);// es *2porque elarreglo llega hasta 2
		correct=-1;//variable para saber si laimagen esta correcta
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
		gc.setCurrent(gc.getTurno1());
	}

	@Override
	public void turno2() {

	}

	@Override
	public void render(Graphics g) {
		myHud.render(g);
		
		//dibuja la imagen si hay algo que no se dibujó no se renderisa
		g.drawImage(gusano.getImgAnimal(), gusano.getX(),gusano.getY(), gusano.getWidth(), gusano.getHeight(), null);
		g.drawImage(tortuga.getImgAnimal(),tortuga.getX(),tortuga.getY(), tortuga.getWidth(), tortuga.getHeight(), null);
		g.drawImage(rino.getImgAnimal(), rino.getX(), rino.getY(), rino.getWidth(), rino.getHeight(),null);
		g.drawImage(ask, 213, 201, null);
		//se muestra el boton de pausa
		g.drawImage(img.getImage("pausaB"), 412, 385, null);
		//aqui dibuja la letra
		g.drawImage(letras[selectLetra], 404, 260, 58, 80, null);
		//aqui aparece el hud
		g.drawImage(hud,5,5,856,55,null);
		//si acierta, avatar feliz 
		if(correct == 1) {
			g.drawImage(avatar3, 666,245, 200, 200,null);
		}
		//si no aciertaavatar triste
		if(correct ==0) {
			g.drawImage(avatar5, 666, 245,200,200,null);
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
		if(gc.getX() != -1 && gc.getY()!=-1) {
			//si esta en gusano
			if(gusano.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 0) {
					//recupera el string del tiempo
					myHud.getTimepoPlayer2().add(time.toString());
					//cuando dan click a letra, verifica el momento (en tiempo)
					lastSec = 0;
					lastMin = 0;
					lastCent = 0;
					
					vidas=3;
					correct = 1;
					//agregando  g si estan correctas y lo guarda en correctas
					myHud.getCorrectasPlayer2().add(3);
					gc.setX(-1);
					gc.setY(-1);
					//se duerme unos segundos
					try {
						Thread.sleep(1000);
					}catch(InterruptedException ex){
						Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
					}
					//la imagen aparezca y desaparezca
					correct=-1;
					selectLetra++;//pasar a la siguiente letra
					//pasa turno
					turno1();
				}else {
					correct= 0;
					vidas--; //quitar vidas
				}
			}
			if(tortuga.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 1) {
					//recupera el string del tiempo
					myHud.getTimepoPlayer2().add(time.toString());
					lastSec = 0;
					lastMin = 0;
					lastCent = 0;
					
					vidas=3;
					correct = 1;
					//agregando t si estan correctas
					myHud.getCorrectasPlayer2().add(4);
					gc.setX(-1);
					gc.setY(-1);
					//se duerme unos segundos
					try {
						Thread.sleep(1000);
					}catch(InterruptedException ex){
						Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
					}
					//la imagen aparezca y desaparezca
					correct=-1;
					selectLetra++;//pasar a la siguiente letra
					//pasa turno
					turno1();
				}else {
					correct= 0;
					vidas--; //quitar vidas
				}
			}
			if(rino.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 2) {
					//recupera el string del tiempo
					myHud.getTimepoPlayer2().add(time.toString());
					lastSec = 0;
					lastMin = 0;
					lastCent = 0;
					vidas=3;
					correct = 1;
					//agregando si estan correctas
					myHud.getCorrectasPlayer2().add(5);
					gc.setX(-1);
					gc.setY(-1);
					//se duerme unos segundos
					try {
						Thread.sleep(1000);
					}catch(InterruptedException ex){
						Logger.getLogger(Starting.class.getName()).log(Level.SEVERE, null, ex);
					}//la imagen aparezca y desaparezca
					correct=-1;
					selectLetra=0;
					//pasa turno
					turno1();
				} else {
					correct= 0;
					vidas--; //quitar vidas
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
				gc.setResume(2);
				pause();
			}
			
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
			turno1();
		}
	}

}
