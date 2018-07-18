package serverSide;

import comInf.ClientProxy;
import comInf.Message;
import comInf.MessageException;
import comInf.PlaneMessage;
import comInf.ProcessMessage;

/**
 * This data type defines the interface to the Plane in a solution of the Air Lift Problem 
 * that implements the type 2 client-server model (server replication)
 */
public class PlaneInterface implements ProcessMessage{

	/**
	 * Plane (represents the service to be provided)
	 * @serialField flight
	 */
	private PlaneImpl flight;
	
	/**
	 * Constructs the interface to the plane
	 * @param flight Plane implementation
	 */
	public PlaneInterface(PlaneImpl flight){
		this.flight = flight;
	}
	
	/**
	 * Processing the messages by performing the corresponding task.
	 * Generating a reply message.
	 * @param inMessage message with the request
	 * @return reply message
	 * @throws MessageException if the message with the request is considered invalid
	 */
	@Override
	public Message processAndReply(Message inMessage) throws MessageException {
		PlaneMessage outMessage = null;
		
		/* Validating the received message*/
		
		switch(inMessage.getType()){
			case PlaneMessage.FLIGHT:
				break;
			case PlaneMessage.WAITDESTINATION:
				if((inMessage.getPassengerId() < 0) || (inMessage.getPassengerId() > 21))
					throw new MessageException("Passenger Id invalid!", inMessage);
				break;
			case PlaneMessage.SHUTDOWN:
				break;
			default:
				throw new MessageException("Invalid Type!", inMessage);	
		}
		
		/* Processing */
		
		switch(inMessage.getType()){
			case PlaneMessage.FLIGHT:
				flight.flight(inMessage.getFlying());
				outMessage = new PlaneMessage(PlaneMessage.ACK);
				break;
			case PlaneMessage.WAITDESTINATION:
				flight.waitUntilDestination(inMessage.getPassengerId());
				outMessage = new PlaneMessage(PlaneMessage.ACK);
				break;
			case PlaneMessage.SHUTDOWN:
				PlaneServerRun.waitConnection = false;
				(((ClientProxy) (Thread.currentThread())).getScon()).setTimeout(10);
				outMessage = new PlaneMessage(PlaneMessage.ACK);
				break;
		}
		
		return outMessage;
	}
}
