package Components;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister.Pack;
import DataTypes.Domain;
import DataTypes.DomainVehicleTuple;
import DataTypes.NodeID;
import DataTypes.Route;
import umontreal.iro.lecuyer.simevents.Event;
import umontreal.iro.lecuyer.simevents.Sim;

public class Vehicle{

NodeID id;
NodeID controllerID;
Domain domain;
ArrayList <Route> routingTable = new ArrayList<Route>();
Queue<Packet> dataPackets = new ArrayDeque<Packet>();
Queue<Packet> routingPackets;
DataPacket currentDataPacket;
ArrayList<Vehicle> neigbouringCars = new ArrayList<Vehicle>();
ArrayList<Domain> reachableDomains;
int CW = 4;
final int CWmax = 16;
final double slotDuration = 0.02; // 0.02 ms
final double SIFS = 0.00001; 
final double AIFS = 0.00002;
final double DIFS = 0.00005;
final double txDataPacket = 0.4; // 400 ms
final double txAck = 0.00025; // 250 ms
final double propagationDelay = 0;
final double txRoutePacket = 0.05; //50 msec
final double interferenceRange = 3;

public Vehicle(NodeID id, NodeID controllerID) {
	
	this.id = id;
	this.controllerID= controllerID;
	
}

public Vehicle(NodeID id, NodeID controllerID, Domain domain, ArrayList<Domain> reachableDomains) {
	
	this.id = id;
	this.controllerID= controllerID;
	this.domain=domain;
	this.reachableDomains = reachableDomains;

	
}


// check if medium is free to send
public boolean canSend() {
	
	
	for(int i = 0; i<Scenario.sendingNodes.size();i++) {
		NodeID n = Scenario.sendingNodes.get(i);
		
		// this can also be modified by using square route sqrt(x^2 + y^2)
		
		
		if (Math.sqrt(Math.pow(this.id.x - n.x, 2)+Math.pow(this.id.y - n.y, 2)) <= interferenceRange) {
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
		   System.out.println("Node (" + nodeId.x + ", "+ nodeId.y + ") stopped sending");
		   
		   for (int i = 0; i<Scenario.sendingNodes.size();i++) {
			   if(Scenario.sendingNodes.get(i).equals(nodeId)) {
				   Scenario.sendingNodes.remove(Scenario.sendingNodes.get(i));
				   break;
			   }
		   }
		   
		   // new test2().schedule(0.001);
		   
	      }
	   
	   
	   }

public void testing(double t, DataPacket p) {
	//currentDataPacket = p;
	p.setGenerationTime(t);
	new sendDataPacket(p).schedule(t);
}
public void test3 () {
	new StopSending(id).schedule(0.8);
}
class test2 extends Event {
    public void actions() {
    	
      // System.out.println();
    }
 }


// compare two vehicles using their IDs
public boolean equals(Vehicle v) {
	if (id.equals(v.id)) {
		return true;
	}
	return false;
}



// check route availability for a certain destination
public Route isrouteAvailable(NodeID dest) {
	
	// check if route is available locally
	
	for(int i =0; i<this.routingTable.size(); i++) {
	
		Route r = this.routingTable.get(i);
		if(r.getNodeID().equals(dest)) {
			//System.out.println("Node (" + dest.x + ", "+ dest.y + ") route available for node "+ id.x + ", "+ id.y + ")");
			return r;
		}
	}
	System.out.println("Node (" + dest.x + ", "+ dest.y + ") route not available by (" + id.x + ", "+ id.y + ")");
	return null;
}

public void updateRoutingTable(Route route) {
	
	for(int i =0; i<this.routingTable.size(); i++) {
		Route r = this.routingTable.get(i);
		System.out.println(r);
		System.out.println(route);
		if(r.getNodeID().equals(route.getNodeID())) {
			this.routingTable.remove(r);
			System.out.println("Route found");
			break;
		}
	}
	
	System.out.println("updating routing table"+ "(" + id.x + ", "+ id.y + ")");
	this.routingTable.add(route);
	
}



class PacketArrival extends Event {
	
   public void actions() {
	   
	   // destination should be different
	   NodeID n = Scenario.randomDestination();
	   while(!n.equals(id)) {
		   n = Scenario.randomDestination();
	   }
	   DataPacket packet = new DataPacket(Scenario.counter++, 1, id, n, 1250 ,Sim.time());
	   if(currentDataPacket == null) {
		   currentDataPacket = packet;
		   //new sendDataPacket(packet).schedule();
		 //  System.out.println("Packet arrived");
		   
	   }else {
		   dataPackets.add(packet);
		   //System.out.println("Packet added to the queue");
		   
	   }
	   
	   
	   System.out.println("New packet arrived at Vehicle" + "(" + id.x + ", "+ id.y + ")");
      }
   }



class sendDataPacketToDestination extends Event {
	 DataPacket packet;
	 Route route;
	 sendDataPacketToDestination(DataPacket packet) {

		 this.packet = packet;
		 this.route = packet.getRoute();
		 currentDataPacket = packet;
	}
	 
	   public void actions() {
		   System.out.println("sendDataPacketToDestination" + "(" + id.x + ", "+ id.y + ")");
		   
		   // send packet to next hop 
		   Vehicle v = Scenario.getVehicle(route.getRoute().get(0));
		   //packet.setHops(packet.getHops()+1);
		   v.receiveDataPacket(packet);

	      }
	   }


// sending data packet to destination

class sendDataPacket extends Event {
	
	 int x = 0;
	 DataPacket packet;
	 sendDataPacket(DataPacket packet) {
		 this.packet=packet;
		 
		 
	}
	 
	 public void sendPacket() {
		 
		 // send requestPathPacket to the destination nodes
		 // add node to the list of sending nodes
		 // schedule stopSending after the transmission is over
		 
		 Scenario.sendingNodes.add(id);
		 new StopSending(id).schedule(txDataPacket);
		 packet.setRoute(isrouteAvailable(packet.getDestinationNode()));
		 new sendDataPacketToDestination(packet).schedule(txDataPacket);
		 currentDataPacket = null;
		 System.out.println("sendDataPacket/ sendPacket" + "(" + id.x + ", "+ id.y + ")");
		 CW = 4;
		 
	 }
	 public void backOff() {
		
		 int slots = (int)(Math.random()*CW);
		 x =0;
		 CW*=2;
		 System.out.println("Back off " + slots*slotDuration);
		 this.schedule(slots*slotDuration);
	 }
	 	 
   public void actions() {
   	
	   currentDataPacket = packet;
   	
   	if(x == 0 && isrouteAvailable(packet.getDestinationNode())==null) {
   		
   		// a controller
   		if(controllerID.equals(id)) {
   			
   			// gateway selection
   			// send request path to gateways
   			
   			RequestPathPacket requestPathPacket = new RequestPathPacket(packet.getId(), 2, id, packet.getDestinationNode(), 450, Sim.time());
   			new sendRequestPath(requestPathPacket, selectGateways()).schedule(0.0001);
   			System.out.println("sendDataPacket/ no route and is a controller" + "(" + id.x + ", "+ id.y + ")");
   			
   		}else {
   			
   			// send requestPath to controller
   			RequestPathPacket requestPathPacket = new RequestPathPacket(packet.getId(), 2, id, packet.getDestinationNode(), 450, Sim.time());
   			ArrayList<Vehicle> array = new ArrayList<Vehicle>();
   			Vehicle v = Scenario.getVehicle(controllerID);
   			array.add(v);
   			new sendRequestPath(requestPathPacket, array).schedule(0.0001);
   			System.out.println("sendDataPacket/ no route and not a controller" + "(" + id.x + ", "+ id.y + ")");
   		}
   		
   	}else {
   	
   	
   	if(x == 0) { // if route availabel locally
   		
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
   			// send data packet to destination
   			sendPacket();
   			
   			
   			
   		}else {
   			backOff();
   		}
   		
   		
   	}
   	
   	
   }
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
		   System.out.println("sendRequestPathToGateways" + "(" + destinations.get(0).id.x + ", "+ destinations.get(0).id.y + ")");
		   
			 for(int i=0;i<destinations.size();i++) {
				 Vehicle v  = destinations.get(i);
				 v.receiveRequestpath(requestPathPacket);
			 }

	      }
	   }



// sending request path packet
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
		 CW*=2;
		 x =0;
		 this.schedule(slots*slotDuration);
	 }
	 	 
	 public void sendPacket() {
		 
		 // send requestPathPacket to the destination nodes
		 // add node to the list of sending nodes
		 // schedule stopSending after the transmission is over
		 
		 Scenario.sendingNodes.add(id);
		 new StopSending(id).schedule(txRoutePacket);
		 new sendRequestPathToGateways(this.requestPathPacket, this.destinations).schedule(txRoutePacket);
		 System.out.println("sendRequestPath/ sendPacket" + "(" + id.x + ", "+ id.y + ")");
		 CW = 4;
		 
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
 

 // TODO
 // this should be called somewhere

 class sendRouteUpdate extends Event {
	
	 RequestPathPacket packet;
	 int x = 0;
	 sendRouteUpdate(RequestPathPacket packet) {
		 this.packet = packet;
		
	}
	 
	 
	 public void backOff() {
		 int slots = (int)(Math.random()*CW);
		 CW*=2;
		 x =0;
		 this.schedule(slots*slotDuration);
	 }
	 
 public void sendPacket() {
		 
	
		 Scenario.sendingNodes.add(id);
		 new StopSending(id).schedule(txRoutePacket);
		 packet.setRoute(isrouteAvailable(packet.getDestinationNode()));
		 packet.setType(2);
		 Vehicle v = Scenario.getVehicle(controllerID);
		 RouteUpdatePacket packet2 = new RouteUpdatePacket(packet.getId(), packet.getType(), packet.getSourceNode(), packet.getDestinationNode(), packet.getSize(), Sim.time(), packet.getRoute());
		 packet2.setRoute(packet.getRoute());
		 System.out.println("sendRouteUpdate/ sendPacket" + "(" + id.x + ", "+ id.y + ")"+ " route "+ packet2.getRoute());
		 v.receiveRouteUpdate(packet2);
		 
		 CW = 4;
	 }
 
 
 
	 
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
 
 
// TODO send a route info to the requested node (send an arbiterary route to test simuation)
 class SendRouteInfo extends Event {
	
	 RouteInfoPacket p;
	 SendRouteInfo(RouteInfoPacket p) {
		
		this.p=p;
	}
    public void actions() {
    	//System.out.println("testing    "+ isrouteAvailable(p.getDestinationNode()));
    	System.out.println("sendRouteInfo" + "(" + id.x + ", "+ id.y + ")" + p.getRoute());
    	RouteInfoPacket packet = new RouteInfoPacket(p.getId(), p.getType(), p.getSourceNode(), p.getDestinationNode(), p.getSize(), Sim.time(), isrouteAvailable(p.getDestinationNode()));
    	packet.setRoute(p.getRoute());
    	Vehicle destination = Scenario.getVehicle(p.getSourceNode());
    	destination.receiveRouteInfo(packet);
    	

       }
    }
 
 

 public void receiveRequestpath(RequestPathPacket requestPathPacket) {
	 
	 Scenario.totalRxRouteRequestPacket++;
	 
	 // process receive RequestPath
	 
	 if(isrouteAvailable(requestPathPacket.getDestinationNode())!=null) {
		 
		 if(this.id.equals(controllerID)) {
			 
			 System.out.print("receiveRequestpath....route available and node is the controller");
			 
			 // sends route info
			 new SendRouteInfo(new RouteInfoPacket(requestPathPacket.getId(), requestPathPacket.getType(), requestPathPacket.getSourceNode(), requestPathPacket.getDestinationNode(), requestPathPacket.getSize(), Sim.time(), isrouteAvailable(requestPathPacket.getDestinationNode()))).schedule(txRoutePacket);
			 
			 
		 }else {
			 
			 System.out.print("receiveRequestpath....route available and node is not the controller");
			 
			 // sends route update to controller 
			 new sendRouteUpdate(requestPathPacket).schedule(txRoutePacket);
			 
		 }
	 }else {
		 
		 if(this.id.equals(controllerID)) {
			 
			 System.out.print("receiveRequestpath....route not available and node is the controller");
			 
			 // gateways selection
			 // sends requestPath to set of gateways
			 ArrayList<Vehicle> gateways = selectGateways();
			 new sendRequestPath(requestPathPacket, gateways).schedule(txRoutePacket);
			 
			 
			 
		 }else {
			 
			 // message came from same domain
			 if(Scenario.getVehicle(requestPathPacket.getSourceNode()).domain.equals(domain)) {
				 System.out.print("receiveRequestpath....message came from the same domain");
				 
				 // gateways selection
				 // sends requestPath to set of gateways
				 ArrayList<Vehicle> gateways = selectGateways();
				 new sendRequestPath(requestPathPacket, gateways).schedule(0.02);
				 
			 }else {
				 
				 System.out.print("receiveRequestpath....message came from a different domain");
				 
				 // sends requestPath to controller
				 Vehicle v = Scenario.getVehicle(controllerID);
				 ArrayList<Vehicle> destinations = new ArrayList<Vehicle>();
				 destinations.add(v);
				 new sendRequestPath(requestPathPacket, destinations).schedule(0.02);
				 
				 
			 }
			 
			 
		 }
		 
	 }
	 
 }
 

 public void receiveDataPacket (DataPacket packet) {
	

	 // data packet received
	 // if node is the destination then update the packet details such as delay etc 
	 // update total bytes sent for throughput etc
	 
	 // if not then send packet to next destination
	 // update hop count 
	 
	 if(id.equals(packet.getDestinationNode())) {
		 // node is the destination node
		 Scenario.txDataBytes+= packet.getSize();
		 Scenario.totalRxPackets+=1;
		 Scenario.txDataPacketsDelay = Scenario.txDataPacketsDelay+ Math.abs(Sim.time()-packet.getGenerationTime());
		 //System.out.println(Scenario.txDataPacketsDelay);
		 System.out.println("receiveDataPacket/ is destination" + "(" + id.x + ", "+ id.y + ")" + "Sim time "+ Sim.time()+"   Packet Generation time" + packet.getGenerationTime());
		 
		 
	 }else {
		 
		 //node is an intermediate node
		// Route r = packet.getRoute();
		 //r.getRoute().remove(0);
		 //packet.setRoute(r);
		 // send data packet to node id r.get(0)
		 Route r = isrouteAvailable(packet.getDestinationNode());
		 if(r == null) {
			 
			 // route is supposed to be available locally
			 // if not we will just add it here as this is the responsibility of other implementations
			 r = packet.getRoute();
			 r.getRoute().remove(r.getRoute().get(0));
			 this.updateRoutingTable(r);
			 new sendDataPacket(packet).schedule(0);
			 System.out.println("Route not available for packet from ("+ packet.getSourceNode().x +", "+packet.getSourceNode().y+") to "+ packet.getDestinationNode().x +", "+packet.getDestinationNode().y+") ");
		 }else {
			 
			 new sendDataPacket(packet).schedule(0);
			 System.out.println("receiveDataPacket/ is not destination" + "(" + id.x + ", "+ id.y + ")");
			 
		 }
		 
		 
	 }
	 
    }
 
 
 public void receiveRouteUpdate(RouteUpdatePacket routeUpdatePacket) {
	 
	 // we should check if the node is the source node requested the route update
	 // we check if it's a controller and needs to update its routing table 
	 // should the controller send a route info packet to the source node requested the route
	 
	 // assumption: instead of creating the route using reverse way, we can just give a simple route to the destination that will just work for now
	 // for simplicity
	 
	 Scenario.totalRxRouteUpdatepackets++;
	 //System.out.println(" routeUpdatePacket.route = "+ routeUpdatePacket.route);
	 updateRoutingTable(routeUpdatePacket.route);
	 System.out.println("receiveRouteUpdate" + "(" + id.x + ", "+ id.y + ")");
	 //System.out.println("route here "+ routeUpdatePacket.getRoute());
	 RouteInfoPacket p = new RouteInfoPacket(routeUpdatePacket.getId(), routeUpdatePacket.getType(), routeUpdatePacket.getSourceNode(), routeUpdatePacket.getDestinationNode(), routeUpdatePacket.getSize(), Sim.time(), routeUpdatePacket.getRoute());
	 System.out.println("nnanana"+ p.getRoute());
	 p.setRoute(routeUpdatePacket.route);
	 new SendRouteInfo(p).schedule(txRoutePacket);
	// To be fixed
	 
 }
 


 
 public void receiveRouteInfo(RouteInfoPacket packet) {
	 
	 // we should send the packet correspondent to the requested path to its destination
	 // also add to the routing overhead
	 Scenario.totalRxRouteInfoPackets++;
	// System.out.println("receiveRouteInfo" + "(" + id.x + ", "+ id.y + ")");
	 if(this.id.equals(packet.getSourceNode())) {
		 System.out.println("receiveRouteInfo" + "(" + id.x + ", "+ id.y + ")"+"  packet.getRoute()  "+ packet.getRoute());
		 this.updateRoutingTable(packet.getRoute());
		 DataPacket p = new DataPacket(currentDataPacket.getId(), currentDataPacket.getType(), currentDataPacket.getSourceNode(), currentDataPacket.getDestinationNode(), currentDataPacket.getSize(), currentDataPacket.getGenerationTime());
		 new sendDataPacket(p).schedule(0);
		 currentDataPacket = null;
		 
	 }
	 
	 
	 
 }
 
 /*

 class receiveRouteInfo extends Event {
		
	 receiveRouteInfo() {
		
	}
    public void actions() {

       }
    }
 
*/
 public static void print(ArrayList<Vehicle> array) {
	 
	 for(int i = 0; i< array.size();i++) {
		 System.out.println("V "+ i + "(" + array.get(i).id.x + ", " + array.get(i).id.y+ ")" + "Reachable Domains:  "+ array.get(i).reachableDomains.size());
	 }
	 
 }

 public static ArrayList<Vehicle> sortNumberOfDomains(ArrayList<Vehicle> array){
    
	 Vehicle temp;
	 
	 for(int i =1; i<array.size();i++) {
		 
		 
		 for(int j =i; j>0;j--) {
			 
			 // sort in descending order
			 if(array.get(j).reachableDomains.size() >  array.get(j-1).reachableDomains.size()) {

				 // swap
				 temp = array.get(j);
				 array.set(j, array.get(j-1));
				 array.set(j-1, temp);
			 
			 }

		 }
	 }
	 
	 
	 
	 return array;
	 
 }
 
 
 // should be used when creating the tuples
 public ArrayList<Vehicle> selectGateways1() {
	 
	 ArrayList<Vehicle> result = new ArrayList<Vehicle>();
	 result.addAll(neigbouringCars);
	 
	 ArrayList<Domain> domains = new ArrayList<Domain>();
	 
	 for(int x = 0; x< result.size(); x++) {
		 
		 result = sortNumberOfDomains(result);
		 domains.addAll(result.get(x).reachableDomains);
		 System.out.println("Iteration = " + x);
		 
		  print(result);
		 
		  
		 for(int i = x+1; i< result.size();i++) {
			 
			 ArrayList<Domain> temp3 = new ArrayList<Domain>();
			 ArrayList<Domain> dom = result.get(i).reachableDomains;
			 for(int j = 0; j<dom.size();j++) {
				 
				 Domain d = dom.get(j);
				 boolean exist = false;
				 for(int k = 0; k<domains.size();k++) {
					 
					 if(domains.get(k).equals(d)){
						 exist = true;
						 break;
						 
					 }
					 
				 }
				 
				 if(exist==false) {
					 temp3.add(d);
				 }
				 
				 
			 }
			 
			 result.get(i).reachableDomains.removeAll(result.get(i).reachableDomains);
			 result.get(i).reachableDomains.addAll(temp3);

		 }
		 
		 
	 }
	 
	 ArrayList<Vehicle> output = new ArrayList<Vehicle>();
	 for(int i =0; i<result.size();i++) {
		 if(result.get(i).reachableDomains.size()>0) {
			 output.add(result.get(i));
		 }
	 }
	 
	 System.out.println("Gateway: "+output.get(0).id.x+ ", " + output.get(0).id.y + ")");
	 
	 return output;
	 
 }
 
 
	public static boolean find(ArrayList<Domain> domains, Domain d) {
		
		for(int i = 0; i< domains.size();i++) {
			if (domains.get(i).equals(d)) {
				return true;
			}
		}
		return false;
	}
 

 public static Vehicle findMinDistVehicle(ArrayList<Vehicle> array, Domain d){
    
	 
	 double minDistance = 0;
	 int indexMin = 0;
	 int i;
	 for(i = 0;i<array.size();i++) {
		if(find(array.get(i).reachableDomains,d)) {
		 minDistance = Math.sqrt(Math.pow(d.getController().id.x - array.get(i).id.x, 2)+Math.pow(d.getController().id.y - array.get(i).id.y, 2));
		 indexMin = i;
		 break;
		}
	 }

	 for(;i<array.size();i++) {
		 
		 if (find(array.get(i).reachableDomains,d)) {
			 double distance = Math.sqrt(Math.pow(d.getController().id.x - array.get(i).id.x, 2)+Math.pow(d.getController().id.y - array.get(i).id.y, 2));
			 
			 if (distance < minDistance) {
				 minDistance = distance;
				 indexMin = i;
			 }
		 }
		 
	 }
	 
	 
	 return array.get(indexMin);
	 
 }
 
 
 public ArrayList<Vehicle> selectGateways2(int filter){
	 
	 ArrayList<Vehicle> temp = new ArrayList<Vehicle>();
	 temp.addAll(neigbouringCars);
	 ArrayList<Vehicle> result = new ArrayList<Vehicle>();
	 
	 ArrayList<Domain> reachableDomains  = new ArrayList<Domain>();
	 for (int i = 0; i< temp.size();i++) {
		 for(int j = 0; j<temp.get(i).reachableDomains.size();j++) {
			 if(find(reachableDomains, temp.get(i).reachableDomains.get(j)) == false) {
				 reachableDomains.add(temp.get(i).reachableDomains.get(j));
			 }
			 
		 }
		 
		 
	 }
	 for(int i = 0; i< reachableDomains.size();i++) {
		 
		 result.add(findMinDistVehicle(temp, reachableDomains.get(i)));
		 
	 }
	 
	 ArrayList<Vehicle> output = new ArrayList<Vehicle>();
	 
	 if(filter == 1) {

		 output = result;
		 
	 }else {
		 
		 if(filter == 4) {
			 
			 for(int i = 0; i< result.size()/4;i++) {
				 output.add(result.get(i));
			 }
			 
			 
		 }else {
			 
			 // filter = 6
			 
			 for(int i = 0; i< result.size()/6;i++) {
				 output.add(result.get(i));
			 }
		 }
		 
		 
	 }
	 
	 
	 return output;
 }
 
 
 
 public ArrayList<Vehicle> selectGateways(){
	 
	 ArrayList<Vehicle> gateways;
	 
	 
	 if(Scenario.gatewaySelectionAlgorithm==1) {

		 gateways = selectGateways1();

	 }else {
		 
		 gateways = selectGateways2(Scenario.gatewaysFilter);
	 }
	 
	 return gateways;
	 
 }
 


 

}




