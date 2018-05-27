package org.insa.algo.shortestpath;


import java.util.*;

import java.util.Map.Entry;

import org.insa.graph.*;
import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected ShortestPathSolution doRun() {
    	ShortestPathSolution solution = null;
    	ShortestPathData data = getInputData();
    	Graph graph = data.getGraph();
    	
    	int nbrNodes = graph.size();
    	BinaryHeap<Label> tas = new BinaryHeap<Label>();
    	ArrayList<Arc> succ = new ArrayList<Arc>();
    	
    	Map<Node,Integer> HM = new HashMap<>();
    	
    	
    	LabelAstar labelAstarMin;
    	
    	LabelAstar labelAstar[]= new LabelAstar[nbrNodes];
    	
    	
	    switch(data.getMode()) {
    	
	    
	    case LENGTH :
	    	for(int i=0;i<nbrNodes;i++) {
        		HM.put(graph.get(i), i);
        		labelAstar[i]= new LabelAstar(i,false,Double.POSITIVE_INFINITY,null,graph.get(i),graph.get(i).getPoint().distanceTo(data.getDestination().getPoint()));
        		
    		    } break;
	    case TIME :
	    	for(int i=0;i<nbrNodes;i++) {
        		HM.put(graph.get(i), i);
        		labelAstar[i]= new LabelAstar(i,false,Double.POSITIVE_INFINITY,null,graph.get(i),graph.get(i).getPoint().distanceTo(data.getDestination().getPoint())/(130.0f));
        		
    		    } break;
    	
	    }
    
    	labelAstar[HM.get(data.getOrigin())].setCout(0);
    	tas.insert(labelAstar[HM.get(data.getOrigin())]);
    	
    	 notifyOriginProcessed(data.getOrigin());
    	 
    	 
    	 while(!labelAstar[HM.get(data.getDestination())].getMarque() && !tas.isEmpty()) {
    		 
    		 labelAstarMin=(LabelAstar) tas.deleteMin();
    		 
    		 labelAstar[labelAstarMin.getsommetCourant()].setMarque(true);
    		 
    		 notifyNodeMarked(labelAstarMin.getNode());
    		 
    		 succ = labelAstarMin.getNode().getSuccessors();
    		 
    		 
    		    
             for(Arc arc : succ) {
             
             	double coutY,coutX,w,tempCout;
             	 w = data.getCost(arc);
             	
             	 coutY=labelAstar[HM.get(arc.getDestination())].getCout();
             	 
             	 notifyNodeReached(arc.getDestination());
             	 
             	 coutX=labelAstar[HM.get(arc.getOrigin())].getCout();
             	 
             	if(!labelAstar[HM.get(arc.getDestination())].getMarque()) {
             	
             		 
            		if(coutY>coutX+w) {
            			 
            			 tempCout=coutY;
            			 coutY=coutX+w;
            			 
            			 labelAstar[HM.get(arc.getDestination())].setCout(coutY);
            			 labelAstar[HM.get(arc.getDestination())].setArcPredec(arc);
            			 labelAstar[HM.get(arc.getDestination())].setPère(arc.getOrigin());
            			 
            			 if(tempCout==Double.POSITIVE_INFINITY) {
            				 tas.insert(labelAstar[HM.get(arc.getDestination())]);
            			 }
            			 else {
            				 tas.remove(labelAstar[HM.get(arc.getDestination())]);
            				 tas.insert(labelAstar[HM.get(arc.getDestination())]);
            			 }
            			 
             		
             	
             	}
             	 
             }
    		 
    		 
    	 }
    	 }
    	 
    		solution = null;
         	if(labelAstar[HM.get(data.getDestination())].getArcPrede() == null) {
         		solution = new ShortestPathSolution(data, Status.INFEASIBLE);
         	}else {
         		notifyDestinationReached(data.getDestination());
         		ArrayList<Arc> arcs = new ArrayList<>();
    			Node tempNode = data.getDestination();
    			while (labelAstar[HM.get(tempNode)].getPère() != null) {
    				
    				arcs.add(labelAstar[HM.get(tempNode)].getArcPrede() );
    				tempNode = labelAstar[HM.get(tempNode)].getPère();
    			}
              
    			Collections.reverse(arcs);
    			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));

         	 }
    	 
    	 
    	 
    	 
    	 
    	
    	
    	
    	
    	return solution;
    }
}
