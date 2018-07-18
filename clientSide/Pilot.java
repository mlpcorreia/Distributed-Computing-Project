package clientSide;

import comInf.Airport;
import comInf.Destination;
import comInf.Plane;

/**
 * Definition of the Pilot thread - monitor-based solution.
 */
public class Pilot extends Thread{
	
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
	 * Number of passengers in the current flight
	 * @serialField passengersOnPlane
	 */
	private int passengersOnPlane;
	
	/**
	 * Constructs a Pilot Thread
	 * @param airport Airport Object
	 * @param flight Plane Object
	 * @param dest Destination Object
	 */
	public Pilot(Airport airport, Plane flight, Destination dest){
		flightStub = flight;
		airportStub = airport;
		destStub = dest;
	}
	
	/**
	 * Life cycle of the Pilot thread
	 */
	@Override
	public void run(){
		while(!airportStub.isFinished()){
			flightStub.flight(false);
			airportStub.signalReadyForBoarding();
			passengersOnPlane  = airportStub.waitUntilReadyToFlight();
			flightStub.flight(true);
			destStub.dropPassengers(passengersOnPlane);
		}
		
		System.out.println("Pilot life cycle ended!");
	}
}
