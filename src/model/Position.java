package model;

public abstract class Position {
	private int x;
	private int y;
	private Piece p;
	
	public Position(int x, int y){
		this.x=x;
		this.y=y;
		p=null;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
