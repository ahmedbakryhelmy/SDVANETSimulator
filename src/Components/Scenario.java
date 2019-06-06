package Components;

import java.util.ArrayList;

import DataTypes.NodeID;

public class Scenario {
	
static	ArrayList<NodeID> sendingNodes = new ArrayList<NodeID> ();
static ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

public Vehicle getVehicle(NodeID nodeId) {
	
	Vehicle v;
	
	for (int i = 0; i< vehicles.size(); i++) {
		v = vehicles.get(i);
		if(v.id.x == nodeId.x && v.id.y == nodeId.y) {
			return v;
		}
	}
	return null;
}


}
