package serverSide;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import comInf.Destination;
import comInf.Logger;
import comInf.PassengerStates;
import comInf.PilotStates;
import comInf.Plane;

/**
 * Plane Monitor class
 */
public class PlaneImpl implements Plane{
	
	private Logger log;
	private Destination dest;
	private Lock mutex;		// mutual exclusion lock
	
	/**
	 * Constructs the Plane implementation
	 * @param log Logger Object
	 * @param dest Destination Object
	 */
	public PlaneImpl(Logger log, Destination dest){
		this.log = log;
		this.dest = dest;
		mutex = new ReentrantLock();
	}
	
	/**
	 * Flying to destination and back to the Airport (originated by the Pilot)
	 * @param state Pilot flying state
	 */
	@Override
	public void flight(boolean state){
		mutex.lock();
		
		if(state){
			log.departedWith();
			log.setStatePilot(PilotStates.F);
			try {
				Thread.sleep((long) (Math.random() * 1000));		// range + valMin
			} catch (InterruptedException e) {}
		}
		else{
			log.setStatePilot(PilotStates.FB);
			try {
				Thread.sleep((long) (Math.random() * 1000));		// range + valMin
			} catch (InterruptedException e) {}
		}
		
		
		mutex.unlock();
	}
	
	/**
	 * Waits in plane until arrival (originated by the Passenger)
	 * @param passengerId Passenger Identification
	 */
	@Override
	public void waitUntilDestination(int passengerId){
		mutex.lock();
		
		log.setStatePassenger(passengerId, PassengerStates.IF);
		
		mutex.unlock();
		
		dest.waitArrival();
	}
}
