package cvrp;

import java.io.File;
import java.io.FileNotFoundException;

public class MainCVRP {

	public static void main(String args[]) throws FileNotFoundException {
		
		File directory = new File("CVRP_Instances_Augerat");
		File[] files = directory.listFiles();
		double moy = 0.0;

		for (File file : files) {
			if (file.getName().endsWith(".vrp")) {
				String nameFile = file.getName();
				
				VRPinstance instance = null;
				try {

					instance = new VRPinstance("CVRP_Instances_Augerat/" + nameFile);
					System.out.println();
					System.out.println("Fichier : " + nameFile);
					
					HeuristicCVRP h = new ClarkWrightHeuristic();
					moy += h.computeSolution(instance);

//					HeuristicCVRP h1 = new GiantTourHeuristic();	
//					moy += h1.computeSolution(instance);


					
				} catch (java.io.FileNotFoundException e) {
					System.out.println("File not found");
				}

			}
		}
		
		System.out.println();
		System.out.println("Moyenne : " + moy / files.length);
		
		
		
	}

}
