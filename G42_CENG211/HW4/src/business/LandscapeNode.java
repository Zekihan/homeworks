package business;

public class LandscapeNode extends Node{
	
	private final int DEFAULT_VALUE = 6;
	private CategoryType type;

	public LandscapeNode(int nodeId, String name, CategoryType type) {
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
		case WATERFALL:
			return getWaterfallDistance(other);
			
		case BEACH:
			return getBeachDistance(other);
			
		case HISTORICAL_RUIN:
			return getHistoricalRuinDistance(other);
			
		default:
			return DEFAULT_VALUE;
		}
	}
	private double getWaterfallDistance(Node other) {
		if(other.getClass() ==BuildingNode.class) {
			BuildingNode node = (BuildingNode)other;
			switch (node.getType()) {
			case CAFETERIA:
				return DEFAULT_VALUE/3;
			case FACILITIES:
				return (DEFAULT_VALUE*5)/2;
			default:
				return DEFAULT_VALUE;
			}
		}else {
			return DEFAULT_VALUE;
		}
	}
	private double getBeachDistance(Node other) {
		if(other.getClass() ==BuildingNode.class) {
			BuildingNode node = (BuildingNode)other;
			switch (node.getType()) {
			case DEPARTMENT:
				return ((DEFAULT_VALUE*DEFAULT_VALUE)/2)+4;
			default:
				return DEFAULT_VALUE;
			}
		}else {
			return DEFAULT_VALUE;
		}
	}
	private double getHistoricalRuinDistance(Node other) {
		if(other.getClass() ==BuildingNode.class) {
			BuildingNode node = (BuildingNode)other;
			switch (node.getType()) {
			case CAFETERIA:
				return DEFAULT_VALUE*DEFAULT_VALUE;
			default:
				return DEFAULT_VALUE;
			}
		}else {
			return DEFAULT_VALUE;
		}
	}

	@Override
	public String toString() {
		return "Landscape, type=" + type + ", " + super.toString();
	}
	
	

	
	
	
}
