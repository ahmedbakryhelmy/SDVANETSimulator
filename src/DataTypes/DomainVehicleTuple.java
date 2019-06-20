package DataTypes;
import java.util.ArrayList;

import Components.Vehicle;
public class DomainVehicleTuple {

	
	private Vehicle vehicle;
	private ArrayList<Domain> domains;
	
	public DomainVehicleTuple(Vehicle vehicle, ArrayList<Domain> domains) {
		
		this.domains = domains;
		this.vehicle = vehicle;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public ArrayList<Domain> getDomains() {
		return domains;
	}
	public void setDomains(ArrayList<Domain> domains) {
		this.domains = domains;
	}
	
}
