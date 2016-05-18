package cvrp;

import java.util.ArrayList;
import java.util.Iterator;
import util.CustomList;

public class ClarkWrightHeuristic implements HeuristicCVRP {

	double[][] matrix = null;
	int capacity;
	int[] demands = null;
	int nbCustomer;
	long meanTime = 0;
	int nbFile = 0;
	
	@Override
	public double computeSolution(VRPinstance instance) {

		long start = System.currentTimeMillis();

		matrix = instance.getMatrix();
		capacity = instance.getCapacity();
		demands = instance.getDemands();
		nbCustomer = instance.getN();
		
		ArrayList<CustomList<Integer>> solution = new ArrayList<CustomList<Integer>>();

		for (int i = 1; i < nbCustomer; i++) {
			CustomList<Integer> tmp = new CustomList<Integer>();
			tmp.add(i);
			solution.add(tmp);
		}
		
		Double[][] savings = new Double[nbCustomer][nbCustomer];
		
		for (int i = 1; i < savings.length; i++) {
			for (int j = i + 1; j < savings.length; j++) {
				savings[i][j] = (matrix[i][0] + matrix[0][j]) - matrix[i][j];
			}
		}
		
		double bestSavings = -1;
		
		while (bestSavings != 0) {
			
			int clientI = 0;
			int clientJ = 0;
			bestSavings = 0;
			
			for (int i = 1; i < savings.length; i++) {
				for (int j = i + 1; j < savings.length; j++) {
					if(savings[i][j] >= bestSavings) {
						bestSavings = savings[i][j];
						clientI = i;
						clientJ = j;
					}
				}
			}
			
			savings[clientI][clientJ] = 0.0;
						
			int posI = 0;
			int posJ = 0;
			for (int i = 0; i < solution.size(); i++) {
				for (Iterator<Integer> iterator = solution.get(i).iterator(); iterator.hasNext();) {
					
					int id = iterator.next();
					if(id == clientI) {
						posI = i;
					}
					
					if(id == clientJ) {
						posJ = i;
					}
					
				}
			}
			
			double totalDemand = 0.0;
			for (Iterator<Integer> iterator = solution.get(posI).iterator(); iterator.hasNext();) {
				int id = iterator.next();
				totalDemand += demands[id];
				
			}
			
			for (Iterator<Integer> iterator = solution.get(posJ).iterator(); iterator.hasNext();) {
				int id = iterator.next();
				totalDemand += demands[id];
				
			}
			
			if (totalDemand <= capacity) {
				if (posI != posJ) {
					
					if(solution.get(posI).getFirst() == clientI && solution.get(posJ).getLast() == clientJ) {
							solution.get(posJ).append(solution.get(posI));
							solution.remove(posI);
					}
					
					else if (solution.get(posJ).getFirst() == clientJ && solution.get(posI).getLast() == clientI) {
						solution.get(posI).append(solution.get(posJ));
						solution.remove(posJ);

					}
				}		
			}		
			
		}
		
		System.out.println("=======Solution CVRP : Clark and Wright Heuristic =======");
		
		System.out.println();

		
		System.out.println("Nombre de véhicules : " + solution.size());
		System.out.println("Capacité MAX : " + capacity);
		System.out.println();
		
		double value = 0.0;
		
		for (int i = 0; i < solution.size(); i++) {
			double cost = costSubTour(solution.get(i));
			value += cost;
			System.out.print(solution.get(i) + " avec coût de : " + cost);
			System.out.println();
		}
		
		long end = System.currentTimeMillis();

		long ms = (end - start);

		
		System.out.println();
		System.out.println("Temps de résolution : " + ms + " ms");
		System.out.println("Résultat : " + value);	
	
		return value;
	}
	
	public double costSubTour(CustomList<Integer> customList) {
				
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		
		for (Iterator<Integer> iterator = customList.iterator(); iterator.hasNext();) {
			
			tmp.add(iterator.next());
			
		}
		double costSubTour = matrix[0][tmp.get(0)] + matrix[tmp.get(tmp.size() - 1)][0];
		
		for (int i = 0; i < tmp.size() - 1; i++) {
			costSubTour += matrix[tmp.get(i)][tmp.get(i+1)];
		}
				
		return costSubTour;
	}

}
