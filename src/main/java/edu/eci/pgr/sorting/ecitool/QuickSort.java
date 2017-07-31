package edu.eci.pgr.sorting.ecitool;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import edu.eci.pgr.sorting.Task;

public class QuickSort {
	public List<Task> quicksortNode(List<Task> list) {
		String [] array = new String[list.size()];
		for(int i=0;i<array.length;i++){
			array[i] = list.get(i).getTable().getName();
		}
		Arrays.sort(array);
		LinkedList<Task> ret = new LinkedList<Task>();
		for(int i=0;i<array.length;i++){
			ret.add(getTask(array[i], list));
		}
		return ret;
	}
	
	public List<SortingTreeNode> quicksortSortingTreeNode(List<SortingTreeNode>list){
		int [] array = new int[list.size()];
		for(int i=0;i<array.length;i++)
			array[i] = list.get(i).getLevel();
		Arrays.sort(array);
		LinkedList<SortingTreeNode> ret = new LinkedList<SortingTreeNode>();
		for(int i=0;i<array.length;i++){
			ret.add(getTreeNode(array[i], list));
		}
		return ret;
	}

	private Task getTask(String name,List<Task> list){
		Task task = null;
		for(int i=0;i<list.size()&& task == null;i++)
			if(list.get(i).getTable().getName().equals(name))
				task = list.get(i);
		return task;
	}
	private SortingTreeNode getTreeNode(int level,List<SortingTreeNode> list){
		SortingTreeNode node = null;
		for(int i=0;i<list.size()&& node == null;i++)
			if(list.get(i).getLevel()== level)
				node = list.get(i);
		return node;
	}
	
//	public static void main(String []args){
//		List<Task> lista = new ArrayList<Task>();
//		lista.add(new Task("Beatriz"));
//		lista.add(new Task("Diana"));
//		lista.add(new Task("Alfredo"));
//		lista.add(new Task("David"));
//		lista.add(new Task("Carlos"));
//		lista.add(new Task("Sergio"));
//		lista.add(new Task("Oscar"));
//		lista.add(new Task("Kathe"));
//		lista.add(new Task("Alejandro"));
//		
//		List<Task> other = quicksortNode(lista);
//		
//		System.out.println("La lista organizada es: ");
//		for(int i=0;i<other.size();i++){
//			System.out.println(other.get(i).getName());
//		}
//	}
}
