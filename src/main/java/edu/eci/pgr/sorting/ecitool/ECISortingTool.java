package edu.eci.pgr.sorting.ecitool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.eci.pgr.business.TableVO;
import edu.eci.pgr.sorting.SortingTool;
import edu.eci.pgr.sorting.Task;
public class ECISortingTool implements SortingTool{
	private SortingTree tree;
	protected LinkedList<SortingTreeNode> elementsAddedToTheTree;
	
	public ECISortingTool(){
		tree = new SortingTree(new SortingTreeNode(new Task(new TableVO("HEAD"))));
		elementsAddedToTheTree = new LinkedList<SortingTreeNode>();
	}
	
	private void addNewRoot(SortingTreeNode head){
		tree.addChild(new SortingTree(head));
	}
	
	/**
	 * Creates a new tree according to the list of tables given
	 * @param list ,tables to be put in the tree
	 * @pre the list of tables has been sorted alphabetically
	 * @pos a new tree is created from the tables given
	 */
	public void sort(LinkedList<Task> list){
		while(list.size()>0){
			Task head = list.remove();
			/** If the table has no PKS it means the table can have new
			values and the integrity restriction won't be violated **/
			if(head.getDependenciesLength() == Task.NO_ELEMENTS){
				SortingTreeNode n = new SortingTreeNode(head);
				addNewRoot(n);
				if(!belongsToListOfElements(head))
					elementsAddedToTheTree.add(n);
			}
			else if(head.getDependenciesLength() == 1 && head.getDependency(0).getTable().getName().equals(head.getTable().getName())){
				SortingTreeNode n = new SortingTreeNode(head);
				addNewRoot(n);
				if(!belongsToListOfElements(head))
					elementsAddedToTheTree.add(n);
			}
			else{
				List<SortingTreeNode> parentsThatBelong = new ArrayList<SortingTreeNode>();
				for(int j=0;j<head.getDependencies().size();j++){
					SortingTreeNode parent = null;
					Task p = head.getDependencies().get(j);
					if(head.getTable().getName().equals(p.getTable().getName())){
						p.setAutoReferenced(true);
						parent = new SortingTreeNode(head);
					}else{
						if(belongsToListOfElements(head.getDependencies().get(j))){
							parent = getElementInTree(head.getDependencies().get(j));
						}else{
							parent = tree.getSortingTreeNode(head.getDependencies().get(j));
						}
					}
					if(parent!=null){
						parentsThatBelong.add(parent);
						if(!belongsToListOfElements(parent.getValue()) && !p.isAutoReferenced())
							elementsAddedToTheTree.add(parent);
					}
				}
				if(parentsThatBelong.size()<head.getDependencies().size())
					list.add(head);
				else{
					parentsThatBelong = new QuickSort().quicksortSortingTreeNode(parentsThatBelong);
					tree.addChildToParent(head, parentsThatBelong.get(parentsThatBelong.size()-1));
				}
			}
		}
	}
	
	private SortingTreeNode getElementInTree(Task element){
		if(!belongsToListOfElements(element))
			return null;
		for(int i=0;i<elementsAddedToTheTree.size();i++){
			if(elementsAddedToTheTree.get(i).getValue().getTable().getName().equals(element.getTable().getName())){
				return elementsAddedToTheTree.get(i);
			}
		}
		return null;
	}
	
	private boolean belongsToListOfElements(Task element){
		boolean res = false;
		for(int i=0;i<elementsAddedToTheTree.size()&&!res;i++){
			if(elementsAddedToTheTree.get(i).getValue().getTable().getName().equals(element.getTable().getName()))
				res= true;
		}
		return res;
	}
	public List<Task> getSortedList(){
		List<SortingTree> subTrees = tree.getChildren();
		LinkedList<SortingTree> queue = new LinkedList<SortingTree>();
		List<Task> result = new ArrayList<Task>();
		for(int i=0;i<subTrees.size();i++){
			queue.add(subTrees.get(i));
		}
		while(queue.size()>0){
			SortingTree head = queue.remove();
			result.add(head.getHead().getValue());
			for(int i=0;i<head.getChildren().size();i++){
				queue.add(head.getChildren().get(i));
			}
		}
		return result;
	}
}
