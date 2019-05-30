package Components;

import DataTypes.NodeID;

public class RouteUpdatePacket extends Packet{

	public RouteUpdatePacket(int id, int type, NodeID sourceNode, NodeID destinationNode, int size,
			double generationTime) {
		super(id, type, sourceNode, destinationNode, size, generationTime);
		// TODO Auto-generated constructor stub
	}

}
