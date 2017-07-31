package edu.eci.pgr.view.graph;

import java.util.HashMap;
import java.util.List;

import edu.eci.pgr.sorting.Task;
public class ModelDrawer {
	public static final int HEIGHT = 30;
	public static final int WIDTH = 100;
	public static final int DISTANCE = 50;
	private Position[][]matrix;
	private HashMap<String,Position> positions;
	private int radius;
	public ModelDrawer(List<Task> tasks){
		radius = getRadius(tasks.size());
		matrix = new Position[radius][radius];
		int value = tasks.size()-1;
		int size = radius * radius-1;
		int i, j, k;
		int mat[][] = new int[radius][radius];
		for(i=0;i<radius;i++)
			for(j=0;j<radius;j++)
				mat[i][j] = -1;
		for (k = 0; k < radius; k++) {
			j = k;
			for (i = k; i <= (radius - 1 - k); i++) {
				if (value >= size) {
					matrix[i][j] = new Position(tasks.get(value).getTable().getName());
					mat[i][j] = value;
					value--;
				}
				size--;
			}
			i = radius - 1 - k;
			for (j = k + 1; j <= radius - 1 - k; j++) {
				if (value >= size) {
					matrix[i][j] = new Position(tasks.get(value).getTable().getName());
					mat[i][j] = value;
					value--;
				}
				size--;
			}
			j = radius - 1 - k;
			for (i = radius - 2 - k; i >= k; i--) {
				if (value >= size) {
					matrix[i][j] = new Position(tasks.get(value).getTable().getName());
					mat[i][j] = value;
					value--;
				}
				size--;
			}
			i = k;
			for (j = radius - 2 - k; j >= k + 1; j--) {
				if (value >= size) {
					matrix[i][j] = new Position(tasks.get(value).getTable().getName());
					mat[i][j] = value;
					value--;
				}
				size--;
			}
		}
		
		positions = new HashMap<String, Position>();
		for(i=0;i<radius;i++){
			for(j=0;j<radius;j++){
				if(matrix[i][j] != null){
					matrix[i][j].setX(j*(DISTANCE+WIDTH));
					matrix[i][j].setY(i*(DISTANCE+HEIGHT));
					positions.put(matrix[i][j].getName(),matrix[i][j]);
				}
			}
		}
	}
	public HashMap<String,Position> getPositions(){
		return positions;
	}
	private int getRadius(int size) {
		int total = 1;
		while(total*total < size)
			total+=2;
		return total;
	}
	public int getRadius(){
		return radius;
	}
	
}
