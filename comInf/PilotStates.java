package comInf;

/**
 * 
 * This class contains the different states of the Pilot
 *
 */

public enum PilotStates {
	
	FB,			// FLYING_BACK 		  Initial state
	RFB,		// READY_FOR_BOARDING Transition state
	WFB,		// WAIT_FOR_BOARDING  Blocked state
	F,			// FLYING 			  Transition state
	DP			// DROPING_PASSENGERS Blocked state
}
