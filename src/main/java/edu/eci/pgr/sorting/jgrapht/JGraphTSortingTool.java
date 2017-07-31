package edu.eci.pgr.sorting.jgrapht;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;

import edu.eci.pgr.exceptions.CycleDetectedException;
import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.sorting.SortingTool;
import edu.eci.pgr.sorting.Task;

public class JGraphTSortingTool implements SortingTool{
    private static final String REPRESENT = MessageBundleManager.getString("JGraphTSortingTool.0"); //$NON-NLS-1$
	private static final String TABLES = MessageBundleManager.getString("JGraphTSortingTool.1"); //$NON-NLS-1$
	private static final String CYCLES_DETECTED = MessageBundleManager.getString("JGraphTSortingTool.2"); //$NON-NLS-1$
	private DefaultDirectedGraph<String, DefaultEdge> graph;
    private List<Task> tasksList;
	public JGraphTSortingTool(){
		graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
	}
	public void sort(LinkedList<Task> list) throws CycleDetectedException{
		tasksList = list;
		for(int i=0;i<list.size();i++){
			graph.addVertex(list.get(i).getTable().getName());
		}
		for(int i=0;i<list.size();i++){
			if(list.get(i).getDependenciesLength()>0){
				for(int j=0;j<list.get(i).getDependenciesLength();j++){
					if(list.get(i).getTable().getName().equals(list.get(i).getDependency(j).getTable().getName()))
						list.get(i).setAutoReferenced(true);
					else{
						graph.addEdge(list.get(i).getTable().getName(), list.get(i).getDependency(j).getTable().getName());
					}
				}
			}
		}
		CycleDetector<String, DefaultEdge> cycleDetector = null;
		cycleDetector = new CycleDetector<String, DefaultEdge>(graph);
		String message = ""; //$NON-NLS-1$
		if(cycleDetector.detectCycles()){
			message = CYCLES_DETECTED;
	        Iterator<String> iterator;
	        Set<String> cycleVertices;
	        Set<String> subCycle;
	        String cycle;

	        cycleVertices = cycleDetector.findCycles();

	        while (! cycleVertices.isEmpty()) {
	           iterator = cycleVertices.iterator();
	           cycle = iterator.next();
	           // Get all vertices involved with this vertex.
	           subCycle = cycleDetector.findCyclesContainingVertex(cycle);
	           message+=TABLES;
	           for (String sub : subCycle) {
	              message +="   " + sub+"\n"; //$NON-NLS-1$ //$NON-NLS-2$
	              cycleVertices.remove(sub);
	           }
	           message+=REPRESENT;
	        }
	        throw new CycleDetectedException(message);
	     }
	}

	public List<Task> getSortedList() {
		LinkedList<Task> list = new LinkedList<Task>();
        TopologicalOrderIterator<String, DefaultEdge> orderIterator;
        orderIterator = new TopologicalOrderIterator<String, DefaultEdge>(graph);
        while (orderIterator.hasNext()) {
        	String name = orderIterator.next();
        	for(int i=0;i<tasksList.size();i++){
        		if(tasksList.get(i).getTable().getName().equals(name))
        			list.push(tasksList.get(i));
        	}
        }
        return list;
	}
}
