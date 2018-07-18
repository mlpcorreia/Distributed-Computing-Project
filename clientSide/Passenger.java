package clientSide;

import comInf.Airport;
import comInf.Destination;
import comInf.Plane;

/**
 * Definition of the Passenger thread - monitor-based solution.
 */
public class Passenger extends Thread{

	/**
	 * Airport stub
	 * @serialField airportStub
	 */
	private Airport airportStub;
	
	/**
	 * Plane stub
	 * @serialField flightStub
	 */
	private Plane flightStub;
	
	/**
	 * Destination stub
	 * @serialField destStub
	 */
	private Destination destStub;
	
	/**
	 * Passenger Identification
	 * @serialField id
	 */
	private int id;
	
	/**
	 * Constructs a Passenger Thread
	 * @param id Passenger Identification
	 * @param airport Airport Object
	 * @param flight Plane Object
	 * @param dest Destination Object
	 */
	public Passenger(int id, Airport airport, Plane flight, Destination dest){
		this.id = id;
		airportStub = airport;
		flightStub = flight;
		destStub = dest;
	}
	
	/**
	 * Get the passenger Id
	 * @return the Passenger Identification
	 */
	public int getPassengerId(){
		return id;
	}
	
	/**
	 * Life cycle of the Passenger thread
	 */
	@Override
	public void run(){
		travelToAirport();
		airportStub.waitInQueue(id);
		flightStub.waitUntilDestination(id);
		destStub.leavePlane(id);
		
		System.out.println("Passenger " + id + " life cycle ended!");
	}
	
	/**
	 * Travel to the Airport (internal operation)
	 */
	public void travelToAirport(){
		try {
			Thread.sleep((long) (Math.random()*1000));		// range + valMin
		} catch (InterruptedException e) {}
	}
}
