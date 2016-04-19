package tsp;

class Arc implements Comparable<Arc> {
	private Node source;
	private Node target;
	private double weight;

	public Arc(Node source, Node target, double weight) {
		this.source = source;
		this.target = target;
		this.weight = weight;
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