package Components;

import DataTypes.Domain;
import DataTypes.NodeID;
import DataTypes.Route;

public class RouteInfoPacket extends Packet{

	Route route;
	public RouteInfoPacket(int id, int type, NodeID sourceNode, NodeID destinationNode, int size,
			double generationTime, Route route, Domain domain) {
		super(id, type, sourceNode, destinationNode, size, generationTime, domain);
		// TODO Auto-generated constructor stub
		this.setRoute(route);
	}


}
