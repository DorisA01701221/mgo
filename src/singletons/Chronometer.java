package singletons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;
//es cronometro singleton
public class Chronometer{
	private static Chronometer chronometer;
	private Timer time;
	private ActionListener action;
	private int minutes;
	private int seconds;
	private int hundredths;//centesimas
	//constructor
	public Chronometer() {
		//inicialisas el timepo
		minutes = 0;
		seconds = 0;
		hundredths = 0;
		action=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				hundredths++;
				if(hundredths == 100) {
					hundredths = 0;//la centesima vuelve a cero
					seconds++;//aumenta segundos
				}
				if(seconds == 60) {
					seconds = 0;//los segundos vuelven a cero
					minutes++;//aumenta minutos
				}
			}
		};
		time = new Timer (10, action);
		//el thread corra
		time.start();
	}
	//regresa el cronometro
	public static Chronometer getChronometer() {
		if(chronometer == null) {//sino existe un cronometro,lo crea
			chronometer = new Chronometer();
		} 
		//regresa el cronometro
		return chronometer;
	}
	//getters & setters
	public int getMinutes() { return minutes; }
	public void setMinutes(int minutes) { this.minutes = minutes; }
	public int getSeconds() { return seconds; }
	public void setSeconds(int seconds) { this.seconds = seconds; }
	public int getHundredths() { return hundredths; }
	public void setHundredths(int hundredths) { this.hundredths = hundredths; }
	//imprime tiempo
	public String toString() { return minutes + ":" + seconds + ":" + hundredths; }	

}
