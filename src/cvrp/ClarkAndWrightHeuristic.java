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
		double sumDemand;
		
		while (positiveSavings) {
			
			bestSavings = 0;
			tmpSavings = 0;
			posIBestSavings = 0;
			posJBestSavings = 0;

//			for (CustomList<Integer> tour : L) {
//				System.out.println(tour);
//			}
//			System.out.println("---------------------------");
			
			// compute savings for all pair (i,j)
			for (int i = 0; i < L.size() - 1; i++) {
				for (int j = i + 1; j < L.size(); j++) {					
					
					tmpSavings = computeSaving(L.get(i), L.get(j));
					System.out.println("-----------");
					sumDemand = sumDemand(L.get(i)) + sumDemand(L.get(j));
					
					//System.out.println(L.get(i) + " et " + L.get(j));
					//if (sumDemand <= capacity) System.out.println("capacité OK : " + sumDemand + " pour " + capacity);

					if (tmpSavings > bestSavings && sumDemand <= capacity) {
						//System.out.println("meilleur solution de fusion : " + tmpSavings + "contre " + bestSavings);
						posIBestSavings = i;
						posJBestSavings = j;
						bestSavings = tmpSavings;
					}
				}			
			}
						
			if (bestSavings > 0) {
				//System.out.println("Finalement, fusion de : " + L.get(posIBestSavings) + " et " + L.get(posJBestSavings));
				L.get(posIBestSavings).append(L.get(posJBestSavings));
				L.remove(posJBestSavings);
			}
			
			else {
				positiveSavings = false;
			}
			
			
			
		}
			
		System.out.println("------------------------");

		double value = 0;
		for (CustomList<Integer> customList : L) {
			value += costTour(customList);
			System.out.println(customList);
		}
		
		System.out.println("La distance totale des véhicules est de : " + value + " pour un nombre de camion de : " + L.size());
		
		return value;
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
	
	public double costTour(CustomList<Integer> tour) {		
//		System.out.println(tour);
//		System.out.println("cout de 0-" + tour.getFirst() + " = " + matrix[0][tour.getFirst()]);
		
		double cost = matrix[0][tour.getFirst()] + matrix[tour.getLast()][0]; 
		
		Iterator<Integer> ite = tour.iterator();
		int node1 = ite.next();
		int node2 = 0;
		
		try {
			while (ite.hasNext()) {
				node2 = ite.next();
//				System.out.println("cout de " + node1 + "-" + node2 + " = " + matrix[node1][node2]);
				cost += matrix[node1][node2];
				node1 = node2;
			}
			
			node2 = ite.next();
//			System.out.println("cout de " + node1 + "-" + node2 + " = " + matrix[node1][node2]);
			cost += matrix[node1][node2];
		} 
		
		catch (NullPointerException e) {}
		
//		System.out.println("cout de " + tour.getLast() + "-0 = " + matrix[tour.getLast()][0]);
//		System.out.println(cost);
		return cost;
	}
	
	public double sumDemand(CustomList<Integer> tour) {
		
		System.out.println(tour);
		double sumDemand = 0;
		Iterator<Integer> ite = tour.iterator();
		int node = 0;
		while (ite.hasNext()) {
			node = ite.next();
			System.out.println(node + " pour une valeur de " + demands[node]);
			sumDemand += demands[node];
		}
		
		node = ite.next();
		sumDemand += demands[node];
		System.out.println(node + " pour une valeur de " + demands[node]);

			
		return sumDemand;
	}

}
