package comInf;

/**
 * Plane Interface
 */
public interface Plane {

	/**
	 * Pilot flying to destination and back to the Airport
	 * @param state Pilot flying state
	 */
	public void flight(boolean state);
	
	/**
	 * Passengers waits in plane until arrival
	 * @param passengerId Passenger Identification
	 */
	public void waitUntilDestination(int passengerId);
}
