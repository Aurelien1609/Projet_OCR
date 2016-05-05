package cvrp;

import java.util.ArrayList;
import java.util.List;

import tsp.DecreasingArcHeuristicTSP;
import tsp.HeuristicTSP;
import tsp.InsertHeuristicTSP;

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
			
		HeuristicTSP h = new InsertHeuristicTSP();
		//HeuristicTSP h = new DecreasingArcHeuristicTSP();

		List<Integer> solutionTSP = new ArrayList<Integer>();		
		h.computeSolution(matrix, solutionTSP);
				
		List<Integer> tour = new ArrayList<Integer>();
		Double[] result = new Double[solutionTSP.size()];
		result[0] = 0.0;
		for (int i = 1; i < result.length; i++) {
			result[i] = Double.MAX_VALUE;
		}
		
		Integer[] bestPred = new Integer[solutionTSP.size()];
		bestPred[0] = 0;
		
		double sumDemand = 0;
		double costTour = 0;
		for (int i = 1; i < solutionTSP.size(); i++) {
			tour.add(solutionTSP.get(i));
			for (int j = i; j < solutionTSP.size(); j++) {
				if (i != j) {
					tour.add(solutionTSP.get(j));
				}
				
				for (Integer integer : tour) {
					sumDemand += demands[integer];
				}
								
				if (sumDemand < capacity) {
					costTour = costSubTour(tour);
										
					Double arcWeight;
					int positionI;
					int positionJ;
					
					if (tour.size() == 1) {
						
						positionJ = posList(solutionTSP, tour.get(0));
						positionI = positionJ - 1;
						arcWeight = result[positionI] + costTour;
					}
					
					else {
						positionJ = posList(solutionTSP, tour.get(tour.size() - 1));
						positionI = posList(solutionTSP, tour.get(0)) - 1;

						arcWeight = result[positionI] + costTour;
						
					}
					
					if (arcWeight < result[positionJ]) {
						result[positionJ] = arcWeight;
						bestPred[positionJ] = solutionTSP.get(positionI);
					}
										
				}
				
				sumDemand = 0;

			}
			
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
		for (int i = 0; i < PCC.size() - 1; i++) {
			solution.add(new ArrayList<Integer>());
		}
		
		System.out.println("========= Plus court chemin =================");
		System.out.print(PCC.get(PCC.size() - 1) + "--");

		int subtour = 0;
		int ite = 1;
		for (int i = PCC.size() - 2; i >= 0; i--) {
			System.out.print(PCC.get(i) + "--");
			
			while (PCC.get(i) != solutionTSP.get(ite)) {
				solution.get(subtour).add(solutionTSP.get(ite));
				ite++;
			}
			solution.get(subtour).add(solutionTSP.get(ite));
			ite++;
			subtour++;			
		}
		
		System.out.println();
		System.out.println();

		System.out.println("=======Solution CVRP : Giant Tour Heuristic =======");
		
		System.out.println();

		
		System.out.println("Nombre de véhicules : " + solution.size());
		System.out.println("Capacité MAX : " + capacity);
		System.out.println();
		double value = 0.0;
		
		for (int i = 0; i < solution.size(); i++) {
			System.out.println(solution.get(i) + " avec coût de : " + costSubTour(solution.get(i)));
			value += costSubTour(solution.get(i));
		}
		
		System.out.println();
		System.out.println("Résultat : " + value);	
		
		return value;
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
	
	public double costSubTour(List<Integer> subTour) {
		
		double costSubTour = matrix[0][subTour.get(0)] + matrix[subTour.get(subTour.size() - 1)][0];
		
		for (int i = 0; i < subTour.size() - 1; i++) {
			costSubTour += matrix[subTour.get(i)][subTour.get(i+1)];
		}
		
		
		return costSubTour;
	}
	

}
