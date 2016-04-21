package cvrp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.CustomList;

public class ClarkAndWrightHeuristic implements HeuristicCVRP {

	double[][] matrix = null;
	int capacity = 0;
	int[] demands = null;
	int nbCustomer = 0;
	
	@Override
	public double computeSolution(VRPinstance instance) {
		// TODO Auto-generated method stub
		
		matrix = instance.getMatrix();
		capacity = instance.getCapacity();
		demands = instance.getDemands();
		nbCustomer = instance.getN();
		
		ArrayList<CustomList<Integer>> L = new ArrayList<CustomList<Integer>>();
		CustomList<Integer> tmp;
		//ArrayList<Double> savings = new ArrayList<Double>();
		
		// initial solution
		for (int i = 1; i < nbCustomer; i++) {
			tmp = new CustomList<Integer>();
			tmp.add(i);
			L.add(tmp);
		}
		
		Boolean positiveSavings = true;
		double bestSavings;
		double tmpSavings;
		int posIBestSavings;
		int posJBestSavings;
		
		while (positiveSavings) {
			
			bestSavings = Integer.MIN_VALUE;
			tmpSavings = 0;
			posIBestSavings = 0;
			posJBestSavings = 0;


			// compute savings for all pair (i,j)
			for (int i = 0; i < L.size() - 1; i++) {
				for (int j = i + 1; j < L.size(); j++) {
					
					//System.out.println("(" + i + "," + j + ")");
					//System.out.println(L.get(i) + " et " + L.get(j));
					
					tmpSavings = computeSaving(L.get(i), L.get(j));
					if (tmpSavings > bestSavings) {
						//System.out.println("valeur tmp : " + tmpSavings + " best valeur : " + bestSavings + " en pos (" + i + "," + j + ")");
						posIBestSavings = i;
						posJBestSavings = j;
						bestSavings = tmpSavings;
					}
				}			
			}
			
			System.out.println(" best : " + L.get(posIBestSavings) + " et " + L.get(posJBestSavings) + " avec valeur de " + bestSavings);
			
			if (bestSavings > 0) {
				L.get(posIBestSavings).append(L.get(posJBestSavings));
				L.remove(posJBestSavings);
				
				for (CustomList<Integer> tour : L) {
					System.out.println(tour);
				}
			}
			
			else {
				positiveSavings = false;
			}
			
			
			
		}
		
		
		
		
		
		
				
		
		
		
		
		return 0;
	}
	
	public double computeSaving(CustomList<Integer> tour1, CustomList<Integer> tour2) {
		
		CustomList<Integer> tmp = new CustomList<Integer>();
		
		Iterator<Integer> ite = tour1.iterator();
		int node;
		while (ite.hasNext()) {
			node = ite.next();
			tmp.add(node);
		}
		
		tmp.add(tour1.getLast());
		Iterator<Integer> ite2 = tour2.iterator();
		while (ite2.hasNext()) {
			node = ite2.next();
			tmp.add(node);
		}
		
		tmp.add(tour2.getLast());
				
		return costTour(tour1) + costTour(tour2) - costTour(tmp);
	}
	
	public double costTour(CustomList<Integer> tour)
	{		
		double cost = matrix[0][tour.getFirst()] + matrix[tour.getLast()][0]; 
		
		Iterator<Integer> ite = tour.iterator();
		int node1 = ite.next();
		int node2 = 0;
		
		try {
			while (ite.hasNext()) {
				node2 = ite.next();
				cost += matrix[node1][node2];
				node1 = node2;
			}
			
			node2 = ite.next();
			cost += matrix[node1][node2];
		} 
		
		catch (NullPointerException e) {
			// TODO: handle exception
		}
		
		
		return cost;
	}

}
