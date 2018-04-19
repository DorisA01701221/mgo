package states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import extras.Animal;
import interfaz.GameState;
import singletons.ImageLoader;

public class Turno2 implements GameState{
	private GameContext gc;//cambio de estados
	private ImageLoader img;
	private BufferedImage  ask, avatar3, avatar5, hud;
	private BufferedImage[] letras;
	private Animal gusano, tortuga, rino;
	private int selectLetra;
	private int correct;
	private int vidas;

	public Turno2(GameContext gc) {
		this.gc=gc;
		//accediendo a imageloader
		img=ImageLoader.getImageLoader();
		gusano =  new Animal(img.getImage("gusano"),100, 60,121 , 121);
		tortuga = new Animal(img.getImage("tortuga") ,373, 60,121 , 121 );
		rino = new Animal(img.getImage("rino"),645, 60, 121 , 121);
		vidas=3; //va a tener 3 vidas
		//accediendo a la pregunta
		ask = img.getImage("ask");
		//poner hud
		hud= img.getImage("hud");
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
	}

	@Override
	public void update() {
		//actualiza
		overrr();
		if(gc.getX() != -1 && gc.getY()!=-1) {
			//si esta en cerdo
			if(gusano.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 0) {
					correct = 1;
					//agregando  C si estan correctas y lo guarda en correctas
					gc.getCorrectasPlayer2().add(3);
					selectLetra++;//pasar a la siguiente letra
					gc.setX(-1);
					gc.setY(-1);
					turno1();
				}else {
					correct= 0;
					vidas--; //quitar vidas
				}
			}
			if(tortuga.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 1) {
					correct = 1;
					//agregando  J si estan correctas
					gc.getCorrectasPlayer2().add(4);
					selectLetra++;//pasar a la siguiente letra
					gc.setX(-1);
					gc.setY(-1);
					turno1();
				}else {
					correct= 0;
					vidas--; //quitar vidas
				}
			}
			if(rino.getRec().contains(gc.getX(), gc.getY())) {
				if(selectLetra == 2) {
					correct = 1;
					//agregando  V si estan correctas
					gc.getCorrectasPlayer2().add(5);
					selectLetra=0;//pasar a la siguiente letra
					gc.setX(-1);
					gc.setY(-1);
					turno1();
				} else {
					correct= 0;
					vidas--; //quitar vidas
				}
			}
			gc.setX(-1);
			gc.setY(-1);
		}
		if(vidas==0) {
			vidas=3;
			turno1();
		}
	}

}
