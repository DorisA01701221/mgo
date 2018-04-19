package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import extras.Animal;
import interfaz.GameState;
import singletons.ImageLoader;

public class Turno1 implements GameState{
	private GameContext gc;//cambio de estados
	private ImageLoader img;
	private BufferedImage  ask, avatar1, avatar4, hud;
	private BufferedImage[] letras;
	private Animal cerdo, jirafa, vaca;
	private int selectLetra;
	private int correct;
	public Turno1(GameContext gc) {
		this.gc=gc;
		//accediendo a imageloader
		img=ImageLoader.getImageLoader();
		cerdo =  new Animal(img.getImage("cerdo"),100, 60,121 , 121);
		jirafa = new Animal(img.getImage("jirafa") ,373, 60,121 , 121 );
		vaca = new Animal(img.getImage("vaca"),645, 60, 121 , 121);

		ask = img.getImage("ask");
		//poner hud
		hud= img.getImage("hud");
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
		gc.setCurrent(gc.getTurno2());
	}

	@Override
	public void render(Graphics g) {
		//dibuja la imagen si hay algo que no se dibujó no se renderisa
		g.drawImage(cerdo.getImgAnimal(), cerdo.getX(), cerdo.getY(), cerdo.getWidth(), cerdo.getHeight(), null);
		g.drawImage(jirafa.getImgAnimal(),jirafa.getX(),jirafa.getY(), jirafa.getWidth(), jirafa.getHeight(), null);
		g.drawImage(vaca.getImgAnimal(), vaca.getX(), vaca.getY(), vaca.getWidth(), vaca.getHeight(),null);
		g.drawImage(ask, 213, 201, null);
		//aqui dibuja la letra
		g.drawImage(letras[selectLetra], 404, 260, 58, 80, null);
		//aqui aparece el hud
		g.drawImage(hud,5,5,856,55,null);
		//si acierta, avatar feliz 
		if(correct == 1) {
			g.drawImage(avatar1, 0,245, 200, 200,null);
		}
		//si no aciertaavatar triste
		if(correct ==0) {
			g.drawImage(avatar4, 0, 245,200,200,null);
		}

		if(!gc.getCorrectasPlayer1().isEmpty()) {
			Iterator<Integer> i = gc.getCorrectasPlayer1().iterator();
			int x=130;//para que la imagen ue ponga en el hud se recorra
			while(i.hasNext()) {
				//recuperalo que tiene guardado en correctas 
				int aux=i.next();
				switch(aux) {
				case 0: g.drawImage(cerdo.getImgAnimal(),x,5,50,50, null);
				break;
				case 1: g.drawImage(jirafa.getImgAnimal(),x,5,50,50, null);
				break;
				case 2: g.drawImage(vaca.getImgAnimal(),x,5,50,50,null);
				break;
				}
				
				x+=60;//para que la imagen ue ponga en el hud se recorra
			}
			
		}
	}

	@Override
	public void update() {
		//actualiza
		//
		if(gc.getX() != -1 && gc.getY()!=-1) {
			//si esta en cerdo
			if(cerdo.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 0) {
					correct = 1;
					//agregando  C si estan correctas y lo guarda en correctas
					gc.getCorrectasPlayer1().add(0);
					selectLetra++;//pasar a la siguiente letra
					turno2();
				}else {
					correct= 0;
				}
			}
			if(jirafa.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 1) {
					correct = 1;
					//agregando  J si estan correctas
					gc.getCorrectasPlayer1().add(1);
					selectLetra++;//pasar a la siguiente letra
					turno2();
				}else {
					correct= 0;
				}
			}
			if(vaca.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 2) {
					correct = 1;
					//agregando  V si estan correctas
					gc.getCorrectasPlayer1().add(2);
					selectLetra=0;//pasar a la siguiente letra
					turno2();
				} else {
					correct= 0;
				}
			}
			//reinicia context a que no se ha oprimido nada
			gc.setX(-1);
			gc.setY(-1);
		}
	}

}
