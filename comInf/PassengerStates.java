package comInf;

/**
 * 
 * This class contains the different states of the Passengers
 *
 */

public enum PassengerStates {
	
	GTA,			// GOING_TO_AIRPORT Initial state
	IQ,				// IN_QUEUE         Blocked state
	IF,				// IN_FLIGHT        Blocked state
	AD				// AT_DESTINATION   Final state
}
