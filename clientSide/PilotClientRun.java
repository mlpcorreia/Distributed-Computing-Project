package clientSide;

import comInf.AirportStub;
import comInf.DestinationStub;
import comInf.PlaneStub;

/**
 * This data type simulates a client-side solution of the Air Lift Problem 
 * that implements the client-server model type 2 (server replication)
 * Communication is based on message passing over sockets using the TCP protocol.
 */
public class PilotClientRun {

	/**
	 * Main Program
	 */
	public static void main(String[] args) {
		
		Pilot pilot;
		AirportStub airportStub;
		PlaneStub planeStub;
		DestinationStub destStub;
		
		airportStub = new AirportStub("host", port);
		planeStub = new PlaneStub("host", port);
		destStub = new DestinationStub("host", port);
		
		pilot = new Pilot(airportStub, planeStub, destStub);
		
		pilot.start();
		
		try{
			pilot.join();
		}catch(InterruptedException e){}
		
		/* Servers Shutdown Order */
		
		airportStub.shutdown();
		planeStub.shutdown();
		destStub.shutdown();
		
		System.out.println("Pilot Client Run terminated!");
	}
}
