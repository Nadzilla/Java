package apps;

import java.io.IOException;
import java.util.ArrayList;

import structures.Graph;

public class Driver {
	public static void main(String[] args) throws IOException {
        
		Graph graph = new Graph("graph6.txt"); 
        
        PartialTreeList partialTreeList = MST.initialize(graph);
        
        ArrayList<PartialTree.Arc> arcArrayList = MST.execute(partialTreeList);
        

        for (int i = 0; i < arcArrayList.size(); i++) {
            PartialTree.Arc anArcArrayList = arcArrayList.get(i);
            System.out.println(anArcArrayList);
        }

	}
}
