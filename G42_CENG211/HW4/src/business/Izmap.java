package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Izmap {

	private HashMap<Node, List<Node>> map;

	public Izmap(HashMap<Node, List<Node>> map) {
		setMap(map);
	}

	public HashMap<Node, List<Node>> getHashMap() {
		return map;
	}

	private void setMap(HashMap<Node, List<Node>> map) {
		this.map = map;
	}
	
	public void removeNode(Node node) {
		List<Node> neighbors = map.get(node);
		for (Node node1 : neighbors) {
			int index = map.get(node1).indexOf(node);
			map.get(node1).remove(index);
		}
		map.remove(node);
	}
	public int generateUniqueNodeId() {
		boolean done = false;
		int id = 1;
		while(!done) {
			if(isNodeExist(id)) {
				id++;
			}else {
				done = true;
			}
		}
		return id;
	}
	
	public boolean isNodeExist(int nodeId) {
		Set<Node> nodeSet = map.keySet();
		Set<Integer> idSet = new HashSet<>();
		for (Node node: nodeSet) {
			idSet.add(node.getId());
		}
		for (int id: idSet) {
			if(id == nodeId) {
				return true;
			}
		}
		return false;
	}
	
	public List<Node> getNeighbors(Node node) {
		if(map.get(node) == null) {
			return new ArrayList<Node>();
		}
		return map.get(node);
	}
	
	public double getShortestDistance(Node node1,Node node2) {

		if(node1.equals(node2)) {
			return 0;
		}else if (isNeighbor(node1, node2)) {
			return node1.getNeighborDistance(node2);
		}else {
			if(isReachable(node1, node2)) {
				List<List<Node>> allpaths = getAllPaths(node1, node2);
				double shortest = getDistance(allpaths.get(0));
				for (int i = 0; i < allpaths.size(); i++) {
					double distance = getDistance(allpaths.get(i));
					if(distance < shortest) {
						shortest = distance;
					}
				}
				return shortest;
			}
			else {
				return -1;
			}
		}

		
		
	}
	private double getDistance(List<Node> path) {
		double distance = 0;
		for (int i = 0; i < path.size()-1; i++) {
			distance += path.get(i).getNeighborDistance(path.get(i+1));
		}
		return distance;
	}
	public List<List<Node>> getAllPaths(Node node1,Node node2) {
		List<List<Integer>> paths = new ArrayList<>();
		int v = map.size();
        boolean[] isVisited = new boolean[v*2];
        
        ArrayList<Integer> pathList = new ArrayList<>(); 

        pathList.add(node1.getId()); 

        paths = getAllPathsRecursive(node1, node2, isVisited, pathList, paths);
        List<List<Node>> pathsByNodes = new ArrayList<>(paths.size());
        for (int i = 0; i < paths.size(); i++) {
        	List<Node> pathByNode = new ArrayList<>();
			List<Integer> path = paths.get(i);
			for (int j = 0; j < path.size(); j++) {
				pathByNode.add(getNodeById(path.get(j)));
			}
			pathsByNodes.add(pathByNode);
		}
        return pathsByNodes;
	}
	public Node getNodeById(int id) {
		if(isNodeExist(id)) {
			Set<Node> keys = map.keySet();
			for (Node node : keys) {
				if(node.getId() == id) {
					return node;
				}
			}
		}else {
			throw new IllegalArgumentException("There is no location exist with given id");
		}
		return null;

	}
	private List<List<Integer>> getAllPathsRecursive(Node node1,Node node2, boolean[] isVisited, List<Integer> localPathList,List<List<Integer>> paths) { 
		isVisited[node1.getId()] = true; 
		if (node1.equals(node2)) {
			ArrayList<Integer> copy = new ArrayList<Integer>();
			for (Integer integer : localPathList) {
				copy.add(integer);
			}
			paths.add(copy);
			isVisited[node1.getId()]= false; 
			return paths; 
		} 
		
		for (Node node : map.get(node1)) { 
			Integer i = node.getId();
			if (!isVisited[i]) { 
				localPathList.add(i); 
				getAllPathsRecursive(node, node2, isVisited, localPathList, paths);
				localPathList.remove(i); 
			} 
		} 
		isVisited[node1.getId()] = false;
		return paths; 
	}
	
	public boolean isNeighbor(Node node1,Node node2) {
		return getHashMap().get(node1).contains(node2);
	}
	
	public boolean isReachable(Node node1,Node node2) {
		return getReachable(node1).contains(node2);
	}
	public List<Node> getReachable(Node node1) {
		Set<Node> mapKey = map.keySet();
		List<Node> unlooked = new ArrayList<>();
		for (Node node : mapKey) {
			unlooked.add(node);
		}
		Set<Node> reached = new HashSet<>();
		List<Node> neighbors = getNeighbors(node1);
		unlooked.remove(node1);
		for (Node node : neighbors) {
			reached.add(node);
		}
		for (Node node : neighbors) {
			if (!(node.equals(node1))) {
				if(!unlooked.isEmpty())
					reached.addAll(getPriReachable(node,unlooked));
			}
		}
		return new ArrayList<Node>(reached);
	}
	
	public List<Node> getPriReachable(Node node1,List<Node> unlooked) {
		Set<Node> reached = new HashSet<>();
		List<Node> neighbors = getNeighbors(node1);
		if(unlooked.remove(node1)) {
			for (Node node : neighbors) {
				reached.add(node);
			}
			for (Node node : neighbors) {
				if (!(node.equals(node1))) {
					if(!unlooked.isEmpty())
						reached.addAll(getPriReachable(node,unlooked));
				}
			}
		}
		return new ArrayList<Node>(reached);
	}

	public void addNode(Node newNode, List<Integer> neigborNodeIds) {
		List<Node> neighbors = new ArrayList<>();
		for (int nodeId: neigborNodeIds) {
			if(isNodeExist(nodeId)) {
				neighbors.add(getNodeById(nodeId));
			}
		}
		map.put(newNode, neighbors);
		for (Node node : neighbors) {
			map.get(node).add(newNode);
		}
	}
}
