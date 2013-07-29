package maze;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Board extends JPanel {
	final int SIZE=10;
	final int INIT_HEIGHT=25;
	final int INIT_WIDTH=25;
	Modle modle;
	public Board() {
		modle=new Modle();
		addKeyListener(new TAdapter());
		setFocusable(true);
	}
	public void nextScene(JPanel scene){
		this.getParent().add(scene);
		this.getParent().remove(this);
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		boolean[][] LR=modle.map.getWall_lr();
		boolean[][] UD=modle.map.getWall_ud();
		int height=Map.HEIGHT;
		int width=Map.WIDTH;
		boolean[][] squares=modle.map.validMap();
		g.setColor(Color.BLACK);
		for (int i = 0; i < height+1; i++) {
			for (int j = 0; j < width; j++) {
				if (UD[i][j]) {
					g.drawLine(INIT_WIDTH+SIZE*j, INIT_HEIGHT+SIZE*i, INIT_WIDTH+SIZE*(j+1), INIT_HEIGHT+SIZE*i);
				}
			}
		}
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width+1; j++) {
				if (LR[i][j]) {
					g.drawLine(INIT_WIDTH+SIZE*j, INIT_HEIGHT+SIZE*i, INIT_WIDTH+SIZE*j, INIT_HEIGHT+SIZE*(i+1));
				}
			}
		}
		g.fillRect(INIT_WIDTH+modle.getX()*SIZE, INIT_HEIGHT+modle.getY()*SIZE, SIZE, SIZE);
		/*g.setColor(Color.BLUE);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (squares[i][j]) {
					g.fillRect(INIT_WIDTH+j*SIZE+1, INIT_HEIGHT+i*SIZE+1, SIZE-1, SIZE-1);
				}
			}
		}*/
		g.dispose();
	}
	private class TAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int code=e.getKeyCode();
			switch (code) {
			case KeyEvent.VK_UP:
				modle.move(moveType.UP);
				break;
			case KeyEvent.VK_DOWN:
				modle.move(moveType.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				modle.move(moveType.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				modle.move(moveType.RIGHT);
				break;
			default:
				break;
			}
			repaint();
			super.keyPressed(e);
		}
	}
}
