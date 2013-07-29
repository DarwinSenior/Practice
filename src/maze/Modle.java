package maze;

import java.util.InputMismatchException;
import java.util.Random;

public class Modle {
	public Map map;
	private int x,y;
	public Modle() {
		x=0;
		y=0;
		map=new Map();
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void move(moveType move){
		if (map.canPass(x, y, move)) {
			switch (move) {
			case UP:
				y--;
				break;
			case DOWN:
				y++;
				break;
			case LEFT:
				x--;
				break;
			case RIGHT:
				x++;
				break;
			default:
				break;
			}
		}
		if (x==Map.WIDTH-1 && y==Map.HEIGHT-1) {
			map=new Map();
			x=0;
			y=0;
		}
	}
}
class Map{
	private boolean[][] wall_lr;
	private boolean[][] wall_ud;
	public static int HEIGHT=45;
	public static int WIDTH=45;
	public Map() {
		do{
			generatMap();
		}while(!validMap()[HEIGHT-1][WIDTH-1]);
	}
	public boolean canPass(int x, int y, moveType move){
		switch (move) {
		case UP:
			return !wall_ud[y][x];
		case DOWN:
			return !wall_ud[y+1][x];
		case LEFT:
			return !wall_lr[y][x];
		case RIGHT:
			return !wall_lr[y][x+1];
		default:
			throw new InputMismatchException();
		}
	}
	private void floodFill(boolean[][] squares, int x, int y){
		if (!squares[y][x]) {
			squares[y][x]=true;
			if (canPass(x, y, moveType.UP)) {
				floodFill(squares, x, y-1);
			}
			if (canPass(x, y, moveType.DOWN)) {
				floodFill(squares, x, y+1);
			}
			if (canPass(x, y, moveType.LEFT)) {
				floodFill(squares, x-1, y);
			}
			if (canPass(x, y, moveType.RIGHT)) {
				floodFill(squares, x+1, y);
			}
		}
	}
	public boolean[][] validMap(){
		boolean[][] squares=new boolean[HEIGHT][WIDTH];
		floodFill(squares, 0, 0);
		return squares;
	}
	private void generatMap(){
		wall_lr=new boolean[HEIGHT][WIDTH+1];
		wall_ud=new boolean[HEIGHT+1][WIDTH];
		for (int i = 0; i < HEIGHT; i++) {
			wall_lr[i][0]=true;
			wall_lr[i][WIDTH]=true;
		}
		for (int i = 0; i < WIDTH; i++) {
			wall_ud[0][i]=true;
			wall_ud[HEIGHT][i]=true;
		}
		Random random=new Random();
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 1; j < WIDTH; j++) {
				wall_lr[i][j]=random.nextBoolean();
			}
		}
		for (int i = 1; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				wall_ud[i][j]=random.nextBoolean();
			}
		}
	}
	public boolean[][] getWall_lr() {
		return wall_lr;
	}
	public boolean[][] getWall_ud() {
		return wall_ud;
	}
	
}
enum moveType{
	UP,DOWN,LEFT,RIGHT
}