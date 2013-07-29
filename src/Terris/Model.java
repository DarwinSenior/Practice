package Terris;

import java.util.ArrayList;
import java.util.Random;


class Position{
	public int x;
	public int y;
}
class MovingTerris{
	private Position movingTerris;
	private int type;
	private int rotation;
	final static int[][][] terrisType={
			{{0,0},{0,1},{1,0},{1,1}},//Square-shape=0
			{{0,0},{1,0},{0,1},{0,2}},//l-shape=1
			{{0,0},{-1,0},{0,1},{1,1}},//z-shape=2
			{{0,0},{-1,0},{1,0},{2,0}},//Line-shape=3
			{{0,0},{-1,0},{1,0},{0,1}},//t-shape=4
			{{0,0},{-1,0},{0,1},{0,2}},//lMirrored-shape=5
			{{0,0},{1,0},{0,1},{1,-1}}//zMirrored-shape=6
	};
	public MovingTerris() {
		rotation=0;
		movingTerris=new Position();
		movingTerris.x=5;//TODO Magic Number
		movingTerris.y=0;//TODO Magic Number
		Random random=new Random();
		type=random.nextInt(terrisType.length);
	}
	Position[] getPositions(){
		Position position[]=new Position[4];
		for (int i = 0; i < position.length; i++) {
			position[i]=new Position();
			position[i].x=terrisType[type][i][0];
			position[i].y=terrisType[type][i][1];
			for (int j = 0; j < rotation; j++) {
				int x=-position[i].y;
				position[i].y=position[i].x;
				position[i].x=x;
			}
			position[i].x+=movingTerris.x;
			position[i].y+=movingTerris.y;
		}
		return position;
	}
	void moveDown(){
		movingTerris.y++;
	}
	void moveLeft(){
		movingTerris.x--;
	}
	void moveRight(){
		movingTerris.x++;
	}
	void moveUp(){
		movingTerris.y--;
	}
	void rotate(){
		rotation=(rotation+1)%4;
		if (type==0) {
			rotation=0;
		}
	}
	void unrotate(){
		rotation=(rotation-1)%4;
		if (type==0) {
			rotation=0;
		}
	}
}
public class Model {
	private ArrayList<Position> settledTerris;
	private MovingTerris terris;
	static final int HEIGHT=20;
	static final int WIDTH=10;
	private int count;
	public Model() {
		count=0;
		settledTerris=new ArrayList<Position>();
		terris=new MovingTerris();
	}
	public Position[] getMovingTerris(){
		return terris.getPositions();
	}
	public int getCount() {
		return count;
	}
	public ArrayList<Position> getSettledTerris() {
		return settledTerris;
	}
	public void moveDown(){
		terris.moveDown();
		if (isHit()) {//settle new terris
			terris.moveUp();
			for (Position position : terris.getPositions()) {
				settledTerris.add(position);
			}
			terris=new MovingTerris();
			cancel();
		}
	}
	public void moveAllDown(){
		while (!isHit()) {
			terris.moveDown();
		}
		terris.moveUp();
		for (Position position : terris.getPositions()) {
			settledTerris.add(position);
		}
		terris=new MovingTerris();
		cancel();
	}
	public void moveLeft(){
		terris.moveLeft();
		if (isHit()) {
			terris.moveRight();
		}
	}
	public void moveRight(){
		terris.moveRight();
		if (isHit()) {
			terris.moveLeft();
		}
	}
	public void rotate(){
		terris.rotate();
		if (isHit()) {
			terris.unrotate();
		}
	}
	private boolean isHit(){
		for (Position position : terris.getPositions()) {
			for (Position comparingPosition : settledTerris) {
				if (position.x==comparingPosition.x && position.y==comparingPosition.y) {
					return true;
				}
			}
			if (position.x<0 || position.x>=WIDTH || position.y<0 || position.y>=HEIGHT) {
				return true;
			}
		}
		return false;
	}
	private void cancelling(int line){
		for (int i = 0; i < settledTerris.size(); i++) {
			if (settledTerris.get(i).y<line) {
				settledTerris.get(i).y++;
				continue;
			}
			if (settledTerris.get(i).y==line) {
				settledTerris.remove(i);
				i--;
			}
		}
	}
	public boolean canbeCanclled(int line){
		int count=0;
		for (Position position : settledTerris){
			if (position.y==line) {
				count++;
			}
		}
		return (count==WIDTH);
	}
	private void cancel(){
		int line=0;
		while (line<HEIGHT) {
			if (canbeCanclled(line)) {
				count++;
				cancelling(line);
			}
			line++;
		}
	}
}