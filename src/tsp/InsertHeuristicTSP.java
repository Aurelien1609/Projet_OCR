package tsp;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * This heuristic iteratively appends a customer
 * to the current solution until a tour is obtained
 *
 */

public class InsertHeuristicTSP implements HeuristicTSP {

	/** TODO : coder cette méthode */
	public double computeSolution(double[][] matrix, List<Integer> solution) {
			
		ArrayList<Integer> nodesInSubTour = new ArrayList<Integer>();
		ArrayList<Integer> nodesOutSubTour = new ArrayList<Integer>();		
		
		ArrayList<Node> listNodes = new ArrayList<Node>();
		ArrayList<Arc> listArcs = new ArrayList<Arc>();
		
		// create all nodes in G
		for (int i = 0; i < matrix.length; i++) {
			Node node = new Node(i);
			listNodes.add(node);
			nodesOutSubTour.add(node.getId());
		}
		
		// fastest distance between two nodes
		double maxValue = 0.0;
		int posI = 0;
		int posJ = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] > maxValue && i != j) {
					maxValue = matrix[i][j];
					posI = i; posJ = j;
				}
			}
		}
		
		changeListNode(nodesOutSubTour, nodesInSubTour, posI);
		changeListNode(nodesOutSubTour, nodesInSubTour, posJ);
		listArcs.add(new Arc(listNodes.get(posI), listNodes.get(posJ), maxValue));
		listArcs.add(new Arc(listNodes.get(posJ), listNodes.get(posI), maxValue));
		
		listNodes.get(posI).setSuccNode(listNodes.get(posJ));
		listNodes.get(posJ).setPredNode(listNodes.get(posI));
		listNodes.get(posI).setPredNode(listNodes.get(posJ));
		listNodes.get(posJ).setSuccNode(listNodes.get(posI));
		
		while (nodesInSubTour.size() < matrix.length) {
			// compute distance node max with subtour
			maxValue = 0.0;
			int selectNode = 0;
			for (int nodeIn : nodesInSubTour) {
				for (int nodeOut : nodesOutSubTour) {
					if (matrix[nodeIn][nodeOut] > maxValue) {
						maxValue = matrix[nodeIn][nodeOut];
						selectNode = nodeOut;
					}
				}
			}
			
			// add arc mininmize cost subtour
			Arc arcInsert = null;
			double costInsert = Double.MAX_VALUE;
			double cost = 0.0;
			
			for (Arc arc : listArcs) {
				cost = matrix[arc.getSource().getId()][selectNode] + matrix[selectNode][arc.getTarget().getId()] - arc.getWeight();
				if (cost < costInsert) {
					costInsert = cost;
					arcInsert = arc;
				}
				
			}
								
			Boolean result = false;
			for (int i = 0; i < listArcs.size(); i++) {
				if (listArcs.get(i) == arcInsert) {
					listArcs.remove(i);
					result = true;
					arcInsert.getSource().setSuccNode(null);
					arcInsert.getTarget().setPredNode(null);
				}
			}
			
			if (result) {
				Arc arc1 = new Arc(arcInsert.getSource(), listNodes.get(selectNode), matrix[arcInsert.getSource().getId()][selectNode]);
				Arc arc2 = new Arc(listNodes.get(selectNode), arcInsert.getTarget(), matrix[selectNode][arcInsert.getTarget().getId()]);
				listArcs.add(arc1);
				listArcs.add(arc2);
				arc1.getSource().setSuccNode(arc1.getTarget());
				arc1.getTarget().setPredNode(arc1.getSource());
				arc2.getSource().setSuccNode(arc2.getTarget());
				arc2.getTarget().setPredNode(arc2.getSource());
			}
			
			changeListNode(nodesOutSubTour, nodesInSubTour, selectNode);
			
		}

		Node currentNode = listNodes.get(0);
		int inc = 0;
		solution.add(Integer.parseInt(currentNode.getName()));
		while (currentNode.getSuccNode() != null && inc < listNodes.size() - 1) {
			currentNode = currentNode.getSuccNode();
			solution.add(Integer.parseInt(currentNode.getName()));
			inc += 1;
		}
		
		
		System.out.println("=========SolutionTSP : Insert Heuristic =========");
		for (int i = 0; i < solution.size() - 1; i++) {
			System.out.print(solution.get(i) + "-");
		}
		
		System.out.print(solution.get(solution.size() - 1));
		
						
		double value = 0.0;
		
		for (Arc arc : listArcs) {
			value += arc.getWeight();
		}
		
		System.out.println();
		System.out.println("Valeur du TSP : " + value);
		System.out.println();

		
		return value;
	}
	
	public Boolean changeListNode(ArrayList<Integer> input, ArrayList<Integer> output, int node) {
		
		Boolean result = false;
		int position = 0;
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i) == node) {
				position = i;
				result = true;
			}
		}
		
		if (result) {
			input.remove(position);
			output.add(node);
		}		
		
		return result;
	}
	

}