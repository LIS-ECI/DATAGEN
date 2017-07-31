package edu.eci.pgr.sorting;

import java.util.LinkedList;
import java.util.List;

import edu.eci.pgr.exceptions.CycleDetectedException;


public interface SortingTool {
	public void sort(LinkedList<Task> list)throws CycleDetectedException;
	public List<Task> getSortedList();
}
