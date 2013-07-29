package Terris;

import javax.swing.JFrame;

public class Terris extends JFrame{
	public Terris() {
		// TODO Auto-generated constructor stub
		Board board=new Board();
		add(board);
		
		setSize(300,500);
		setTitle("Terris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		
	}
	public static void main(String[] args) {
		new Terris();
	}
}
