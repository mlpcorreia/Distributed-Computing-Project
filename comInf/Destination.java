package comInf;

/**
 * Destination Interface
 */
public interface Destination {

	/**
	 * Pilot drops passengers at Destination
	 * @param passengersOnPlane Number of passenger in the plane
	 */
	public void dropPassengers(int passengersOnPlane);
	
	/**
	 * Passengers leave the plane
	 * @param passengerId Passenger Identification
	 */
	public void leavePlane(int passengerId);
	
	/**
	 * Block the passenger in the plane until arrival
	 */
	public void waitArrival();
}
