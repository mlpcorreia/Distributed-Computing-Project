package comInf;

/**
 * Airport Interface
 */
public interface Airport {

	/**
	 * Pilot signals that the plane is ready for boarding
	 */
	public void signalReadyForBoarding();
	
	/**
	 * Pilot waits until the flight is ready to take off
	 * @return the number of passengers in the plane
	 */
	public int waitUntilReadyToFlight();
	
	/**
	 * Hostess waits for the next flight
	 * @return boolean that indicates if there are more passengers to flight
	 */
	public boolean waitForNextFlight();
	
	/**
	 * Hostess waits for passengers to enter the queue
	 */
	public void waitForPassenger();
	
	/**
	 * Passengers waits in queue until Hostess verifies the passport
	 * @param passengerId Passenger Identification
	 */
	public void waitInQueue(int passengerId);
	
	/**
	 * Hostess waits until Passengers shows passport
	 * @return boolean that indicates if the flight is ready to take off
	 */
	public boolean checkPassport();
	
	/**
	 * Hostess signals the pilot that the flight is ready to start
	 */
	public void signalReadyToFlight();
	
	/**
	 * Pilot checks if the flights are finished
	 * @return boolean that indicates if the flights are finished
	 */
	public boolean isFinished();
}
