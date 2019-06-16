package DataTypes;

import java.util.ArrayList;

import Components.Vehicle;

public class Domain {
	
	private Vehicle controller;
	private ArrayList<Vehicle> vehicles;
	

	public void setController(Vehicle controller) {
	
		this.controller=controller;
	}
	public Vehicle getController() {
		return controller;
	}
	public boolean equals(Domain d) {
		if(d.controller.equals(this.controller)) {
			return true;
		}
		
		return false;
	}
	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	

}
