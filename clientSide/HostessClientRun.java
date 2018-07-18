package clientSide;

import comInf.AirportStub;

/**
 * This data type simulates a client-side solution of the Air Lift Problem 
 * that implements the client-server model type 2 (server replication)
 * Communication is based on message passing over sockets using the TCP protocol.
 */
public class HostessClientRun {

	/**
	 * Main Program
	 */
	public static void main(String[] args){
		
		Hostess hostess;
		AirportStub airportStub;
		
		airportStub = new AirportStub("host", port);
		hostess = new Hostess(airportStub);
		
		hostess.start();
		
		try{
			hostess.join();
		}catch(InterruptedException e){}
		
		System.out.println("Hostess Client Run terminated!");
	}
}
