package edu.eci.pgr.view.graph;

public class Position {
	private int x;
	private int y;
	private String name;
	public Position() {
		super();
	}
	
	public Position(String name) {
		super();
		this.name = name;
	}

	public Position(int x, int y, String name) {
		super();
		this.x = x;
		this.y = y;
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getName() {
		return name;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

