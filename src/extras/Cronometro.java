package extras;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Cronometro implements Runnable {
	private Thread time;
	private int min;
	private int sec;
	private int cent;
	private boolean running;
	
	public Cronometro() {
		time = new Thread(this);
		
		min=0;
		sec=0;
		cent=0;
		
		running=true;
		time.start();
	}
	
		@Override
	public String toString() {
		return min + ":" + sec + ":" + cent;
	}
	
	@Override
	public void run() {
		while(this.running) {
			try {
				Thread.sleep(10);
			}catch(InterruptedException ex){
				Logger.getLogger(Cronometro.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			cent++;
			
			if(cent == 100) {
				cent=0;
				sec++;
			}
			
			if(sec == 60) {
				sec=0;
				min++;
			}
		}
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSec() {
		return sec;
	}

	public void setSec(int sec) {
		this.sec = sec;
	}

	public int getCent() {
		return cent;
	}

	public void setCent(int cent) {
		this.cent = cent;
	}
}
