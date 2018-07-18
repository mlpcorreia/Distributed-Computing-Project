package comInf;

/**
 * 
 * This class contains the different states of the Hostess
 *
 */

public enum HostessStates {
	
	WFF,		// WAIT_FOR_FLIGHT    Initial state and blocked
	WFP,		// WAIT_FOR_PASSENGER Blocked state
	CP,			// CHECK_PASSPORT     Blocked state
	RTF			// READY_TO_FLIGHT    Transition state
}
