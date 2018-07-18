package comInf;

/**
 *	Message Interface
 */
public interface Message {

	/**
	 * Conversion to an XML string
	 * @return string with the message description
	 */
	public String toXMLString();
	
	/**
	 * Get Message type 
	 * @return msgType
	 */
	public int getType();
	
	/**
	 * Get passenger identification
	 * @return passenger id
	 */
	public int getPassengerId();
	
	/**
	 * Get Passengers on Plane
	 * @return passengersBoarded 
	 */
	public int getPassengersBoarded();
	
	/**
	 * Get flying state
	 * @return boolean that states if the pilot is flying back or to the destination
	 */
	public boolean getFlying();
	
	/**
	 * Get the pilot state at a given moment
	 * @return the pilot state
	 */
	public PilotStates getPilotState();
	
	/**
	 * Get the hostess state at a given moment
	 * @return the hostess state
	 */
	public HostessStates getHostessState();
	
	/**
	 * Get the passenger state at a given moment
	 * @return the passenger state
	 */
	public PassengerStates getPassengerState();
}
