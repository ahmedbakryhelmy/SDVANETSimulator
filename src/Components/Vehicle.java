package Components;
import java.util.ArrayList;
import java.util.Queue;

import DataTypes.Domain;
import DataTypes.DomainVehicleTuple;
import DataTypes.NodeID;
import DataTypes.Route;
import umontreal.iro.lecuyer.simevents.Event;
import umontreal.iro.lecuyer.simevents.Sim;

public class Vehicle{

private NodeID id;
private int type;
private NodeID controllerID;
private Domain domain;
private ArrayList <Route> routingTable;

private Queue<Packet> dataPackets;

private Queue<Packet> routingPackets;

private ArrayList<DomainVehicleTuple> reachableDomains;


public Route isrouteAvailable(NodeID dest) {
	
	// check if route is available locally
	
	for(int i =0; i<this.routingTable.size(); i++) {
	
		Route r = this.routingTable.get(i);
		if(r.getNodeID() == dest) {
			
			return r;
		}
	}
	return null;
}

public void updateRoutingTable(Route route) {
	
	for(int i =0; i<this.routingTable.size(); i++) {	
		Route r = this.routingTable.get(i);
		if(r.getNodeID() == route.getNodeID()) {
			this.routingTable.remove(i);
			break;
		}
	}
	
	this.routingTable.add(route);
	
}


class PacketArrival extends Event {
	
   public void actions() {

      }
   }

 class sendRequestPath extends Event {
	
	 sendRequestPath() {
		
	}
    public void actions() {

       }
    }
 

 class sendDataPacket extends Event {
	
	 sendDataPacket() {
		
	}
    public void actions() {

       }
    }
 

 class sendRouteUpdate extends Event {
	
	 sendRouteUpdate() {
		
	}
    public void actions() {

       }
    }

 class SendRouteInfo extends Event {
	
	 SendRouteInfo() {
		
	}
    public void actions() {

       }
    }

 class receiveRequestpath extends Event {
	
	 receiveRequestpath() {
		
	}
    public void actions() {

       }
    }

 class receiveDataPacket extends Event {
	
	 receiveDataPacket() {
		
	}
    public void actions() {

       }
    }

 class receiveRouteUpdate extends Event {
	
	 receiveRouteUpdate() {
		
	}
    public void actions() {

       }
    }

 class receiveRouteInfo extends Event {
		
	 receiveRouteInfo() {
		
	}
    public void actions() {

       }
    }

 class selectGateways extends Event {
		
	 selectGateways() {
		
	}
    public void actions() {

    	
       }
    }

 

public NodeID getId() {
	return id;
}

public void setId(NodeID id) {
	this.id = id;
}


public int getType() {
	return type;
}

public void setType(int type) {
	this.type = type;
}


public Domain getDomain() {
	return domain;
}

public void setDomain(Domain domain) {
	this.domain = domain;
}


public NodeID getControllerID() {
	return controllerID;
}

public void setControllerID(NodeID controllerID) {
	this.controllerID = controllerID;
}


public Queue<Packet> getDataPackets() {
	return dataPackets;
}

public void setDataPackets(Queue<Packet> dataPackets) {
	this.dataPackets = dataPackets;
}


public Queue<Packet> getRoutingPackets() {
	return routingPackets;
}

public void setRoutingPackets(Queue<Packet> routingPackets) {
	this.routingPackets = routingPackets;
}


public ArrayList<DomainVehicleTuple> getReachableDomains() {
	return reachableDomains;
}

public void setReachableDomains(ArrayList<DomainVehicleTuple> reachableDomains) {
	this.reachableDomains = reachableDomains;
}



}
