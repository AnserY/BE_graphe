package org.insa.algo.shortestpath;


import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graph.*;
import org.insa.algo.utils.BinaryHeap;
public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Graph graph = data.getGraph();
        int nbrNodes = graph.size();
        BinaryHeap<Node> tas = new BinaryHeap<Node>();
        ArrayList<Arc> succ = new ArrayList<Arc>();
        Node NodeMin;
        
        Label label[] = new Label[nbrNodes];
        for(int i=0 ; i<nbrNodes;i++) {
        	label[i]=new Label(i);
        	label[i].setMarque(false);
        	label[i].setCout(Double.POSITIVE_INFINITY);
        	label[i].setPère(0);
        }
        label[0].setCout(0);
        tas.insert(graph.get(0));
     
         while(!label[data.getDestination().getId()].getMarque() || !tas.isEmpty()) {
        
        	 NodeMin=tas.deleteMin();
        	 label[NodeMin.getId()].setMarque(true);
             succ = NodeMin.getSuccessors();
             for(Arc arc : succ) {
            	 double coutY,coutX,w;
            	 w = data.getCost(arc);
            	 coutY=label[arc.getDestination().getId()].getCout();
            	 coutX=label[arc.getOrigin().getId()].getCout();
            	 
            	 if(!label[arc.getDestination().getId()].getMarque()) {
            		 
            		 //label[arc.getDestination().getId()].setCout(Min(label[arc.getDestination().getId()].getCout(),data.getCost(arc)));
            	
            		 if(coutY>)
            	 }
            	 
            	 
            	 
             }
        
        
        }
        return solution;
    }
    
    private double Min(double y , double x) {
    	double res = (y >= x) ? x : y;
    	return res;
    }

}
