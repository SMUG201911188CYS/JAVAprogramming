package main.java.com.zeruls.game;

import main.java.com.zeruls.game.states.GameStateManager;
import main.java.com.zeruls.game.util.KeyHandler;
import main.java.com.zeruls.game.util.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	public static int width;
	public static int height;
	public static int frames;

	public static int oldFrameCount;
	
	private Thread thread;
	private boolean running = false;
	
	private BufferedImage img;
   private	Graphics2D g;
   
   private MouseHandler mouse;
   private KeyHandler key;

   private GameStateManager gsm;

   private Image BackGround;

   private int FPS = 60;
   private final int UPS = 25;

    public GamePanel(int width,int height ) {
		this.width= width;
		this.height= height;
		setPreferredSize(new Dimension(width,height));
		setFocusable(true);
		requestFocus();
		
	}

	public static void sleep(long millis) throws InterruptedException {
		Thread.sleep(millis);
	}

	
	
	public void addNotify() {
		super.addNotify();

		if(thread==null) {
			thread = new Thread(this,"GameThread");
			thread.start();
			
		}
	}
	
	public void init() throws CloneNotSupportedException {
		running = true;
		
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();
      
     mouse = new MouseHandler(this);
      key = new KeyHandler(this);	//JPanel*/
	  //this.addMouseListener(new MouseHandler(this));
	  //this.addKeyListener(new KeyHandler(this));
      gsm = new GameStateManager();
	}
	@Override
	public void run() {
        try {
            init();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        /*final double GAME_HERTZ = 64.0;
        final double TBU = 1000000000 / GAME_HERTZ; // Time Before Update	15,625,000

        final int MUBR = 3; // Must Update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 1000;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render 1,000,000

		int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);*/

        //-----------------------
		long initialTime = System.nanoTime();
		final double timeU = 1000000000 / UPS;
		final double timeF = 1000000000 / FPS;
		double deltaU = 0, deltaF = 0;
		int ticks = 0;
		long timer = System.currentTimeMillis();
      
        oldFrameCount = 0;
		
		while(running) {
			long currentTime = System.nanoTime();
			deltaU += (currentTime - initialTime) /timeU;
			deltaF += (currentTime - initialTime) / timeF;
			initialTime = currentTime;

			if(deltaU >=1) {
                try {
                    input(mouse,key);
                } catch (CloneNotSupportedException | InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    update();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                ticks++;
				deltaU--;
			}

			if(deltaF >=1) {
				render();
				draw();
				frames++;
				deltaF--;
			}
			if(System.currentTimeMillis() - timer > 1000) {
				frames = 0;
				ticks=0;
				timer += 1000;
			}
		}
	}
	
	public void update() throws CloneNotSupportedException {
		gsm.update();
	}
	
	public void input(MouseHandler mouse, KeyHandler key) throws CloneNotSupportedException, InterruptedException {

		gsm.input(mouse, key);
	}

	public void render() {
		if(g != null) {
			g.setColor(Color.BLACK);
			g.fillRect(0,0,width,height);
			gsm.render(g);
		}
	}
	
	public void draw() {
		Graphics g2 = (Graphics) this.getGraphics();
		g2.drawImage(img,0,0,width,height,null);
		g2.dispose();
	}
}
