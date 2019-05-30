package DataTypes;
import java.util.ArrayList;

public class Route {

private NodeID nodeID;
private ArrayList <NodeID> route;


public NodeID getNodeID() {
	return nodeID;
}
public void setNodeID(NodeID nodeID) {
	this.nodeID = nodeID;
}
public ArrayList <NodeID> getRoute() {
	return route;
}
public void setRoute(ArrayList <NodeID> route) {
	this.route = route;
}
}
