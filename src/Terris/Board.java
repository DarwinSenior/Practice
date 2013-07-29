package Terris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener{
	final int SIZE=20;
	final int INIT_HEIGHT=50;
	final int INIT_WEIGHT=50;
	Timer timer;
	Model model;
	boolean pause;
	
	public Board() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		newGame();
		pause=false;
	}
	void newGame(){
		model=new Model();
		timer=new Timer(300, this);
		timer.start();
	}
	void resume(){
		timer.start();
	}
	void pause(){
		timer.stop();
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (Position position : model.getSettledTerris()) {
			drawTerris(g, position);
		}
		for (Position position : model.getMovingTerris()) {
			drawTerris(g, position);
		}
		g.dispose();
	}
	private void drawTerris(Graphics g,Position p){
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), 
	            new Color(102, 204, 102), new Color(102, 102, 204), 
	            new Color(204, 204, 102), new Color(204, 102, 204), 
	            new Color(102, 204, 204), new Color(218, 170, 0)
	        };
		Random random=new Random();
		int x=INIT_WEIGHT+SIZE*p.x;
		int y=INIT_HEIGHT+SIZE*p.y;
		Color color=colors[random.nextInt(colors.length)];
		g.setColor(color);
		g.fillRect(x, y, SIZE-1, SIZE-1);
		g.setColor(color.brighter());
		g.drawLine(x, y, x+SIZE-1, y);
		g.drawLine(x, y, x, y+SIZE-1);
		g.setColor(color.darker());
		g.drawLine(x+SIZE-1, y+SIZE-1, x, y+SIZE-1);
		g.drawLine(x+SIZE-1, y+SIZE-1, x+SIZE-1, y);
		g.drawString("The count:"+model.getCount(), 20, 20);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		model.moveDown();
		repaint();
	}
	private class TAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int code=e.getKeyCode();
			switch (code) {
			case KeyEvent.VK_UP:
				model.rotate();
				break;
			case KeyEvent.VK_LEFT:
				model.moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				model.moveRight();
				break;
			case KeyEvent.VK_DOWN:
				model.moveAllDown();
				break;
			case KeyEvent.VK_P:
				if (pause) {
					pause=false;
					resume();
				}else {
					pause=true;
					pause();
				}
			default:
				break;
			}
			super.keyPressed(e);
		}
	}
}
