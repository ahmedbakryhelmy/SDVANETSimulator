package Components;

import DataTypes.NodeID;
import DataTypes.Route;

public class RouteInfoPacket extends Packet{

	Route route;
	public RouteInfoPacket(int id, int type, NodeID sourceNode, NodeID destinationNode, int size,
			double generationTime, Route route) {
		super(id, type, sourceNode, destinationNode, size, generationTime);
		// TODO Auto-generated constructor stub
		this.route=route;
	}

}
