package org.insa.algo.shortestpath;


import java.util.*;

import java.util.Map.Entry;


import java.util.Arrays;
import java.util.Collections;

import org.insa.graph.*;
import org.insa.algo.AbstractSolution.Status;
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
        int k=0,j=0,f=0;
        
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        ArrayList<Arc> succ = new ArrayList<Arc>();
        Label LabelMin;
        
        Map<Node,Integer> HM = new HashMap<>();
        
        Label label[]= new Label[nbrNodes];
           
        
        
        for(int i=0 ; i<nbrNodes;i++) {
        	HM.put(graph.get(i), i);
        	label[i]= new Label(i,false,Double.POSITIVE_INFINITY,null,graph.get(i));
        	
        }
       

        label[HM.get(data.getOrigin())].setCout(0);
        tas.insert(label[HM.get(data.getOrigin())]);
        notifyOriginProcessed(data.getOrigin());
        
        
       
        while(!label[HM.get(data.getDestination())].getMarque() && !tas.isEmpty()) {
        	
        
        	 LabelMin=tas.deleteMin();
        	 
        	 label[LabelMin.getsommetCourant()].setMarque(true);
        	 //System.out.println(label[LabelMin.getsommetCourant()].getCout());
        	 notifyNodeMarked(LabelMin.getNode());
        	 
        	 succ = LabelMin.getNode().getSuccessors();
             
            for(Arc arc : succ) {
            
            	double coutY,coutX,w,tempCout;
            	 w = data.getCost(arc);
            	
            	 coutY=label[HM.get(arc.getDestination())].getCout();
            	 
            	 notifyNodeReached(arc.getDestination());
            	 
            	 coutX=label[HM.get(arc.getOrigin())].getCout();
            	 
            	 if(!label[HM.get(arc.getDestination())].getMarque()) {
            		 
            		 
            		if(coutY>coutX+w) {
            			 
            			 tempCout=coutY;
            			 coutY=coutX+w;
            			 label[HM.get(arc.getDestination())].setCout(coutY);
            			 label[HM.get(arc.getDestination())].setArcPredec(arc);
            			 label[HM.get(arc.getDestination())].setPère(arc.getOrigin());
            			 
            			 
            			 if(tempCout==Double.POSITIVE_INFINITY) {
            				 tas.insert(label[HM.get(arc.getDestination())]);
            			 }
            			 else {
            				 tas.remove(label[HM.get(arc.getDestination())]);
            				 tas.insert(label[HM.get(arc.getDestination())]);
            			 }
            		 }
            	 }
            	 
            	 
            	++j; 
             }
        ++k;
        
        }
        //System.out.println("le nombre d'iteration :"+(k+j));
     	solution = null;
     	if(label[HM.get(data.getDestination())].getArcPrede() == null) {
     		solution = new ShortestPathSolution(data, Status.INFEASIBLE);
     	}else {
     		notifyDestinationReached(data.getDestination());
     		ArrayList<Arc> arcs = new ArrayList<>();
			Node tempNode = data.getDestination();
			while (label[HM.get(tempNode)].getPère() != null) {
				++f;
				arcs.add(label[HM.get(tempNode)].getArcPrede() );
				tempNode = label[HM.get(tempNode)].getPère();
			}
           //System.out.println("le nombre d'arc :"+f);
			Collections.reverse(arcs);
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));

     	 }
        return solution ;
    }
    
    
}
