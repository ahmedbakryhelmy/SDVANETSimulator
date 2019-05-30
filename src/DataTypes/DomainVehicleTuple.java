package DataTypes;
import java.util.ArrayList;

import Components.Vehicle;
public class DomainVehicleTuple {

	
	private Domain domain;
	private ArrayList<Vehicle> vehicles;
	
	public Domain getDomain() {
		return domain;
	}
	public void setDomain(Domain domain) {
		this.domain = domain;
	}
	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
}
