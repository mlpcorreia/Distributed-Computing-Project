package clientSide;

import comInf.AirportStub;
import comInf.DestinationStub;
import comInf.PlaneStub;
import comInf.SimulPar;

/**
 * This data type simulates a client-side solution of the Air Lift Problem 
 * that implements the client-server model type 2 (server replication)
 * Communication is based on message passing over sockets using the TCP protocol.
 */
public class PassengersClientRun {

	/**
	 * Main Program
	 */
	public static void main(String[] args) {
		
		Passenger[] passengers = new Passenger[SimulPar.NPASS];
		AirportStub airportStub;
		PlaneStub planeStub;
		DestinationStub destStub;
		
		airportStub = new AirportStub("host", port);
		planeStub = new PlaneStub("host", port);
		destStub = new DestinationStub("host", port);
		
		for(int i = 0; i < SimulPar.NPASS; i++)
			passengers[i] = new Passenger(i, airportStub, planeStub, destStub);
		
		for(int i = 0; i < SimulPar.NPASS; i++)
			passengers[i].start();
		
		for(int i = 0; i < SimulPar.NPASS; i++)
			try{
				passengers[i].join();
			}catch(InterruptedException e){}
		
		System.out.println("Passengers Client Run terminated!");
	}

}
