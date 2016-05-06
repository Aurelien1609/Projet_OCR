package tsp;
import java.util.List;

public class MainTSP {

	
	private static char stringtoCode(String s){
		if(s.equals("-h1") || s.equals("--heuristic1")) return 'h';
		if(s.equals("-h2") || s.equals("--heuristic2")) return 'H';
		if(s.equals("-e") || s.equals("--exact")) return 'e';
		if(s.equals("-l1") || s.equals("--lowerbound")) return 'l';
		return '0';
	}
	
	
	/** run the test 
	 * 
	 * Syntax : TestTSP -{h1,h2,e,l} instanceName timeLimit
	 * 
	 * h1 : closest neighbor
	 * h2 : arc insertion heuristic
	 * e  : branch and bound
	 * l  : lower bound
	 * 
	 * Parameter timeLimit is only used when the first parameter is e
	 * 
	 * */
	public static void main(String args[]) {
		TestTSP tt = new TestTSP();		
		//tt.loadFile(args[1]);
		//tt.loadFileList("instances");
		//tt.loadFile("toy_instances/toy02.tsp");
		tt.loadFileList("toy_instances");
		//tt.loadFile("instances/bier127.tsp");
		//tt.loadFile("instances/brd14051.tsp");



		List<Double> listRes; // list of results

//		if(args.length < 2){
//		    System.out.println("Argument Error\nSyntax : TestTSP -{h1,h2,e,l} instanceName timeLimit");
//		    return;
//		}
		
//		listRes = tt.testHeuristic(new InsertHeuristicTSP());
//		System.out.println("Heuristic insertion : " + TestTSP.avgVal(listRes)
//				+ " on average");
//		listRes = tt.testHeuristic(new DecreasingArcHeuristicTSP());
//		System.out.println("Heuristic decreasing arcs : " + TestTSP.avgVal(listRes)
//				+ " on average");
		
		
		//switch(stringtoCode(args[0])){
		switch(stringtoCode("-h2")){
		case 'h' : // heuristic
			listRes = tt.testHeuristic(new InsertHeuristicTSP());
			System.out.println("Heuristic insertion : " + TestTSP.avgVal(listRes)
					+ " on average");
			break;
		case 'H' : // heuristic
			listRes = tt.testHeuristic(new DecreasingArcHeuristicTSP());
			System.out.println("Heuristic decreasing arcs : " + TestTSP.avgVal(listRes)
					+ " on average");
			break;			
		default :  // error
			System.out.println("Argument Error\nSyntax : TestTSP -{h1,h2,e,l} instanceName timeLimit");
		}
	}

	
	
	
}
