package cvrp;

import java.util.ArrayList;
import java.util.List;

import tsp.DecreasingArcHeuristicTSP;
import tsp.HeuristicTSP;
import tsp.Arc;

public class GiantTourHeuristic implements HeuristicCVRP {

	
	double[][] matrix = null;
	int capacity;
	int[] demands = null;
	int nbCustomer;
	
	@Override
	public double computeSolution(VRPinstance instance) {
		
		matrix = instance.getMatrix();
		capacity = instance.getCapacity();
		demands = instance.getDemands();
		nbCustomer = instance.getN();
			
		HeuristicTSP h = new DecreasingArcHeuristicTSP();
		List<Integer> solutionTSP = new ArrayList<Integer>();		
		h.computeSolution(matrix, solutionTSP);
		
//		for (Integer integer : solutionTSP) {
//			System.out.print(integer + "-");
//		}
//		System.out.println();
		
		
		List<Integer> tour = new ArrayList<Integer>();
		List<Arc> listArcs = new ArrayList<Arc>();
		
		double sumDemand = 0;
		double costTour = 0;
		for (int i = 1; i < solutionTSP.size() - 1; i++) {
			tour.add(solutionTSP.get(i));
			for (int j = i + 1; j < solutionTSP.size(); j++) {
				tour.add(solutionTSP.get(j));
				for (Integer integer : tour) {
					sumDemand += demands[integer];
				}
				
				if (sumDemand < capacity) {
					costTour = matrix[0][tour.get(0)] + matrix[tour.get(tour.size() - 1)][0];
					for (int k = 0; k < tour.size() - 1; k++) {
						costTour += matrix[tour.get(k)][tour.get(k + 1)];
					}
					listArcs.add(new Arc(tour.get(0), tour.get(tour.size() - 1), costTour));					
					
				}
			}
			
			sumDemand = 0;
			tour.clear();
		}
		
		for (Arc arc : listArcs) {
			System.out.println("Arc : " + arc.getSourceNode() + "-" + arc.getTargetNode() + " avec poids : " + arc.getWeight());
		}
		
		
		
		
		
		
		
		
		return 0;
	}
	
	

}
