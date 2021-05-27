package BrickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	
	private mapGenerator map;
	
	public Gameplay() {
		map = new mapGenerator(3,7);
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
		
		
		
	}
	
	
	public void paint(Graphics g) {
		//background 
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//drawing map 
		map.draw((Graphics2D)g);
		
		//borders 
		g.setColor(Color.red);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//
		g.setColor(Color.gray);
		g.setFont(new Font("Arial",Font.ITALIC,15));
		g.drawString("@sudhanshu ", 10, 30);
		//scores 
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score: "+score, 590, 30);
		
		//the paddle
		g.setColor(Color.BLUE);
		g.fillRect(playerX, 550, 100, 8);     //as the x-axis is changing and others are fixed
		
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		//if we finish the game then
		if(totalBricks  <=0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You won", 240, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 250, 330);
			
		}
		
		//if ball is fall down then game is over !!
		if(ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over!", 240, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Your score is: "+score, 250, 330);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press enter to Restart ", 230, 350);
			
		}
		
		g.dispose();
		
		
		
	}
	
	
	public void actionPerformed(ActionEvent e) {

		timer.start();
		if(play) {    //to detect the intersection with the paddle then to move the ball upward after the touch
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550,100,8))) {
				ballYdir = -ballYdir;
			}// as the ball is Oval so first we create rectangle around ball and then it detect the intersection with the paddle and then get back to up
			
			
			//for the bricks and the ball intersection .....here first map is global variable of Gamplay class and second map is the 2d array of the mapGenerator class
			A: for(int i=0; i<map.map.length; i++) {
				for(int j=0; j< map.map[0].length; j++) {
					if(map.map[i][j] > 0) {
						//then detect intersection 
						int brickX = j* map.brickwidth + 80;
						int brickY = i* map.brickheight + 50;
						int brickwidth = map.brickwidth;
						int brickheight = map.brickheight;
						
						//to create the rectangle around the bricks
						Rectangle rect = new Rectangle(brickX, brickY, brickwidth, brickheight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY,20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j); //mapGenrator function to set the brick value to 0
							totalBricks--;
							score += 5;//adding the score +5 when brick is intersect with the ball
							
							//for the left and right of the brick then ball move accordingly
							if(ballposX +19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							}else {
								ballYdir = -ballYdir;
							}
							
							break A; //it break the both loop
							
						}
					}
				}
			}
			
			// the ball is moving as the both direction is adding up 
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX  > 670) {
				ballXdir = -ballXdir;
			}
		}
		
		repaint(); //recall the paint method to redraw the paddle
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		//key for left and right
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
		{
			if(playerX >= 600) {
				playerX = 600;
			}
			else {
				moveRight();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(playerX < 10) {
				playerX = 10;
			}
			else {
				moveLeft();
			}
		}
		
		//key for restart the game 
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score =0;
				totalBricks = 21;
				map = new mapGenerator(3, 7);
				
				repaint();
				
			}
		}
	}
	
	
	public void moveRight() {
		play = true;
		playerX+=20;
	}
	public void moveLeft() {
		play = true;
		playerX-=20;
	}

	

	
}
