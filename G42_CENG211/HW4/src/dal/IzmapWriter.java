package dal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import business.BuildingNode;
import business.CategoryType;
import business.Node;

public class IzmapWriter {

	private String fileName;
	
	public IzmapWriter(String fileName) {
		setFileName(fileName);
	}
	
	public void write(HashMap<Node, List<Node>> izmap) {
		PrintWriter fileOut = null;
		try {
			fileOut = new PrintWriter(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Node node: izmap.keySet()) {
			
			CategoryType categoryType = node.getType();
			String type;
			switch(categoryType) {
			case DEPARTMENT :
				type = "Department" ;
				break;
			case CAFETERIA:
				type = "Cafeteria";
				break;
			case ADMINISTRATIVE:
				type = "Administrative";
				break;
			case FACILITIES:
				type = "Facility";
				break;
			case WATERFALL:
				type = "Waterfall";
				break;
			case BEACH:
				type = "Beach";
				break;
			case HISTORICAL_RUIN:
				type = "Historical Ruin";
				break;
			default:
				type = null;
				break;
			}
			if(node.getClass() == BuildingNode.class) {
				String line = node.getId() + " [Building, " + type + ", " + node.getName() + "]";
				fileOut.println(line);
			}else {
				String line = node.getId() + " [Landscape, " + type + ", " + node.getName() + "]";
				fileOut.println(line);
			}
		}
		fileOut.println();
		
		Set<Set<Integer>> bidirectionalRelations = new HashSet<>();
		for(Node node: izmap.keySet()) {
			int nodeId = node.getId();
			for(Node neighborNode: izmap.get(node)) {
				Set<Integer> pair = new HashSet<>();
				pair.add(nodeId);
				pair.add(neighborNode.getId());
				bidirectionalRelations.add(pair);
			}		
		}
		
		for(Set<Integer> pair: bidirectionalRelations) {
			Integer[] pairArr = pair.toArray(new Integer[2]); 
			fileOut.println(pairArr[0] + " <--> " + pairArr[1]);
		}
		
		fileOut.close();
	}

	private void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
