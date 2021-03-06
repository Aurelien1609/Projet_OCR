package tsp;
import java.util.ArrayList;
import java.util.List;




/**
 * This heuristic sorts the arcs by increasing value and 
 * considers each arc in turn for insertion
 * An arc is inserted if and only if it does not create a subtour.
 * The method stops when a tour is obtained.
 */
public class DecreasingArcHeuristicTSP implements HeuristicTSP {

	long meanTime = 0;
	int nbFile = 0;
	public List<Arc> listArcs = new ArrayList<Arc>();
	public List<Node> listNodes = new ArrayList<Node>();
	
	/** TODO coder cette méthode */
	public double computeSolution(double[][] matrix, List<Integer> solution) {
		
		long start = System.currentTimeMillis();
		listArcs = new ArrayList<Arc>();
		listNodes = new ArrayList<Node>();
		
		double value = 0.0;
		createGraph(matrix);
		listArcs.sort(null);		
		for (Arc arc : listArcs) {
			if (arc.getSource().getSuccNode() == null 
					&& arc.getTarget().getPredNode() == null 
					&& arc.getSource() != arc.getTarget().getSuccNode()) {

				arc.getSource().setSuccNode(arc.getTarget());
				arc.getTarget().setPredNode(arc.getSource());
				
				if (findCycle(arc.getSource())) {
					arc.getSource().setSuccNode(null);
					arc.getTarget().setPredNode(null);
				}
				
				else {
					value += arc.getWeight();
				}
				

			}
		}
		
		Node currentNode = listNodes.get(0);
		int inc = 0;
		solution.add(Integer.parseInt(currentNode.getName()));
		while (currentNode.getSuccNode() != null && inc < listNodes.size() - 1) {
			currentNode = currentNode.getSuccNode();
			solution.add(Integer.parseInt(currentNode.getName()));
			inc += 1;
		}
		
		long end = System.currentTimeMillis();
		long ms = (end - start);
		System.out.println("=========SolutionTSP : Decreasing Arc Heuristic =========");
		for (int i = 0; i < solution.size() - 1; i++) {
			System.out.print(solution.get(i) + "-");
		}
		
		System.out.print(solution.get(solution.size() - 1));
		
		System.out.println();
		System.out.println("Temps de résolution : " + ms + " ms");
		System.out.println("Valeur du TSP : " + value);
		System.out.println();

	
		return value;
	}
	
	public Boolean findCycle(Node node) {
		
		String nameNode = node.getName();
		Boolean cycle = false;
		Boolean res = true;
		Node actifNode = node.getSuccNode();

		if (actifNode != null) {
			int lenghtCycle = 1;
			while (res) {
				
				if (actifNode != null) {
					if (actifNode.getName() == nameNode) {
						cycle = true;
						res = false;
						}
					
					actifNode = actifNode.getSuccNode();
					lenghtCycle += 1;
					
					if (lenghtCycle == listNodes.size()) {
						res = false;
					}
				}
				
				else {res = false;}
				
				
			}	
		}
				
		return cycle;
	}	
	
	public void createGraph(double[][] matrix)
	{
		for (int i = 0; i < matrix.length; i++) {
			listNodes.add(new Node(i));
		}
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (i != j) listArcs.add(new Arc(listNodes.get(i), listNodes.get(j), matrix[i][j]));
			}
		}		
		
	}

}