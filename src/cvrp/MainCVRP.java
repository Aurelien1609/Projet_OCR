package cvrp;

public class MainCVRP {

	public static void main(String args[]) {

		VRPinstance instance = null;
		try {
			instance = new VRPinstance("CVRP_Instances_Augerat/A-n32-k5.vrp");
			HeuristicCVRP h = new ClarkAndWrightHeuristic();
			h.computeSolution(instance);
			
		} catch (java.io.FileNotFoundException e) {
			System.out.println("File not found");
		}

		
		
	}

}
