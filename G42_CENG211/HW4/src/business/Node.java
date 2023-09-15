package business;

public abstract class Node {

	private int id;
	private String name;
	
	
	public Node(int nodeId, String name) {
		
		setId(nodeId);
		setName(name);
	}
	
	public abstract double getNeighborDistance(Node other);

	public String getName() {
		return name;
	}
	public CategoryType getType() {
		return null;
	}

	
	private void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		Node other = (Node) obj;
		return ((getId() == other.getId()) && (getName().equals(other.getName())));
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + name ;
	}


}
