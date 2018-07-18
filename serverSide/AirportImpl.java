package serverSide;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

import comInf.Airport;
import comInf.HostessStates;
import comInf.Logger;
import comInf.PassengerStates;
import comInf.PilotStates;
import comInf.SimulPar;

/**
 * Airport Monitor class
 */
public class AirportImpl implements Airport{
	
	private Logger logStub;
	private Lock mutex;						// mutual exclusion lock
	private Condition readyToFlight, 		// ready to flight condition variable
					  waitForPassenger, 	// waiting for passenger condition variable
					  inQueue;				// waiting in queue condition variable
	private boolean boarding,				// signaling boarding is ready
					checkIn, 				// signaling check In is ready
					showPass;				// signaling that is possible to show the passport
	private int passengersLeftAirport,	    // total of passengers that arrived at destination 
				passengersInQueue, 			// total of passengers waiting in queue
				passengersBoarded; 			// total of passengers in flight
	
	/**
	 * Constructs the airport implementation
	 * @param log Logger Object
	 */
	public AirportImpl(Logger log){
		logStub = log;
		mutex = new ReentrantLock();
		readyToFlight = mutex.newCondition();
		waitForPassenger = mutex.newCondition();
		inQueue = mutex.newCondition();
		boarding = false;
		checkIn = false;
		showPass = false;
		passengersLeftAirport = 0;
		passengersInQueue = 0;
		passengersBoarded = 0;
	}
	
	/**
	 * Signals that the plane is ready for boarding (originated by the pilot)
	 */
	@Override
	public void signalReadyForBoarding(){
		mutex.lock();
		
		logStub.boardingStarted();
		logStub.setStatePilot(PilotStates.RFB);
		boarding = true;
		passengersBoarded = 0;
		
		mutex.unlock();
	}
	
	/**
	 * Waits until the flight is ready to take off (originated by the pilot)
	 * @return the number of passengers in the plane
	 */
	@Override
	public int waitUntilReadyToFlight(){
		mutex.lock();
		
		logStub.setStatePilot(PilotStates.WFB);
		waitForPassenger.signal();					// Signal Hostess to wake up and start boarding
		
		while(boarding)
			try{
				readyToFlight.await();				// Blocked until boarding ends
			}catch(InterruptedException e){}
		
		mutex.unlock();
		
		return passengersBoarded;
	}
	
	/**
	 * Waits for the next flight (originated by the Hostess)
	 * @return boolean that indicates if there are more passengers to flight
	 */
	@Override
	public boolean waitForNextFlight(){
		mutex.lock();
		
		if(passengersLeftAirport == SimulPar.NPASS){
			mutex.unlock();
			return true;
		}
		
		logStub.setStateHostess(HostessStates.WFF);
		
		while(!boarding)
			try{
				waitForPassenger.await();			// Blocked until boarding ends
			}catch(InterruptedException e){}
		
		mutex.unlock();
		return false;
	}
		
	/**
	 * Waits for passengers to enter the queue (originated by the Hostess)
	 */
	@Override
	public void waitForPassenger(){
		mutex.lock();
		
		logStub.setStateHostess(HostessStates.WFP);
		
		while(passengersInQueue == 0)
			try{
				waitForPassenger.await();			// Blocked until passengers enter queue
			}catch(InterruptedException e){}
		
		mutex.unlock();
	}
	
	/**
	 * Waits in queue until Hostess verifies the passport (originated by the Passenger)
	 * @param passengerId Passenger Identification
	 */
	@Override
	public void waitInQueue(int passengerId){
		mutex.lock();
		
		passengersInQueue++;
		logStub.setStatePassenger(passengerId, PassengerStates.IQ);
		
		waitForPassenger.signal();					// Signal Hostess to wake up and wait for documents
		
		while(!checkIn)
			try{
				inQueue.await();					// Blocked until check In done
			}catch(InterruptedException e){}
		
		checkIn = false;
		showPass = true;
		passengersBoarded++;
		passengersInQueue--;
		waitForPassenger.signal();					// Signal Hostess to wake up and look at documents
		logStub.passengerChecked(passengerId);
		
		mutex.unlock();
	}
	
	/**
	 * Waits until Passengers shows passport (originated by the Hostess)
	 * @return boolean that indicates if the flight is ready to take off
	 */
	@Override
	public boolean checkPassport(){
		mutex.lock();
		
		checkIn = true;
		inQueue.signal();									// Signal Passenger to wake up and show documents
		logStub.setStateHostess(HostessStates.CP);
		
		while(!showPass)
			try{
				waitForPassenger.await();					// Blocked until passengers shows documents
			}catch(InterruptedException e){}
		
		showPass = false;
		
		if(passengersBoarded == SimulPar.PLANECAPAC || (passengersBoarded + passengersLeftAirport) == 21){
			mutex.unlock();
			return true;
		}
		else if(passengersBoarded >= SimulPar.MINNPASS && passengersInQueue == 0){
			mutex.unlock();
			return true;
		}
		
		mutex.unlock();
		return false;
	}
	
	/**
	 * Signals the pilot that the flight is ready to start (originated by the Hostess)
	 */
	@Override
	public void signalReadyToFlight(){
		mutex.lock();
		
		logStub.setStateHostess(HostessStates.RTF);
		boarding = false;
		passengersLeftAirport += passengersBoarded;
		
		readyToFlight.signal();							// Signal Pilot to wake up and start the flight
		
		mutex.unlock();
	}
	
	/**
	 * Checks if the flights are finished (originated by the Pilot)
	 * @return boolean that indicates if the flights are finished
	 */
	@Override
	public boolean isFinished(){
		mutex.lock();
		
		if(passengersLeftAirport == SimulPar.NPASS){
			logStub.setStatePilot(PilotStates.FB);
			logStub.logEnd();
			mutex.unlock();
			return true;
		}
		
		mutex.unlock();
		return false;
	}	
}
