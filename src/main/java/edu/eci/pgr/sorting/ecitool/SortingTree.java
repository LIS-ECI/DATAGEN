package edu.eci.pgr.sorting.ecitool;

import java.util.ArrayList;
import java.util.List;

import edu.eci.pgr.sorting.Task;

public class SortingTree {
	private SortingTreeNode head;
	private List<SortingTree> children;
	
	public SortingTree() {
		super();
		children = new ArrayList<SortingTree>();
	}

	public SortingTree(SortingTreeNode head) {
		super();
		this.head = head;
		children = new ArrayList<SortingTree>();
	}

	public SortingTree(SortingTreeNode head, List<SortingTree> children) {
		super();
		this.head = head;
		this.children = children;
	}

	public SortingTreeNode getHead() {
		return head;
	}

	public void setHead(SortingTreeNode head) {
		this.head = head;
	}

	public List<SortingTree> getChildren() {
		return children;
	}

	public void addChild(SortingTree child){
		children.add(child);
	}
	
	public SortingTreeNode getSortingTreeNode(Task node){
		if(node.getTable().getName().equals(head.getValue().getTable().getName()))
			return head;
		if(children == null || children.size()<=0)
			return null;
		SortingTreeNode response = null;
		for(int i = 0; i < children.size() && response==null; i++){
			response = children.get(i).getSortingTreeNode(node);
		}
		return response;
	}
	
	public int getLevelValue(Task node){
		if(node.getTable().getName().equals(head.getValue().getTable().getName()))
			return head.getLevel();
		if(children == null || children.size()<=0)
			return -1;
		int value = -1;
		for(int i = 0; i < children.size() && value == -1; i++)
			value = children.get(i).getLevelValue(node);
		return value;
	}
	
	public boolean addChildToParent(Task child, SortingTreeNode parent){
		if(parent.getValue().getTable().getName().equals(head.getValue().getTable().getName())){
			SortingTreeNode chd = new SortingTreeNode(child);
			chd.setLevel(parent.getLevel()+1);
			children.add(new SortingTree(chd));
			return true;
		}
		if(parent.getValue().getTable().getName().equals(child.getTable().getName())){
			addChild(new SortingTree(new SortingTreeNode(child)));
			return true;
		}
		if(children == null || children.size()<=0)
			return false;
		boolean response = false;
		for(int i = 0; i < children.size() && !response; i++){
			response = children.get(i).addChildToParent(child,parent);
		}
		return response;
	}
	
	
}
