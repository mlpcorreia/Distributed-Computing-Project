package serverSide;

import comInf.DestinationMessage;
import comInf.Message;
import comInf.ClientProxy;
import comInf.MessageException;
import comInf.ProcessMessage;

/**
 * This data type defines the interface to the Destination in a solution of the Air Lift Problem 
 * that implements the type 2 client-server model (server replication)
 */
public class DestinationInterface implements ProcessMessage{

	/**
	 * Destination (represents the service to be provided)
	 * @serialField dest
	 */
	private DestinationImpl dest;
	
	/**
	 * Constructs the interface to the destination
	 * @param dest Destination Implementation
	 */
	public DestinationInterface(DestinationImpl dest){
		this.dest = dest;
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
		DestinationMessage outMessage = null;
		
		/* Validating the received message*/
		
		switch(inMessage.getType()){
			case DestinationMessage.DROPPASSENGERS:
				break;
			case DestinationMessage.LEAVEPLANE:
				break;
			case DestinationMessage.WAITARRIVAL:
				break;
			case DestinationMessage.SHUTDOWN:
				break;
			default:
				throw new MessageException("Invalid Type!", inMessage);
		}
		
		/* Processing */
		
		switch(inMessage.getType()){
		case DestinationMessage.DROPPASSENGERS:
			dest.dropPassengers(inMessage.getPassengersBoarded());
			outMessage = new DestinationMessage(DestinationMessage.ACK);
			break;
		case DestinationMessage.LEAVEPLANE:
			dest.leavePlane(inMessage.getPassengerId());
			outMessage = new DestinationMessage(DestinationMessage.ACK);
			break;
		case DestinationMessage.WAITARRIVAL:
			dest.waitArrival();
			outMessage = new DestinationMessage(DestinationMessage.ACK);
			break;
		case DestinationMessage.SHUTDOWN:
			DestinationServerRun.waitConnection = false;
			(((ClientProxy) (Thread.currentThread())).getScon()).setTimeout(10);
			outMessage = new DestinationMessage(DestinationMessage.ACK);
			break;
		}
		
		return outMessage;
	}
}
