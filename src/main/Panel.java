package main;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;

import javax.swing.*;

import states.GameContext;

public class Panel extends JPanel implements Runnable{
	private static final int PWIDTH = 866;//tamaño de pantalla
	private static final int PHEIGHT = 445;
	private Thread animator;//animacion
	private volatile boolean running = false;//todos los estados inician apagados
	private volatile boolean gameOver = false;
	private volatile boolean isPaused = false;
	private GameContext context;
	private Graphics dbg;
	private Image dbImage = null;
	//constructor
	public Panel() {
		setBackground(Color.white);
		setPreferredSize(new Dimension(PWIDTH,PHEIGHT));
		setFocusable(true);
		requestFocus();
		readyForTermination();
		context =new GameContext();
	}
	public void addNotify()
	{
		super.addNotify();
		startGame();
	}
	private void startGame()
	{
		if(animator == null || !running){
			animator = new Thread(this);
			animator.start();
		}
		ExecutorService application = Executors.newCachedThreadPool();
		application.execute(this);
		application.shutdown();

	}
	public void stopGame(){ running = false; }
	public void pauseGame(){ isPaused = true; }
	public void resumeGame(){ isPaused = false; }
	@Override
	public void run() {
		running = true;
		while(running){
			gameUpdate();
			gameRender();
			paintScreen();
			try{
				Thread.sleep(50);
			}catch(InterruptedException ex){}
		}
		System.exit(0);
	}
	private void gameUpdate(){
		if(!isPaused && !gameOver){
			context.update();
		}
	}
	private void gameRender(){
		if(dbImage == null){
			dbImage = createImage(PWIDTH,PHEIGHT);
			if(dbImage == null){
				System.out.println("dbImage is null");
				return;
			}else{
				dbg = dbImage.getGraphics();
			}
		}
		dbg.setColor(Color.BLACK);
		dbg.fillRect(0,0,PWIDTH,PHEIGHT);
		context.render(dbg);
	}
	private void paintScreen(){
		Graphics g;
		try{
			g = this.getGraphics();
			if((g != null) && (dbImage != null))
				g.drawImage(dbImage,0,0,null);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}
		catch(Exception e){
			System.out.println("Graphics context error: "+ e);
		}
	}
	private void readyForTermination() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				context.setX(x);
				context.setY(y);
			}
		});
	}
	public static void main(String args[]){
		JFrame app = new JFrame("Test");
		app.getContentPane().add(new Panel(), BorderLayout.CENTER);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.pack();
		app.setResizable(false);
		app.setVisible(true);
	}
}
