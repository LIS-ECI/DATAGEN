package edu.eci.pgr.sorting.ecitool;

import edu.eci.pgr.sorting.Task;

public class SortingTreeNode {
	private Task value;
	private int level;
	
	public SortingTreeNode() {
		super();
		level = 0;
	}
	
	public SortingTreeNode(Task value) {
		super();
		this.value = value;
		level = 0;
	}

	public SortingTreeNode(Task value, int level) {
		super();
		this.value = value;
		this.level = level;
	}
	public Task getValue() {
		return value;
	}
	public void setValue(Task value) {
		this.value = value;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
