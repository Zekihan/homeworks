package business;

import java.util.Comparator;
import java.util.List;

import dal.ConsoleInput;
import dal.IzmapReader;
import dal.IzmapWriter;
import view.IzmapManagerView;

public class IzmapManager {
	
	private Izmap map;
	private IzmapManagerView view;
	private ConsoleInput consoleIn;

	public IzmapManager() {
		setMap(new Izmap(new IzmapReader("iztech.izmap").read()));
		setView(new IzmapManagerView());
		setConsoleIn(new ConsoleInput());
	}
	
	public void start() {
		view.startMessage();
		boolean done = false;
		while (!done) {
		    try {
		        view.menu();
				int a = consoleIn.readInt();
				switch(a){
				case 1: 
					view.menuCase1();
					getShortestPath(map.getNodeById(consoleIn.readInt()), map.getNodeById(consoleIn.readInt()));
					break;
				case 2: 
					view.menuCase2();
					int b = consoleIn.readInt();
					switch (b) {
					case 1:
						view.menuCase2Case1();
						addNode();
						break;
					case 2:
						view.menuCase2Case2();
						removeNode(map.getNodeById(consoleIn.readInt()));
						break;
					default:
						break;
					}
					break;
				case 3:
					view.menuCase3();
					getPossibleReachableLocationsFromGivenLocationDistance(map.getNodeById(consoleIn.readInt()), consoleIn.readDouble());
					break;
				case 4:
					view.menuCase4();
					getNeighbors(map.getNodeById(consoleIn.readInt()));
					
					break;
				case 5: 
					view.menuCase5();
					consoleIn.closeKeyboard();
					System.exit(0);
					break;
				default:
					view.menuDefaultCase();
					break;
				}
		        

		    } catch (IllegalArgumentException e) {
		    	view.illegalArgumentExcepitonMessage();
		    } catch (Exception e) {
		    	view.exceptionMessage();
		    }
		
			
		}
	}
	
	private void addNode() {
		List<String> inputList = consoleIn.readLineOfString();
		String nodeName = inputList.get(0);
		String category = inputList.get(1);
		String categoryType = inputList.get(2);
		int nodeId = map.generateUniqueNodeId();
		Node newNode = null;
		CategoryType type = null;
		if(category.equals("Building")) {
			switch(categoryType) {
			case "Department":
				type = CategoryType.DEPARTMENT;
				break;
			case "Cafeteria":
				type = CategoryType.CAFETERIA;
				break;
			case "Administrative":
				type = CategoryType.ADMINISTRATIVE;
				break;
			case "Facility":
				type = CategoryType.FACILITIES;
				break;
			default:
				break;
			}
			newNode = new BuildingNode(nodeId, nodeName, type);
		}else {
			switch(categoryType) {
			case "Waterfall":
				type = CategoryType.WATERFALL;
				break;
			case "Beach":
				type = CategoryType.BEACH;
				break;
			case "Historical Ruin":
				type = CategoryType.HISTORICAL_RUIN;
				break;
			default:
				break;
			}
			newNode = new LandscapeNode(nodeId, nodeName, type);
		}
		
		System.out.println("Enter neighbour nodes id in format \"1,2,3\" (nodes that do exist will be ignored)");
		List<Integer> idList = consoleIn.readLineOfInteger();
		map.addNode(newNode, idList);
		IzmapWriter writer = new IzmapWriter("iztech.izmap");
		writer.write(map.getHashMap());
		System.out.println("Successfully added node with id: " + nodeId);
	}
	
	private void removeNode(Node node) {
		map.removeNode(node);
		IzmapWriter writer = new IzmapWriter("iztech.izmap");
		writer.write(map.getHashMap());
	}
	
	private void getShortestPath(Node node1,Node node2) {
		System.out.printf("%.2f",map.getShortestDistance(node1, node2));
		System.out.println();
	}
	
	private void getPossibleReachableLocationsFromGivenLocationDistance(Node node,double distance) {
		List<Node> reachable = map.getReachable(node);
		for (Node node1 : reachable) {
			double calcDistance = map.getShortestDistance(node, node1);
			if(calcDistance > 0) {
				if(calcDistance<=distance) {
					System.out.print(node1+", Distance : ");
					System.out.printf("%.2f",calcDistance);
					System.out.println();
				}
			}
		}
	}
	
	private void getNeighbors(Node node) {
		List<Node> neighbors = map.getNeighbors(node);
		neighbors.sort(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		for (Node node1 : neighbors) {
			System.out.println(node1);
		}
	}

	private void setMap(Izmap map) {
		this.map = map;
	}

	private void setView(IzmapManagerView view) {
		this.view = view;
	}

	private void setConsoleIn(ConsoleInput consoleIn) {
		this.consoleIn = consoleIn;
	}
}
