package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import extras.Animal;
import extras.Cronometro;
import interfaz.GameState;
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
	private Cronometro time;//objeto para el timer

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
				time= new Cronometro();
				minutosTotales=0;
				segundosTotales=0;

		ask = img.getImage("ask");//accediendo a la pregunta
		
		hud= img.getImage("hud");//poner hud
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
	}

	@Override
	public void overrr() {
		if(gc.getCorrectasPlayer1().size()== 3 || gc.getCorrectasPlayer2().size()==3) {
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

		if(!gc.getCorrectasPlayer2().isEmpty()) {
			Iterator<Integer> i = gc.getCorrectasPlayer2().iterator();
			int x=706;//para que la imagen ue ponga en el hud se recorra
			while(i.hasNext()) {
				//recuperalo que tiene guardado en correctas 
				int aux=i.next();
				switch(aux) {
				case 3: g.drawImage(gusano.getImgAnimal(),x,5,50,50, null);
				break;
				case 4: g.drawImage(tortuga.getImgAnimal(),x,5,50,50, null);
				break;
				case 5: g.drawImage(rino.getImgAnimal(),x,5,50,50,null);
				break;
				}

				x-=60;//para que la imagen ue ponga en el hud se recorra
			}
		}
		
		//ver correctas de oponente
		if(!gc.getCorrectasPlayer1().isEmpty()) {
			Iterator<Integer> i = gc.getCorrectasPlayer1().iterator();
			int x=130;//para que la imagen ue ponga en el hud se recorra
			while(i.hasNext()) {
				//recuperalo que tiene guardado en correctas 
				int aux=i.next();
				switch(aux) {
				case 0: g.drawImage(img.getImage("cerdo"),x,5,50,50, null);
				break;
				case 1: g.drawImage(img.getImage("jirafa"),x,5,50,50, null);
				break;
				case 2: g.drawImage(img.getImage("vaca"),x,5,50,50,null);
				break;
				}
				
				x+=60;//para que la imagen ue ponga en el hud se recorra
			}
		}
		
	}

	@Override
	public void update() {
		System.out.println(time.toString());
		//actualiza
		overrr();
				
		if(gc.getX() != -1 && gc.getY()!=-1) {
			//si esta en gusano
			if(gusano.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 0) {

					vidas=3;
					correct = 1;
					//agregando  C si estan correctas y lo guarda en correctas
					gc.getCorrectasPlayer2().add(3);
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
					
					vidas=3;
					correct = 1;
					//agregando  si estan correctas
					gc.getCorrectasPlayer2().add(4);
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
					
					vidas=3;
					correct = 1;
					//agregando si estan correctas
					gc.getCorrectasPlayer2().add(5);
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
