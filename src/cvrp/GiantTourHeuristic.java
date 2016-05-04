package cvrp;

import java.util.ArrayList;
import java.util.List;

import tsp.DecreasingArcHeuristicTSP;
import tsp.HeuristicTSP;
import tsp.Node;
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
		
		for (Integer integer : solutionTSP) {
			System.out.print(integer + "-");
		}
		System.out.println();
		
		
		List<Integer> tour = new ArrayList<Integer>();
		List<Arc> listArcs = new ArrayList<Arc>();
		Double[] result = new Double[solutionTSP.size()];
		result[0] = 0.0;
		for (int i = 1; i < result.length; i++) {
			result[i] = Double.MAX_VALUE;
		}
		Integer[] bestPred = new Integer[solutionTSP.size()];
		bestPred[0] = 0;
		
		double sumDemand = 0;
		double costTour = 0;
		for (int i = 1; i < solutionTSP.size() - 1; i++) {
			tour.add(solutionTSP.get(i));
			for (int j = i; j < solutionTSP.size(); j++) {
				if (i != j) {
					tour.add(solutionTSP.get(j));
				}
				
				for (Integer integer : tour) {
					sumDemand += demands[integer];
				}
								
				if (sumDemand < capacity) {
					costTour = matrix[0][tour.get(0)] + matrix[tour.get(tour.size() - 1)][0];
					for (int k = 0; k < tour.size() - 1; k++) {
						costTour += matrix[tour.get(k)][tour.get(k + 1)];
					}
					
					listArcs.add(new Arc(solutionTSP.get(i - 1), solutionTSP.get(j), costTour));
					//System.out.println("poids pred = " + result[i - 1] + " poids arc : " + costTour);
					Double arcWeight = result[i - 1] + costTour;
					if (arcWeight < result[j]) {
						result[j] = arcWeight;
						bestPred[j] = solutionTSP.get(i - 1);
					}
					
				}
			}
			
			sumDemand = 0;
			tour.clear();
		}
		
		ArrayList<Integer> PCC = new ArrayList<Integer>();
		int currentNode = solutionTSP.get(solutionTSP.size() - 1);
		int predNode = bestPred[solutionTSP.size() - 1];
		PCC.add(currentNode);
		while (currentNode != 0) {
			int pos = posList(solutionTSP, predNode);
			currentNode = predNode;
			predNode = bestPred[pos];
			PCC.add(currentNode);

		}
		
		ArrayList<ArrayList<Integer>> solution = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < PCC.size(); i++) {
			solution.add(new ArrayList<Integer>());
		}
		
		System.out.println("=========PCC=================");

		int ite = 0;
		for (int i = PCC.size() - 2; i >= 0; i--) {
			System.out.print(PCC.get(i) + "--");
			
			while (solutionTSP.get(ite) != PCC.get(i)) {
				solution.get(i).add(solutionTSP.get(ite));
				ite++;				
			}
			
		}
		
		System.out.println();
		System.out.println("=======Solution=======");
		
		for (int i = 0; i < solution.size(); i++) {
			for (int k = 0; k < solution.get(i).size(); k++) {
				System.out.print(solution.get(i).get(k) + "--");
			}
			System.out.println();
		}
		
		
		return 0;
	}
	
	public int posList(List<Integer> listNodes, int nodes)
	{
		for (int i = 0; i < listNodes.size(); i++) {
			if (listNodes.get(i) == nodes) {
				return i;
			}
		}
		
		return -1;
	}
	

}
