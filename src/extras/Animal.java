package extras;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
// clase para controlar colisiones pero ya no lepude cambiar el nombre a la clase
public class Animal {
	private int x;
	private int y;
	private int width;
	private int height;
	private BufferedImage imgAnimal;
	//este es para hacer lascolisiones
	private Rectangle rec;
	
	//recibirá una imagen
	public Animal(BufferedImage imgAnimal, int x, int y, int width, int height) {
		this.imgAnimal=imgAnimal;
		this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
		rec= new Rectangle (x,y,width,height);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getImgAnimal() {
		return imgAnimal;
	}

	public void setImgAnimal(BufferedImage imgAnimal) {
		this.imgAnimal = imgAnimal;
	}

	public Rectangle getRec() {
		return rec;
	}

	public void setRec(Rectangle rec) {
		this.rec = rec;
	}
	
}
