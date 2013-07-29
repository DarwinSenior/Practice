package maze;

import javax.swing.JFrame;

public class Maze extends JFrame{
	public Maze() {
		Board board=new Board();
		add(board);
		
		setTitle("Maze");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(550,550);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public static void main(String[] args) {
		new Maze();
	}
}
