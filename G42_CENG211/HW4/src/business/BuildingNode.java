package business;

public class BuildingNode extends Node {

	private final int DEFAULT_VALUE = 6;
	private CategoryType type;

	public BuildingNode(int nodeId, String name, CategoryType type) {
		super(nodeId, name);
		setType(type);
	}
	
	public CategoryType getType() {
		return type;
	}
	
	private void setType(CategoryType type) {
		this.type = type;
	}
	
	@Override
	public double getNeighborDistance(Node other) {
		switch (getType()) {
		case ADMINISTRATIVE:
			return getAdministrativeDistance(other);
			
		case CAFETERIA:
			return getCafeteriaDistance(other);
			
		case DEPARTMENT:
			return getDepartmentDistance(other);
			
		case FACILITIES:
			return getFacilitiesDistance(other);
			
		default:
			return DEFAULT_VALUE;
		}
	}
	private double getAdministrativeDistance(Node other) {
		if(other.getClass() ==BuildingNode.class) {
			BuildingNode node = (BuildingNode)other;
			switch (node.getType()) {
			case DEPARTMENT:
				return DEFAULT_VALUE/2;
			default:
				return DEFAULT_VALUE;
			}
		}else {
			return DEFAULT_VALUE;
		}
	}
	private double getCafeteriaDistance(Node other) {
		if(other.getClass() ==BuildingNode.class) {
			BuildingNode node = (BuildingNode)other;
			switch (node.getType()) {
			case DEPARTMENT:
				return (DEFAULT_VALUE*2)-3;
			case FACILITIES:
				return Math.abs(Math.sqrt(DEFAULT_VALUE));
			default:
				return DEFAULT_VALUE;
			}
		}else {
			LandscapeNode node = (LandscapeNode)other;
			switch (node.getType()) {
			case WATERFALL:
				return DEFAULT_VALUE/3;
			case HISTORICAL_RUIN:
				return DEFAULT_VALUE*DEFAULT_VALUE;
			default:
				return DEFAULT_VALUE;
			}
		}
	}
	private double getDepartmentDistance(Node other) {
		if(other.getClass() ==BuildingNode.class) {
			BuildingNode node = (BuildingNode)other;
			switch (node.getType()) {
			case CAFETERIA:
				return (DEFAULT_VALUE*2)-3;
			case ADMINISTRATIVE:
				return DEFAULT_VALUE/2;
			default:
				return DEFAULT_VALUE;
			}
		}else {
			LandscapeNode node = (LandscapeNode)other;
			switch (node.getType()) {
			case BEACH:
				return ((DEFAULT_VALUE*DEFAULT_VALUE)/2)+4;
			default:
				return DEFAULT_VALUE;
			}
		}
	}
	private double getFacilitiesDistance(Node other) {
		if(other.getClass() ==BuildingNode.class) {
			BuildingNode node = (BuildingNode)other;
			switch (node.getType()) {
			case CAFETERIA:
				return Math.abs(Math.sqrt(DEFAULT_VALUE));
			default:
				return DEFAULT_VALUE;
			}
		}else {
			LandscapeNode node = (LandscapeNode)other;
			switch (node.getType()) {
			case WATERFALL:
				return (DEFAULT_VALUE*5)/2;
			default:
				return DEFAULT_VALUE;
			}
		}
	}

	@Override
	public String toString() {
		return "Building, type=" + type + ", " + super.toString();
	}
	

	

}
