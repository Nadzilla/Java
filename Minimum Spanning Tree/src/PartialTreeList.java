package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {
    
	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;
		
		/**
		 * Next node in linked list
		 */
		public Node next;
		
		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;
	
	/**
	 * Number of nodes in the CLL
	 */
	private int size;
	
	/**
	 * Initializes this list to empty
	 */
    public PartialTreeList() {
    	rear = null;
    	size = 0;
    }

    /**
     * Adds a new tree to the end of the list
     * 
     * @param tree Tree to be added to the end of the list
     */
    public void append(PartialTree tree) {
    	Node ptr = new Node(tree);
    	if (rear == null) {
    		ptr.next = ptr;
    	} else {
    		ptr.next = rear.next;
    		rear.next = ptr;
    	}
    	rear = ptr;
    	size++;
    }

    /**
     * Removes the tree that is at the front of the list.
     * 
     * @return The tree that is removed from the front
     * @throws NoSuchElementException If the list is empty
     */
    public PartialTree remove() 
    throws NoSuchElementException {
    		
    	if(size == 0) {
    		throw new NoSuchElementException("Ain't nothing in here"); 
    	}
    	
    	if(size == 1) {
    		size = 0; 
    		Node x = rear; 
    		rear.next = null; 
    		rear = null; 
    		
    		return x.tree; 
    		
    	}
    	
    	else {
    		Node oldFront = rear.next; 
    		Node newFront = rear.next.next; 
    		rear.next = newFront; 
    		size -= 1;  
    		return oldFront.tree; 
    	}
    	
    	
    }

    /**
     * Removes the tree in this list that contains a given vertex.
     * 
     * @param vertex Vertex whose tree is to be removed
     * @return The tree that is removed
     * @throws NoSuchElementException If there is no matching tree
     */
    public PartialTree removeTreeContaining(Vertex vertex) 
    throws NoSuchElementException {
    	 Node ptr = rear; 
    	 PartialTree treeReturn = null;

         do {
            
             PartialTree finalTree = ptr.tree;
    		 boolean check = inTheTree(finalTree, vertex); 
    		 System.out.println(ptr.tree.toString() + " ");
    		
    		if (check == true) {
    			treeReturn = finalTree; 
    			RemoveTheDamnTree(ptr); 
    			break; 
    		}
    	     	ptr = ptr.next; 
    	     	System.out.println("ah");
    		
    		
    	} while (ptr.next != rear); 
    		
    	
    	
    	if (treeReturn == null) {
    		return null; 
    	} else {
    		System.out.println("TREE!" + " "+treeReturn.toString());
    		lastProject(ptr);
    		return treeReturn;
    	}
    	
    	
     }
 
    private boolean inTheTree (PartialTree tree, Vertex vertex) {
    	
    	  Vertex fatherTree = vertex;
          while (fatherTree.parent != fatherTree) {         
        	  fatherTree = fatherTree.parent;
        	  System.out.println(fatherTree.name);
          }
          
          return fatherTree == tree.getRoot();
    	
    	 
    }
    private void RemoveTheDamnTree (Node theNode) {
    	Node firstNode;               
    	firstNode = theNode;
        while (!(firstNode.next == theNode)) {
        	firstNode =firstNode.next;
	     	System.out.println(firstNode.toString());

        }

        
        Node nodeTwo = theNode.next;

       
        if (nodeTwo == theNode && firstNode == theNode) {
            rear = null;
            size--;
        }
        
        else if (nodeTwo == firstNode) {
            if (theNode == rear) {                        
                rear = rear.next;
            }
            //
            (theNode.next).next = theNode.next;            
            size--;
        }
       
        else {
            if (theNode == rear) {
                
                rear = firstNode;
            }
            //
            firstNode.next = nodeTwo;
            size--;
        }
       
        theNode.next = null;
    }
    
    /**
     * Gives the number of trees in this list
     * 
     * @return Number of trees
     */
    public int size() {
    	return size;
    }
    private void lastProject(Node tree) {
    	System.out.println("Tree" + tree.tree.toString());
    	for(int i = 0; i < 10; i++) {
    		System.out.println("Tree" + tree.tree.toString());
    	}
    	
    }
    
    /**
     * Returns an Iterator that can be used to step through the trees in this list.
     * The iterator does NOT support remove.
     * 
     * @return Iterator for this list
     */
    public Iterator<PartialTree> iterator() {
    	return new PartialTreeListIterator(this);
    }
    
    private class PartialTreeListIterator implements Iterator<PartialTree> {
    	
    	private PartialTreeList.Node ptr;
    	private int rest;
    	
    	public PartialTreeListIterator(PartialTreeList target) {
    		rest = target.size;
    		ptr = rest > 0 ? target.rear.next : null;
    	}
    	
    	public PartialTree next() 
    	throws NoSuchElementException {
    		if (rest <= 0) {
    			throw new NoSuchElementException();
    		}
    		PartialTree ret = ptr.tree;
    		ptr = ptr.next;
    		rest--;
    		return ret;
    	}
    	
    	public boolean hasNext() {
    		return rest != 0;
    	}
    	
    	public void remove() 
    	throws UnsupportedOperationException {
    		throw new UnsupportedOperationException();
    	}
    	
    }
}


