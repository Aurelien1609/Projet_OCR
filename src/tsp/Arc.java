package tsp;

public class Arc implements Comparable<Arc> {
	private Node source;
	private Node target;
	private double weight;
	private int sourceNode;
	private int targetNode;

	public Arc(Node source, Node target, double weight) {
		this.source = source;
		this.target = target;
		this.weight = weight;
	}
	
	public Arc(int source, int target, double weight) {
		this.sourceNode = source;
		this.targetNode = target;
		this.weight = weight;
	}

	public int getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(int sourceNode) {
		this.sourceNode = sourceNode;
	}

	public int getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(int targetNode) {
		this.targetNode = targetNode;
	}

	public Node getSource() {
		return source;
	}

	public Node getTarget() {
		return target;
	}
	
	public double getWeight() {
		return weight;
	}

	public String toString() {
		return "(" + source.getName() + "," + target.getName() + ")";
	}

	@Override
	public int compareTo(Arc otherArc) {
		
		double res = (this.getWeight() - otherArc.getWeight());
		
		if(res > 0.0) return 1;
		if(res < 0.0) return -1;
		return 0;
	}

}