package serverSide;

import comInf.AirportMessage;
import comInf.ClientProxy;
import comInf.Message;
import comInf.MessageException;
import comInf.ProcessMessage;

/**
 * This data type defines the interface to the Airport in a solution of the Air Lift Problem 
 * that implements the type 2 client-server model (server replication)
 */
public class AirportInterface implements ProcessMessage {

	/**
	 * Airport (represents the service to be provided)
	 * @serialField airport
	 */
	private AirportImpl airport;
	
	/**
	 * Constructs the interface to the airport
	 * @param airport Airport Object
	 */
	public AirportInterface(AirportImpl airport){
		this.airport = airport;
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
		AirportMessage outMessage = null;
		
		/* Validating the received message*/
		
		switch(inMessage.getType()){
			case AirportMessage.SIGNALBOARD:
				break;
			case AirportMessage.WAITREADYFLIGHT:
				break;
			case AirportMessage.WAITNEXTFLIGHT:
				break;
			case AirportMessage.WAITPASSENGER:
				break;
			case AirportMessage.WAITINQUEUE:
				if((inMessage.getPassengerId() < 0) || (inMessage.getPassengerId() > 21))
					throw new MessageException("Passenger Id invalid!", inMessage);
				break;
			case AirportMessage.CHECKPASSPORT:
				break;
			case AirportMessage.READYTOFLIGHT:
				break;
			case AirportMessage.ISFINISHED:
				break;
			case AirportMessage.SHUTDOWN:
				break;
			default:
				throw new MessageException ("Invalid Type!", inMessage);
		}
		
		/* Processing */
		
		switch(inMessage.getType()){
			case AirportMessage.SIGNALBOARD:
				airport.signalReadyForBoarding();
				outMessage = new AirportMessage(AirportMessage.ACK);
				break;
			case AirportMessage.WAITREADYFLIGHT:
				int passengersBoarded = airport.waitUntilReadyToFlight();
				outMessage = new AirportMessage(AirportMessage.WAITREADYFLIGHTREPLY, passengersBoarded);
				break;
			case AirportMessage.WAITNEXTFLIGHT:
				boolean waitingFlight = airport.waitForNextFlight();
				outMessage = new AirportMessage(AirportMessage.WAITNEXTFLIGHTREPLY, waitingFlight);
				break;
			case AirportMessage.WAITPASSENGER:
				airport.waitForPassenger();
				outMessage = new AirportMessage(AirportMessage.ACK);
				break;
			case AirportMessage.WAITINQUEUE:
				airport.waitInQueue(inMessage.getPassengerId());
				outMessage = new AirportMessage(AirportMessage.ACK);
				break;
			case AirportMessage.CHECKPASSPORT:
				boolean checkPassport = airport.checkPassport();
				outMessage = new AirportMessage(AirportMessage.CHECKPASSPORTREPLY, checkPassport);
				break;
			case AirportMessage.READYTOFLIGHT:
				airport.signalReadyToFlight();
				outMessage = new AirportMessage(AirportMessage.ACK);
				break;
			case AirportMessage.ISFINISHED:
				boolean isFinished = airport.isFinished();
				outMessage = new AirportMessage(AirportMessage.ISFINISHEDREPLY, isFinished);
				break;
			case AirportMessage.SHUTDOWN:
				AirportServerRun.waitConnection = false;
				(((ClientProxy) (Thread.currentThread())).getScon()).setTimeout(10);
				outMessage = new AirportMessage(AirportMessage.ACK);
				break;
		}
		
		return outMessage;
	}

}
