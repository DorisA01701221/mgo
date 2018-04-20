package extras;

import javax.swing.*;
import java.awt.event.*;

public class Cronometro implements Runnable{
	private Timer t;
	private int minutes;
	private int seconds;
	private int cs;
	private ActionListener actions;
	
	public Cronometro() {
		minutes = 0;
		seconds = 0;
		cs = 0;
		
		t = new Timer(10, actions);
		
		actions = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cs++;
				
				if(cs == 100) {
					cs = 0;
					seconds++;
				} 
				
				if(seconds == 60) {
					seconds = 0;
					minutes++;
				}
			}
		};
		
		t.start();
	}
	
	public void startCronometro() {
		t.start();
	}
	
	public void pauseCronometro( ) {
		t.stop();
	}
	
	public void reloadCronometro() {
		if(t.isRunning()) {
			pauseCronometro();
			startCronometro();
		}
		
		minutes = 0;
		seconds = 0;
		cs = 0;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getCs() {
		return cs;
	}

	public void setCs(int cs) {
		this.cs = cs;
	}
	
	public String toString() {
		return Integer.toString(minutes) + ":" + Integer.toString(seconds) + ":" + Integer.toString(cs);
	}
	
	
}
