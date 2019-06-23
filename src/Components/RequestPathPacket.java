package Components;

import DataTypes.Domain;
import DataTypes.NodeID;

public class RequestPathPacket extends Packet{

	public RequestPathPacket(int id, int type, NodeID sourceNode, NodeID destinationNode, int size,
			double generationTime, Domain domain) {
		super(id, type, sourceNode, destinationNode, size, generationTime, domain);
		// TODO Auto-generated constructor stub
	}

}
