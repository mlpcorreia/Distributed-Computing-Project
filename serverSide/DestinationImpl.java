package serverSide;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import comInf.Destination;
import comInf.Logger;
import comInf.PassengerStates;
import comInf.PilotStates;

import java.util.concurrent.locks.Condition;

/**
 * Destination Monitor class
 */
public class DestinationImpl implements Destination{
	
	private Logger logStub;
	private Lock mutex;							// mutual exclusion lock
	private Condition inFlight, 				// waiting to arrival condition variable
					  waitDrop;					// waiting passengers to leave plane condition variable
	private int boardedPassengers; 				// total passengers at destination
	
	/**
	 * Constructs the destination implementation
	 * @param log Logger Object
	 */
	public DestinationImpl(Logger log){
		logStub = log;
		mutex = new ReentrantLock();
		inFlight = mutex.newCondition();
		waitDrop = mutex.newCondition();
		boardedPassengers = 0;
	}

	/**
	 * Drops passengers at Destination (originated by the Pilot)
	 * @param passengersOnPlane Number of passengers on plane
	 */
	@Override
	public void dropPassengers(int passengersOnPlane){
		mutex.lock();

		logStub.arrived();
		logStub.setStatePilot(PilotStates.DP);		
		
		boardedPassengers = passengersOnPlane;
		
		// Waking Up Passengers to leave plane
		for(int i = 0; i < passengersOnPlane; i++){
			inFlight.signal();
		}
		
		while(boardedPassengers != 0)
			try {
				waitDrop.await();					// Blocks Pilot until all the passengers left the plane
			} catch (InterruptedException e) {}				

		logStub.returning();
		
		mutex.unlock();
	}
	
	/**
	 * Leave the plane (originated by the Passenger)
	 * @param passengerId Passenger Identification
	 */
	@Override
	public void leavePlane(int passengerId){
		
		mutex.lock();
		
		while(boardedPassengers == 0)
			try {
				inFlight.await();					// Passengers blocked until arrival
			} catch (InterruptedException e) {}
		
		logStub.setStatePassenger(passengerId, PassengerStates.AD);
		boardedPassengers--;
		waitDrop.signal();							// Signal Pilot wakes up to check if all the passengers are out
			
		mutex.unlock();
	}
	
	/**
	 * Block the passenger in the plane until arrival
	 */
	@Override
	public void waitArrival(){
		mutex.lock();
		
		while(boardedPassengers == 0)
			try {
				inFlight.await();					// Passengers blocked until arrival
			} catch (InterruptedException e) {}
		
		mutex.unlock();
	}
}
