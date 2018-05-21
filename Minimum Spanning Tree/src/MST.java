package apps;

import structures.*;
import java.util.ArrayList;
import java.util.Iterator;



public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	
		Vertex[] verticies; 
		verticies = graph.vertices;
		PartialTreeList list = new PartialTreeList(); 
		 
		for (int i = 0; i < verticies.length; i++) {
			PartialTree temp = new PartialTree(verticies[i]); 
			
			for(Vertex.Neighbor ptr = verticies[i].neighbors; ptr != null; ptr = ptr.next) {
				PartialTree.Arc arc = new PartialTree.Arc(verticies[i], ptr.vertex, ptr.weight); 
				temp.getArcs().insert(arc);
			}
			list.append(temp);
		}
		
		Iterator<PartialTree> iter = list.iterator();
		   while (iter.hasNext()) {
		       PartialTree pt = iter.next();
			   System.out.println(pt.toString() + "ah");
		   }
		return list;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		ArrayList<PartialTree.Arc> finalList = new ArrayList<PartialTree.Arc>(); 
		
		   while (ptlist.size() >= 2) {
			   
			 
			   PartialTree temp =  ptlist.remove();
		       System.out.println("x");
			   

		       MinHeap<PartialTree.Arc> heap = temp.getArcs(); 
		       PartialTree.Arc lowest = heap.deleteMin(); 
		       Vertex ptr1 = null; 
		       Vertex ptr2 = null; 
		       while (lowest != null) {
		    	    ptr1 = lowest.v1;
		    	    ptr2 = lowest.v2;
		    	   
		    	    PartialTree secondTree = ptlist.removeTreeContaining(ptr1);
		             if (secondTree == null) {
		            	 secondTree = ptlist.removeTreeContaining(ptr2);
		             } 
		             if (secondTree != null) {
		            	 finalList.add(lowest);
		                 ptlist.append(temp);
		            	 temp.merge(secondTree);
		                 
		                 break;
		             }
		    	    lowest = heap.deleteMin(); 
		      }    		    		       
		   }
		   
		  
		return finalList;
	}
	
	private static void checkIfTreeisNull (PartialTree tree, PartialTreeList list, Vertex ptr2 ) {
		 if (tree == null) {
        	 tree = list.removeTreeContaining(ptr2);
         } 
	}
}
