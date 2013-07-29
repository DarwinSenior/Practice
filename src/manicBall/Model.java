package manicBall;

import java.util.ArrayList;

class Position{
	int x;
	int y;
	public Position(int x, int y) {
		this.x=x;
		this.y=y;
	}
	static Position position(int x, int y){
		return new Position(x, y);
	}
}
class Size{
	int height;
	int width;
	public Size(int height, int width) {
		this.height=height;
		this.width=width;
	}
	static Size size(int height, int width){
		return new Size(height, width);
	}
}
public class Model {
	private Ball ball;
	private ArrayList<Block> blocks=new ArrayList<Block>();
	private Paddle paddle;
	private boolean isFallingout;
	final Size ballSize=new Size(5, 5);//TODO this should be decided
	final Size paddleSize=new Size(3, 40);//TODO
	final Size blockSize=new Size(10, 20);//TODO
	final Position ballPosition=new Position(200, 380);
	final Position paddlePosition=new Position(200, 395);
	
	public Model(Position[] positions) {
		isFallingout=false;
		for (Position position : positions) {
			blocks.add(new Block(position));
		}
		ball=new Ball(ballPosition, paddleSize, 2, -2);
		paddle=new Paddle(paddlePosition.y, paddlePosition.x);
	}
	public boolean isFallingout(){
		return isFallingout;
	}
	public void setPaddleX(int x){
		paddle.setX(x);
	}
	public void moveBall(){
		ball.move();
		hitTest();
	}
	public Position getPaddlePosition(){
		return paddle.getPosition();
	}
	public Position getBallPosition() {
		return paddle.getPosition();
	}
	public Position getBlockPositions(int index){
		return blocks.get(index).getPosition();
	}
	public void hitTest(){
		int Xb1=ball.getPosition().x-(ballSize.width/2);
		int Xb2=ball.getPosition().x+(ballSize.width/2);
		int Yb1=ball.getPosition().y-(ballSize.height/2);
		int Yb2=ball.getPosition().y+(ballSize.height/2);
		
		//Hit test for Blocks
		for (int i=0; i<blocks.size(); i++) {
			Block block=blocks.get(i);
			int Ya1=block.getPosition().y-(blockSize.height/2);
			int Ya2=block.getPosition().x+(blockSize.height/2);
			if ((Ya1-Yb1)*(Ya1-Yb2)<0 || (Ya2-Yb1)*(Ya2-Yb2)<0) {
				ball.bouncing(BouncingType.Y_DIRECTION);
				blocks.remove(i);
				i--;
				break;
			}
			int Xa1=block.getPosition().x-(blockSize.width/2);
			int Xa2=block.getPosition().x+(blockSize.width/2);
			if ((Xa1-Xb1)*(Xa1-Xb2)<0 || (Xa2-Xb1)*(Xa2-Xb2)<0) {
				ball.bouncing(BouncingType.X_DIRECTION);
				blocks.remove(i);
				i--;
				break;
			}
		}
		//Hit test for wall
		if (Xb1<0 || Xb2>400) {
			ball.bouncing(BouncingType.X_DIRECTION);
		}
		if (Yb1<0) {
			ball.bouncing(BouncingType.Y_DIRECTION);
		}
		//Hit test for Paddle
		if ((Yb2<400&&Yb1>385) && (Xb1>paddle.getX()-paddleSize.width/2)&&(Xb2<paddle.getX()+paddleSize.width/2)) {
			ball.bouncing(BouncingType.Y_DIRECTION);
		}
		//Fall out
		if (Yb1>400) {
			isFallingout=true;
		}
	}

}
enum BouncingType{
	X_DIRECTION, Y_DIRECTION
}
class Ball{
	private Position position;
	private Size size;
	private int dx,dy;
	public Position getPosition() {
		return position;
	}
	public Ball(Position position, Size size, int dx, int dy) {
		this.position=position;
		this.size=size;
		this.dx=dx;
		this.dy=dy;
	}
	public void move(){
		position.x+=dx;
		position.y+=dy;
	}
	public void bouncing(BouncingType type) {
		switch (type) {
		case X_DIRECTION:
			dx=-dx;
			break;
		case Y_DIRECTION:
			dy=-dy;
			break;
		default:
			break;
		}
	}
}
class Paddle{
	private int x;
	final int y;
	public Paddle(int y,int x) {
		this.y=y;
		this.x=x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getX() {
		return x;
	}
	public Position getPosition(){
		return new Position(x, y);
	}
}
class Block{
	private Position position;
	public Block(Position position) {
		this.position=position;
	}
	public Position getPosition() {
		return position;
	}
}