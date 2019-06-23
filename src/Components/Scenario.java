package Components;

import java.util.ArrayList;

import DataTypes.Domain;
import DataTypes.NodeID;
import DataTypes.Route;
import umontreal.iro.lecuyer.simevents.Event;
import umontreal.iro.lecuyer.simevents.Sim;

public class Scenario {
	
static	ArrayList<NodeID> sendingNodes = new ArrayList<NodeID> ();
static  ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
static int counter = 0;
static int gatewaySelectionAlgorithm = 1; // 1 for the new one and 2 for the old one
static int gatewaysFilter = 1;
static double txDataBytes = 0;
static double txDataPacketsDelay = 0;
static int totalRxPackets = 0;
static int totalRxRouteUpdatepackets = 0;
static int totalRxRouteRequestPacket = 0;
static int totalRxRouteInfoPackets = 0;

static String toBePrinted = "";

public static Vehicle getVehicle(NodeID nodeId) {
	
	Vehicle v;
	
	for (int i = 0; i< vehicles.size(); i++) {
		v = vehicles.get(i);
		if(v.id.x == nodeId.x && v.id.y == nodeId.y) {
			return v;
		}
	}
	return null;
}


public static NodeID randomDestination() {
	int v = (int)(Math.random()*vehicles.size());
	return vehicles.get(v).id;
}

class EndOfSim extends Event {
    public void actions() {
       Sim.stop();
       System.out.println("totalRxPackets ="+totalRxPackets);
       System.out.println("totalRxRouteInfoPackets =" + totalRxRouteInfoPackets);
       System.out.println("totalRxRouteRequestPacket =" + totalRxRouteRequestPacket);
       System.out.println("totalRxRouteUpdatepackets =" + totalRxRouteUpdatepackets);
       System.out.println("txDataPacketsDelay ="+ txDataPacketsDelay);
       System.out.println(toBePrinted);
    }
 }

public void simulate (double timeHorizon) {
    new EndOfSim().schedule (timeHorizon);
}


public static Vehicle  initializeRoad() {
		
		Domain d1 = new Domain();
		Domain d2 = new Domain();
		Domain d3 = new Domain();
		Domain d4 = new Domain();
		Domain d5 = new Domain();
		Domain d6 = new Domain();
		
		// additional domains
		Domain d7 = new Domain();
		Domain d10 = new Domain();
		
		Domain d8 = new Domain();
		Domain d11 = new Domain();
		
		
		Domain d9 = new Domain();
		Domain d12 = new Domain();
		
		// domain 1
		Vehicle c00 = new Vehicle(new NodeID(0, 0), new NodeID(1, 2), d1 , new ArrayList<Domain>(){ {}});
		Vehicle c12 = new Vehicle(new NodeID(1, 2), new NodeID(1, 2), d1, new ArrayList<Domain>(){ {}});
		Vehicle c30 = new Vehicle(new NodeID(3, 0), new NodeID(1, 2), d1, new ArrayList<Domain>(){ {add(d4);}});
		Vehicle c33 = new Vehicle(new NodeID(3, 3), new NodeID(1, 2), d1, new ArrayList<Domain>(){ {add(d2); add(d4); add(d5);}});
		d1.setController(c12);
		// domain 2
		Vehicle c07 = new Vehicle(new NodeID(0, 7), new NodeID(1, 6), d2, new ArrayList<Domain>(){ {add(d3);}});
		Vehicle c16 = new Vehicle(new NodeID(1, 6), new NodeID(1, 6), d2, new ArrayList<Domain>(){ {}});
		Vehicle c27 = new Vehicle(new NodeID(2, 7), new NodeID(1, 6), d2, new ArrayList<Domain>(){ {add(d3); add(d5); add(d6);}});
		Vehicle c36 = new Vehicle(new NodeID(3, 6), new NodeID(1, 6), d2, new ArrayList<Domain>(){ {add(d1); add(d5);}});
		d2.setController(c16);
		
		// domain 3
		Vehicle c011 = new Vehicle(new NodeID(0, 11), new NodeID(1, 10), d3, new ArrayList<Domain>(){ {add(d7);}});
		Vehicle c110 = new Vehicle(new NodeID(1, 10), new NodeID(1, 10), d3, new ArrayList<Domain>(){ {}});
		Vehicle c38 = new Vehicle(new NodeID(3, 8), new NodeID(1, 10), d3, new ArrayList<Domain>(){ {add(d2); add(d5); add(d6);}});
		Vehicle c311 = new Vehicle(new NodeID(3, 11), new NodeID(1, 10), d3, new ArrayList<Domain>(){ {add(d6); add(d7); add(d10);}});
		d3.setController(c110);
		
		
		// domain 4
		Vehicle c50 = new Vehicle(new NodeID(5, 0), new NodeID(5, 2), d4, new ArrayList<Domain>(){ {add(d1);}});
		Vehicle c43 = new Vehicle(new NodeID(4, 3), new NodeID(5, 2), d4, new ArrayList<Domain>(){ {add(d1); add(d2); add(d5);}});
		Vehicle c52 = new Vehicle(new NodeID(5, 2), new NodeID(5, 2), d4, new ArrayList<Domain>(){ {add(d1); add(d5);}});
		Vehicle c73 = new Vehicle(new NodeID(7, 3), new NodeID(5, 2), d4, new ArrayList<Domain>(){ {add(d5);}});
		d4.setController(c52);
		
		
		// domain 5
		Vehicle c45 = new Vehicle(new NodeID(4, 5), new NodeID(5, 6), d5, new ArrayList<Domain>(){ {add(d2); add(d1); add(d4);}});
		Vehicle c47 = new Vehicle(new NodeID(4, 7), new NodeID(5, 6), d5, new ArrayList<Domain>(){ {add(d2); add(d3); add(d6);}});
		Vehicle c56 = new Vehicle(new NodeID(5, 6), new NodeID(5, 6), d5, new ArrayList<Domain>(){ {}});
		Vehicle c74 = new Vehicle(new NodeID(7, 4), new NodeID(5, 6), d5, new ArrayList<Domain>(){ {add(d4);}});
		d5.setController(c56);
		
		// domain 6
		Vehicle c49 = new Vehicle(new NodeID(4, 9), new NodeID(5, 10), d6, new ArrayList<Domain>(){ {add(d3); add(d5); add(d2); add(d10); add(d7); }});
		Vehicle c510 = new Vehicle(new NodeID(5, 10), new NodeID(5, 10), d6, new ArrayList<Domain>(){ {}});
		Vehicle c710 = new Vehicle(new NodeID(7, 10), new NodeID(5, 10), d6, new ArrayList<Domain>(){ {add(d10);}});
		Vehicle c78 = new Vehicle(new NodeID(7, 8), new NodeID(5, 10), d6, new ArrayList<Domain>(){ {add(d5);}});
		d6.setController(c510);
		
		// domain 7
		Vehicle c114 = new Vehicle(new NodeID(1, 14), new NodeID(1, 14), d7, new ArrayList<Domain>(){ {}});
		Vehicle c214 = new Vehicle(new NodeID(2, 14), new NodeID(1, 14), d7, new ArrayList<Domain>(){ {add(d3); add(d10); add(d8);}});
		Vehicle c215 = new Vehicle(new NodeID(2, 15), new NodeID(1, 14), d7, new ArrayList<Domain>(){ {add(d10); add(d8);}});
		Vehicle c312 = new Vehicle(new NodeID(3, 12), new NodeID(1, 14), d7, new ArrayList<Domain>(){ {add(d3); add(d6); add(d10);}});
		d7.setController(c114);		

		// domain 10
		Vehicle c513 = new Vehicle(new NodeID(5, 13), new NodeID(5, 13), d10, new ArrayList<Domain>(){ {}});
		Vehicle c414 = new Vehicle(new NodeID(4, 14), new NodeID(5, 13), d10, new ArrayList<Domain>(){ {add(d7); add(d8); add(d11);}});
		Vehicle c612 = new Vehicle(new NodeID(6, 12), new NodeID(5, 13), d10, new ArrayList<Domain>(){ {add(d6); add(d11);}});
		Vehicle c711 = new Vehicle(new NodeID(7, 11), new NodeID(5, 13), d10, new ArrayList<Domain>(){ {add(d6);}});
		d10.setController(c513);	
	
		
		// domain 8
		Vehicle c118 = new Vehicle(new NodeID(1, 18), new NodeID(1, 18), d8, new ArrayList<Domain>(){ {}});
		Vehicle c219 = new Vehicle(new NodeID(2, 19), new NodeID(1, 18), d8, new ArrayList<Domain>(){ {}});
		Vehicle c318 = new Vehicle(new NodeID(3, 18), new NodeID(1, 18), d8, new ArrayList<Domain>(){ {add(d11);}});
		Vehicle c316 = new Vehicle(new NodeID(3, 16), new NodeID(1, 18), d8, new ArrayList<Domain>(){ {add(d7); add(d10); add(d11);}});
		d8.setController(c118);		


		// domain 11
		Vehicle c518 = new Vehicle(new NodeID(5, 18), new NodeID(5, 18), d10, new ArrayList<Domain>(){ {}});
		Vehicle c616 = new Vehicle(new NodeID(6, 16), new NodeID(5, 18), d10, new ArrayList<Domain>(){ {add(d10);}});
		Vehicle c717 = new Vehicle(new NodeID(7, 17), new NodeID(5, 18), d10, new ArrayList<Domain>(){ {add(d10);}});
		Vehicle c619 = new Vehicle(new NodeID(6, 19), new NodeID(5, 18), d10, new ArrayList<Domain>(){ {}});
		d11.setController(c518);	
		
		
		c00.neigbouringCars = new ArrayList<Vehicle> () {{}};
		c12.neigbouringCars = new ArrayList<Vehicle> () {{ add(c00); add(c30); add(c33);}};
		c30.neigbouringCars = new ArrayList<Vehicle> () {{ add(c50);}};
		c33.neigbouringCars = new ArrayList<Vehicle> () {{add(c43); add(c36); add(c45);}};
		
		c07.neigbouringCars = new ArrayList<Vehicle> () {{add(c011);}};
		c16.neigbouringCars = new ArrayList<Vehicle> () {{add(c07); add(c27); add(c36); }};
		c27.neigbouringCars = new ArrayList<Vehicle> () {{add(c38); add(c47); add(c49);}};
		c36.neigbouringCars = new ArrayList<Vehicle> () {{ add(c47); add(c38);}};
		
		
		c011.neigbouringCars = new ArrayList<Vehicle> () {{add(c114);}};
		c110.neigbouringCars = new ArrayList<Vehicle> () {{add(c011); add(c311); add(c38);}};
		c38.neigbouringCars = new ArrayList<Vehicle> () {{ add(c27); add(c47); add(c49);}};
		c311.neigbouringCars = new ArrayList<Vehicle> () {{add(c49); add(c312); add(c414);}};
		
		
		
		c50.neigbouringCars = new ArrayList<Vehicle> () {{add(c30); }};
		c52.neigbouringCars = new ArrayList<Vehicle> () {{ add(c50); add(c43); add(c73);}};
		c43.neigbouringCars = new ArrayList<Vehicle> () {{add(c33); add(c45); add(c36);}};
		c73.neigbouringCars = new ArrayList<Vehicle> () {{add(c74);}};
		
		
		c45.neigbouringCars = new ArrayList<Vehicle> () {{add(c36); add(c33); add(c43);}};
		c56.neigbouringCars = new ArrayList<Vehicle> () {{add(c45); add(c47); add(c74);}};
		c47.neigbouringCars = new ArrayList<Vehicle> () {{ add(c36); add(c38); add(c49);}};
		c74.neigbouringCars = new ArrayList<Vehicle> () {{add(c73); }};
		
		
		c49.neigbouringCars = new ArrayList<Vehicle> () {{add(c38); add(c47); add(c513); add(c312); add(c27); add(c513);}};
		c510.neigbouringCars = new ArrayList<Vehicle> () {{ add(c49); add(c78); add(c710);}};
		c78.neigbouringCars = new ArrayList<Vehicle> () {{add(c56);}};
		c710.neigbouringCars = new ArrayList<Vehicle> () {{ add(c510); add(c78); add(c711);}};
		
		// cars in domain 7
		c114.neigbouringCars = new ArrayList<Vehicle> () {{add(c214); add(c215); add(c312);}};
		c214.neigbouringCars = new ArrayList<Vehicle> () {{add(c011); add(c414); add(c316);}};
		c312.neigbouringCars = new ArrayList<Vehicle> () {{add(c311); add(c513);}};
		c215.neigbouringCars = new ArrayList<Vehicle> () {{add(c414); add(c316);}};
		
		// cars in domain 10
		c513.neigbouringCars = new ArrayList<Vehicle> () {{add(c414); add(c612); add(c711); }};
		c414.neigbouringCars = new ArrayList<Vehicle> () {{add(c215); add(c316); add(c616);}};
		c612.neigbouringCars = new ArrayList<Vehicle> () {{add(c510);}};
		c711.neigbouringCars = new ArrayList<Vehicle> () {{add(c710);}};
		
		// cars in domain 8
		c118.neigbouringCars = new ArrayList<Vehicle> () {{add(c219); add(c318); add(c316);}};
		c219.neigbouringCars = new ArrayList<Vehicle> () {{}};
		c318.neigbouringCars = new ArrayList<Vehicle> () {{add(c518);}};
		c316.neigbouringCars = new ArrayList<Vehicle> () {{add(c414); add(c215); add(c518);}};
		
		// cars in domain 11
		c518.neigbouringCars = new ArrayList<Vehicle> () {{add(c616); add(c619); add(c717); }};
		c619.neigbouringCars = new ArrayList<Vehicle> () {{ add(c215);}};
		c616.neigbouringCars = new ArrayList<Vehicle> () {{add(c513);}};
		c717.neigbouringCars = new ArrayList<Vehicle> () {{add(c711);}};		
		
		Scenario.vehicles.add(c00);
		Scenario.vehicles.add(c12);
		Scenario.vehicles.add(c30);
		Scenario.vehicles.add(c33);
		
		Scenario.vehicles.add(c07);
		Scenario.vehicles.add(c16);
		Scenario.vehicles.add(c27);
		Scenario.vehicles.add(c36);
		
		Scenario.vehicles.add(c011);
		Scenario.vehicles.add(c110);
		Scenario.vehicles.add(c38);
		Scenario.vehicles.add(c311);
		
		Scenario.vehicles.add(c50);
		Scenario.vehicles.add(c52);
		Scenario.vehicles.add(c43);
		Scenario.vehicles.add(c73);
		
		Scenario.vehicles.add(c45);
		Scenario.vehicles.add(c56);
		Scenario.vehicles.add(c47);
		Scenario.vehicles.add(c74);
		
		Scenario.vehicles.add(c49);
		Scenario.vehicles.add(c510);
		Scenario.vehicles.add(c78);
		Scenario.vehicles.add(c710);
		
		// domain 7
		Scenario.vehicles.add(c114);
		Scenario.vehicles.add(c214);
		Scenario.vehicles.add(c312);
		Scenario.vehicles.add(c215);
		
		// domain 10
		Scenario.vehicles.add(c414);
		Scenario.vehicles.add(c513);
		Scenario.vehicles.add(c612);
		Scenario.vehicles.add(c711);
		
		// domain 8
		Scenario.vehicles.add(c118);
		Scenario.vehicles.add(c219);
		Scenario.vehicles.add(c318);
		Scenario.vehicles.add(c316);
		
		// domain 11
		Scenario.vehicles.add(c518);
		Scenario.vehicles.add(c619);
		Scenario.vehicles.add(c616);
		Scenario.vehicles.add(c717);
		
		// route from c33 to c53
		Route rc33 = new Route();
		rc33.setNodeID(c43.id);
		rc33.setRoute(new ArrayList<NodeID>() {{add(c43.id);}});
		c33.routingTable.add(rc33);
		
		Route rc3312 = new Route();
		rc3312.setNodeID(c12.id);
		rc3312.setRoute(new ArrayList<NodeID>() {{ add(c12.id);}});
		c33.routingTable.add(rc3312);
		
		// route c43 -> c73
		Route rc43 = new Route();
		rc43.setNodeID(c73.id);
		rc43.setRoute(new ArrayList<NodeID>() {{add(c73.id);}});
		c43.routingTable.add(rc43);
		
		
		// route c33 -> c43
		Route rc30 = new Route();
		rc30.setNodeID(c50.id);
		rc30.setRoute(new ArrayList<NodeID>() {{add(c50.id);}});
		c30.routingTable.add(rc30);
		
		// route c73 -> c74
		Route rc73= new Route();
		rc73.setNodeID(c74.id);
		rc73.setRoute(new ArrayList<NodeID>() {{add(c74.id);}});
		c73.routingTable.add(rc73);
		
		// route c73 -> c47
		Route rc52 = new Route();
		rc52.setNodeID(c47.id);
		rc52.setRoute(new ArrayList<NodeID>() {{add(c73.id); add(c74.id); add(c56.id); add(c47.id);}});
		c52.routingTable.add(rc52);

		Route rc732 = new Route();
		rc732.setNodeID(c47.id);
		rc732.setRoute(new ArrayList<NodeID>() {{ add(c74.id); add(c56.id); add(c47.id);}});
		c73.routingTable.add(rc732);
		
		Route rc5674 = new Route();
		rc5674.setNodeID(c74.id);
		rc5674.setRoute(new ArrayList<NodeID>() {{ add(c74.id);}});
		c56.routingTable.add(rc5674);
		
		
		Route rc114 = new Route();
		rc114.setNodeID(c215.id);
		rc114.setRoute(new ArrayList<NodeID>() {{ add(c215.id);}});
		c114.routingTable.add(rc114);
		//Route r3 = new Route();
		//Route r4 = new Route();

		
		return c16;
		
	}


