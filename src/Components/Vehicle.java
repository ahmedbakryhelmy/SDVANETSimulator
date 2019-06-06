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

NodeID id;
int type;
NodeID controllerID;
Domain domain;
ArrayList <Route> routingTable;
Queue<Packet> dataPackets;
Queue<Packet> routingPackets;
ArrayList<DomainVehicleTuple> reachableDomains;
int CW;
final int CWmax = 16;
final double slotDuration = 0.02; // 20 micro second
final double SIFS = 0.01;
final double AIFS = 0.02;
final double DIFS = 0.05;
final double txDataFrame = 1;
final double txAck = 0.25;
final double propagationDelay = 0;
final double txRoutePacket = 0.4;
final double interferenceRange = 3;


public boolean canSend() {
	
	
	for(int i = 0; i<Scenario.sendingNodes.size();i++) {
		NodeID n = Scenario.sendingNodes.get(i);
		if (Math.abs(this.id.x -n.x) == interferenceRange || Math.abs(this.id.y -n.y) == interferenceRange) {
			return false;
		}
	}
	
	return true;
}

// removing the vehicle from the list of sending nodes when transmission is over
class StopSending extends Event {
	
	NodeID nodeId;
	public StopSending(NodeID nodeId)
	{
		super();
		this.nodeId=nodeId;
		
	}
	   public void actions() {
		   
		   for (int i = 0; i<Scenario.sendingNodes.size();i++) {
			   if(Scenario.sendingNodes.get(i).equals(nodeId)) {
				   Scenario.sendingNodes.remove(i);
				   return;
			   }
		   }
	      }
	   }

/*
// return a route specified 
public Route getRoute(NodeID nodeId) {
	
	for(int i = 0; i< routingTable.size();i++) {
		if(routingTable.get(i).getNodeID().equals(nodeId)) {
			return routingTable.get(i);
		}
	}
	return null;
	
	
}
*/

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


class sendRequestPathToGateways extends Event {
	 ArrayList <Vehicle> destinations;
	 RequestPathPacket requestPathPacket;
	 sendRequestPathToGateways(RequestPathPacket requestPathPacket, ArrayList<Vehicle> destinations) {
		 super();
		 this.requestPathPacket = requestPathPacket;
		 this.destinations= destinations;
	}
	 
	   public void actions() {
		   
			 for(int i=0;i<destinations.size();i++) {
				 Vehicle v  = destinations.get(i);
				 v.receiveRequestpath(requestPathPacket);
			 }

	      }
	   }

 class sendRequestPath extends Event {
	
	 int x =0;
	 RequestPathPacket requestPathPacket;
	 ArrayList <Vehicle> destinations;
	 
	 sendRequestPath(RequestPathPacket requestPathPacket, ArrayList<Vehicle> destinations) {
		 super();
		 this.requestPathPacket = requestPathPacket;
		 this.destinations= destinations;
	}
	 
	 public void backOff() {
		 int slots = (int)(Math.random()*CW);
		 x =0;
		 this.schedule(slots*slotDuration);
	 }
	 	 
	 public void sendPacket() {
		 
		 // send requestPathPacket to the destination nodes
		 // add node to the list of sending nodes
		 // schedule stopSending after the transmission is over
		 
		 Scenario.sendingNodes.add(id);
		 new StopSending(id).schedule(txRoutePacket);
		 new sendRequestPathToGateways(requestPathPacket, destinations).schedule(txRoutePacket);
		 
	 }
	 
	 // the MAC algorithm is implemented here
    public void actions() {
    	
    	if(x == 0) {
    		if(canSend() == true) {
    			x = 1;
    			// wait for aifs
    			this.schedule(AIFS);
    			
    		}else {

    			backOff();
    			
    		}
    		
    	}else {
    		x = 0;
    		if(canSend() == true) {
    			
    			// node is allowed to send
    			sendPacket();
    			
    		}else {
    			backOff();
    		}
    		
    		
    	}

       }
    }
 

 class sendDataPacket extends Event {
	
	 int x = 0;
	 sendDataPacket() {
		
	}
	 
	 
	 public void backOff() {
		 int slots = (int)(Math.random()*CW);
		 x =0;
		 this.schedule(slots*slotDuration);
	 }
	 	 
    public void actions() {
    	
    	if(x == 0) {
    		if(canSend() == true) {
    			x = 1;
    			// wait for aifs
    			this.schedule(DIFS);
    			
    		}else {

    			backOff();
    			
    		}
    		
    	}else {
    		x = 0;
    		if(canSend() == true) {
    			
    			// node is allowed to send
    			
    			
    		}else {
    			backOff();
    		}
    		
    		
    	}
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

 public void receiveRequestpath(RequestPathPacket requestPathPacket) {
	 
	 
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

 /*
 class EndOfSim extends Event {
     public void actions() {
        Sim.stop();
     }
  }
 
 public void simulate (double timeHorizon) {
     Sim.init();
     new EndOfSim().schedule (timeHorizon);
     new sendRequestPath().schedule(5);
     Sim.start(); 
 }
public static void main (String [] args) {
	Vehicle x = new Vehicle();
	x.simulate(20);
}
*/
 

}
