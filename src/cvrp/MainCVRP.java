package cvrp;

public class MainCVRP {

	public static void main(String args[]) {

		VRPinstance instance = null;
		try {
			//instance = new VRPinstance("CVRP_Instances_Augerat/P-n16-k8.vrp");
			instance = new VRPinstance("CVRP_Instances_Augerat/A-n32-k5.vrp");
			//instance = new VRPinstance("CVRP_Instances_Augerat/A-n80-k10.vrp");

			HeuristicCVRP h = new ClarkWrightHeuristic();
			HeuristicCVRP h1 = new GiantTourHeuristic();
			
			h.computeSolution(instance);
			h1.computeSolution(instance);


			
		} catch (java.io.FileNotFoundException e) {
			System.out.println("File not found");
		}

		
		
	}

}
