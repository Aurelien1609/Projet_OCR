package tsp;

public class Node {
	
	public String name;
	public Node predNode;
	public Node succNode;
	public int Id;
	
	public Node(int name) {
		this.Id = name;
		this.name = Integer.toString(name);
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Node getPredNode() {
		return predNode;
	}

	public void setPredNode(Node predNode) {
		this.predNode = predNode;
	}

	public Node getSuccNode() {
		return succNode;
	}

	public void setSuccNode(Node succNode) {
		this.succNode = succNode;
	}
	
	

}
