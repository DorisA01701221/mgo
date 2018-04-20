package extras;

import java.awt.*;
//esta clase es unica para detectar colisiones
public class Collider {
	private Rectangle collider;
	public Collider(int x, int y, int width, int height) {
		collider = new Rectangle(x, y, width, height);
	}
	public Rectangle getCollider() { return collider; }
}
