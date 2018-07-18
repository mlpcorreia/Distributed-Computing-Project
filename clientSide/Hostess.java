package clientSide;

import comInf.Airport;

/**
 * Definition of the Hostess thread - monitor-based solution.
 */
public class Hostess extends Thread{
	
	/**
	 * Airport stub
	 * @serialField airportStub
	 */
	private Airport airportStub;
	
	/**
	 * Constructs a Hostess Thread
	 * @param airport Airport Object
	 */
	public Hostess(Airport airport){
		airportStub = airport;
	}
	
	/**
	 * Life cycle of the Hostess thread
	 */
	@Override
	public void run(){
		while(!airportStub.waitForNextFlight()){
			do{
				airportStub.waitForPassenger();
			}while(!airportStub.checkPassport());
			
			airportStub.signalReadyToFlight();
		}
		
		System.out.println("Hostess life cycle ended!");
	}
}
