package comInf;

/**
 * Logger Interface
 */
public interface Logger {
	
	/**
	 * Set the pilot state and save it in the log file
	 * @param state PilotState
	 */
	public void setStatePilot(PilotStates state);
	
	/**
	 * Set the hostess state and save it in the log file
	 * @param state Hostess State
	 */
	public void setStateHostess(HostessStates state);
	
	/**
	 * Set the passenger state and save it in the log file
	 * @param id Passenger Identification
	 * @param state Passenger State
	 */
	public void setStatePassenger(int id, PassengerStates state);
	
	/**
	 * Saves in the log file that the boarding started
	 */
	public void boardingStarted();
	
	/**
	 * Saves in the log file the passenger that got is passport checked by the hostess
	 * @param id Passenger Identification
	 */
	public void passengerChecked(int id);
	
	/**
	 * Saves in the log file the number of passengers that left in the plane to the destination
	 */
	public void departedWith();
	
	/**
	 * Saves in the log file that the plane arrived at the destination
	 */
	public void arrived();
	
	/**
	 * Saves in the log file that the plane is returning to the airport
	 */
	public void returning();
	
	/**
	 * Saves in the log file the final results of the Air Lift problem and closes the buffer 
	 */
	public void logEnd();
}