	public static void main (String [] args) {
		Scenario x = new Scenario();
		
		Vehicle c = initializeRoad();
		ArrayList<Vehicle> array = c.selectGateways2(1);

		/*
		for(int i = 0; i< array.size();i++) {
		System.out.println(array.get(i).id.x + "   " + array.get(i).id.y);
		}
		*/
		
		Vehicle c33 = Scenario.vehicles.get(3);
		Vehicle c12 = Scenario.vehicles.get(1);
		Vehicle c73 = Scenario.vehicles.get(15);
		Vehicle c27 = Scenario.vehicles.get(6);
		Vehicle c52 = Scenario.vehicles.get(13);
		Vehicle c47 = Scenario.vehicles.get(18);
		Vehicle c74 = Scenario.vehicles.get(19);
		//Vehicle c74 = Scenario.vehicles.get(19);
		Sim.init();
		//System.out.println(c33.canSend());
		//c12.testFunction(c12.id);
		x.simulate(50);
		//c33.updateRoutingTable(c33.isrouteAvailable(c73.id));
		DataPacket packet = new DataPacket(Scenario.counter++, 1, c12.id, c47.id, 1250 ,0);
		DataPacket packet2 = new DataPacket(Scenario.counter++, 1, c33.id, new NodeID(2, 15), 1250 ,3);
		DataPacket packet3 = new DataPacket(Scenario.counter++, 1, c12.id, c73.id, 1250 ,6);
		DataPacket packet4 = new DataPacket(Scenario.counter++, 1, c33.id, c12.id, 1250 ,3);
		DataPacket packet5 = new DataPacket(Scenario.counter++, 1, c33.id, c73.id, 1250 ,3);
		DataPacket packet6 = new DataPacket(Scenario.counter++, 1, c12.id, c74.id, 1250 ,3);
		//packet.setRoute(c33.isrouteAvailable(c73.id));
		c12.testing(0, packet);
		c33.testing(2, packet2);
		c12.testing(4, packet3);
		c33.testing(8, packet4);
		c33.testing(12, packet5);
		c12.testing(18, packet6);
		
		
	    Sim.start(); 
	 /*   
		ArrayList<Vehicle> v = c33.selectGateways2(1);
		for(int i = 0; i< v.size();i++) {
			 System.out.println("Vehicle "+v.get(i)+"    "+ v.get(i).reachableDomains.size());
		}
	   */
		//System.out.println(c73.equals(c73));
	    
		
		
	}
}