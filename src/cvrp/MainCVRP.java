package cvrp;

public class MainCVRP {

	public static void main(String args[]) {

		VRPinstance instance = null;
		try {
			//instance = new VRPinstance("CVRP_Instances_Augerat/P-n16-k8.vrp");
			instance = new VRPinstance("CVRP_Instances_Augerat/A-n32-k5.vrp");
			//instance = new VRPinstance("CVRP_Instances_Augerat/A-n44-k7.vrp");

			HeuristicCVRP h = new ClarkWrightHeuristic();
			//HeuristicCVRP h = new GiantTourHeuristic();
			h.computeSolution(instance);
			
		} catch (java.io.FileNotFoundException e) {
			System.out.println("File not found");
		}

		
		
	}

}
