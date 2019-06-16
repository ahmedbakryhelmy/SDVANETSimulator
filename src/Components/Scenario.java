package Components;

import java.util.ArrayList;

import DataTypes.Domain;
import DataTypes.NodeID;

public class Scenario {
	
static	ArrayList<NodeID> sendingNodes = new ArrayList<NodeID> ();
static ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
static int counter = 0;
static int gatewaySelectionAlgorithm = 1; // 1 for the new one and 2 for the old one
static int gatewaysFilter = 1;
static double txDataBytes = 0;
static double txDataPacketsDelay = 0;
static int totalRxPackets = 0;
static int totalRxRouteUpdatepackets = 0;
static int totalRxRouteRequestPacket = 0;
static int totalRxRouteInfoPackets = 0;



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

public Vehicle  initializeRoad() {
	
	Domain d1 = new Domain();
	Domain d2 = new Domain();
	Domain d3 = new Domain();
	Domain d4 = new Domain();
	Domain d5 = new Domain();
	Domain d6 = new Domain();
	
	// domain 1
	Vehicle c00 = new Vehicle(new NodeID(0, 0), new NodeID(1, 2), d1 , new ArrayList<Domain>(){ {}});
	Vehicle c12 = new Vehicle(new NodeID(1, 2), new NodeID(1, 2), d1, new ArrayList<Domain>(){ {}});
	Vehicle c30 = new Vehicle(new NodeID(3, 0), new NodeID(1, 2), d1, new ArrayList<Domain>(){ {add(d4);}});
	Vehicle c33 = new Vehicle(new NodeID(3, 3), new NodeID(1, 2), d1, new ArrayList<Domain>(){ {add(d2); add(d4); add(d5);}});
	d1.setController(c12);
	// domain 2
	Vehicle c07 = new Vehicle(new NodeID(0, 7), new NodeID(1, 6), d2, new ArrayList<Domain>(){ {add(d3);}});
	Vehicle c16 = new Vehicle(new NodeID(1, 6), new NodeID(1, 6), d2, new ArrayList<Domain>(){ {add(d5); add(d3);}});
	Vehicle c27 = new Vehicle(new NodeID(2, 7), new NodeID(1, 6), d2, new ArrayList<Domain>(){ {add(d3); add(d5); add(d6);}});
	Vehicle c36 = new Vehicle(new NodeID(3, 6), new NodeID(1, 6), d2, new ArrayList<Domain>(){ {add(d3); add(d5);}});
	d2.setController(c16);
	
	// domain 3
	Vehicle c011 = new Vehicle(new NodeID(0, 11), new NodeID(1, 10), d3, new ArrayList<Domain>(){ {}});
	Vehicle c110 = new Vehicle(new NodeID(1, 10), new NodeID(1, 10), d3, new ArrayList<Domain>(){ {add(d2); add(d6);}});
	Vehicle c38 = new Vehicle(new NodeID(3, 8), new NodeID(1, 10), d3, new ArrayList<Domain>(){ {add(d2); add(d5); add(d6);}});
	Vehicle c311 = new Vehicle(new NodeID(3, 11), new NodeID(1, 10), d3, new ArrayList<Domain>(){ {add(d6);}});
	d3.setController(c110);
	
	
	// domain 4
	Vehicle c50 = new Vehicle(new NodeID(5, 0), new NodeID(5, 2), d4, new ArrayList<Domain>(){ {add(d1);}});
	Vehicle c43 = new Vehicle(new NodeID(4, 3), new NodeID(5, 2), d4, new ArrayList<Domain>(){ {add(d1); add(d2); add(d5);}});
	Vehicle c52 = new Vehicle(new NodeID(5, 2), new NodeID(5, 2), d4, new ArrayList<Domain>(){ {add(d1); add(d5);}});
	Vehicle c73 = new Vehicle(new NodeID(7, 3), new NodeID(5, 2), d4, new ArrayList<Domain>(){ {add(d5);}});
	d4.setController(c52);
	
	
	// domain 5
	Vehicle c45 = new Vehicle(new NodeID(4, 5), new NodeID(5, 6), d5, new ArrayList<Domain>(){ {add(d2); add(d1); add(d5);}});
	Vehicle c47 = new Vehicle(new NodeID(4, 7), new NodeID(5, 6), d5, new ArrayList<Domain>(){ {add(d2); add(d3); add(d6);}});
	Vehicle c56 = new Vehicle(new NodeID(5, 6), new NodeID(5, 6), d5, new ArrayList<Domain>(){ {add(d2);}});
	Vehicle c74 = new Vehicle(new NodeID(7, 4), new NodeID(5, 6), d5, new ArrayList<Domain>(){ {add(d4);}});
	d5.setController(c56);
	
	// domain 6
	Vehicle c49 = new Vehicle(new NodeID(4, 9), new NodeID(5, 10), d6, new ArrayList<Domain>(){ {add(d3); add(d5); add(d2);}});
	Vehicle c510 = new Vehicle(new NodeID(5, 10), new NodeID(5, 10), d6, new ArrayList<Domain>(){ {add(d3); add(d5);}});
	Vehicle c710 = new Vehicle(new NodeID(7, 10), new NodeID(5, 10), d6, new ArrayList<Domain>(){ {add(d5);}});
	Vehicle c78 = new Vehicle(new NodeID(7, 8), new NodeID(5, 10), d6, new ArrayList<Domain>(){ {add(d5);}});
	d6.setController(c510);
	
	
	c00.neigbouringCars = new ArrayList<Vehicle> () {{ add(c12); add(c30);}};
	c12.neigbouringCars = new ArrayList<Vehicle> () {{ add(c00); add(c30); add(c33);}};
	c30.neigbouringCars = new ArrayList<Vehicle> () {{ add(c00); add(c12); add(c33);}};
	c33.neigbouringCars = new ArrayList<Vehicle> () {{ add(c12); add(c30);}};
	
	c07.neigbouringCars = new ArrayList<Vehicle> () {{add(c16); add(c27); }};
	c16.neigbouringCars = new ArrayList<Vehicle> () {{add(c07); add(c27); add(c36); }};
	c27.neigbouringCars = new ArrayList<Vehicle> () {{add(c07); add(c16); add(c36); }};
	c36.neigbouringCars = new ArrayList<Vehicle> () {{ add(c16); add(c27);}};
	
	
	c011.neigbouringCars = new ArrayList<Vehicle> () {{add(c110); add(c311); }};
	c110.neigbouringCars = new ArrayList<Vehicle> () {{add(c011); add(c311); add(c38);}};
	c38.neigbouringCars = new ArrayList<Vehicle> () {{ add(c110); add(c311);}};
	c311.neigbouringCars = new ArrayList<Vehicle> () {{add(c011); add(c110); add(c38); }};
	
	
	
	c50.neigbouringCars = new ArrayList<Vehicle> () {{add(c52); }};
	c52.neigbouringCars = new ArrayList<Vehicle> () {{ add(c50); add(c43); add(c73);}};
	c43.neigbouringCars = new ArrayList<Vehicle> () {{add(c52); add(c73); }};
	c73.neigbouringCars = new ArrayList<Vehicle> () {{add(c52); add(c43); }};
	
	
	c45.neigbouringCars = new ArrayList<Vehicle> () {{add(c56); add(c47);}};
	c56.neigbouringCars = new ArrayList<Vehicle> () {{add(c45); add(c47); add(c74);}};
	c47.neigbouringCars = new ArrayList<Vehicle> () {{ add(c56); add(c45);}};
	c74.neigbouringCars = new ArrayList<Vehicle> () {{add(c56); }};
	
	
	c49.neigbouringCars = new ArrayList<Vehicle> () {{add(c510); }};
	c510.neigbouringCars = new ArrayList<Vehicle> () {{ add(c49); add(c78); add(c710);}};
	c78.neigbouringCars = new ArrayList<Vehicle> () {{add(c710); add(c510);}};
	c710.neigbouringCars = new ArrayList<Vehicle> () {{ add(c510); add(c78);}};
	
	

	return c16;
	
}

public static void main (String [] args) {
	Scenario x = new Scenario();
	Vehicle c = x.initializeRoad();
	ArrayList<Vehicle> array = c.selectGateways2(1);
	
	for(int i = 0; i< array.size();i++) {
	System.out.println(array.get(i).id.x + "   " + array.get(i).id.y);
	}
	
}

}
