package cvrp;

import java.util.ArrayList;
import java.util.Iterator;

import util.CustomList;

public class ClarkWrightHeuristic2 implements HeuristicCVRP {

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
					if(savings[i][j] > bestSavings) {
						bestSavings = savings[i][j];
						clientI = i;
						clientJ = j;
					}
				}
			}
			
			System.out.println(bestSavings + " en position : " + clientI + " " + clientJ);
			savings[clientI][clientJ] = 0.0;
			
			// mettre le BEST à 0
			
			int posI = 0;
			int posJ = 0;
			for (int i = 0; i < solution.size(); i++) {
				for (Iterator<Integer> iterator = solution.get(i).iterator(); iterator.hasNext();) {
					
					int pos = iterator.next();
					if(pos == clientI) {
						posI = i;
					}
					
					if(pos == clientJ) {
						posJ = i;
					}
					
				}
			}
			
			if (posI != posJ) {
				if((solution.get(posI).getFirst() == clientI && solution.get(posJ).getLast() == clientJ) || 
						(solution.get(posJ).getFirst() == clientJ && solution.get(posI).getLast() == clientI)) {
							// mal implémenté : doit calculer toutes les demandes des clients de la liste !
							double totalDemand = demands[clientI] + demands[clientJ];
							if (totalDemand <= capacity) {
								solution.get(posI).append(solution.get(posJ));
								solution.remove(posJ);						
							}
							
						}
			}
			
			
			
			// vérifier : premier et dernier de la liste + capacité non dépassé + appartiennent à deux cycles différents
		
		}
		
		for (int i = 0; i < solution.size(); i++) {
			System.out.println(solution.get(i));
		}
		
		

		
		
		
		
		
		return 0;
	}

}
