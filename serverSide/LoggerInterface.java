package serverSide;

import comInf.ClientProxy;
import comInf.LoggerMessage;
import comInf.Message;
import comInf.MessageException;
import comInf.ProcessMessage;

/**
 * This data type defines the interface to the Logger in a solution of the Air Lift Problem 
 * that implements the type 2 client-server model (server replication)
 */
public class LoggerInterface implements ProcessMessage {

	/**
	 * Logger (represents the service to be provided)
	 * @serialField log
	 */
	private LoggerImpl log;
	
	/**
	 * Constructs the interface to the Logger
	 * @param log Logger Implementation
	 */
	public LoggerInterface(LoggerImpl log){
		this.log = log;
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
		LoggerMessage outMessage = null;
		
		/* Validating the received message*/
		
		switch(inMessage.getType()){
			case LoggerMessage.SETPILOTSTATE:
				break;
			case LoggerMessage.SETHOSTESSSTATE:
				break;
			case LoggerMessage.SETPASSENGERSTATE:
				if((inMessage.getPassengerId() < 0) || (inMessage.getPassengerId() > 21))
					throw new MessageException("Passenger Id invalid!", inMessage);
				break;
			case LoggerMessage.BOARDINGSTARTED:
				break;
			case LoggerMessage.PASSENGERCHECKED:
				if((inMessage.getPassengerId() < 0) || (inMessage.getPassengerId() > 21))
					throw new MessageException("Passenger Id invalid!", inMessage);
				break;
			case LoggerMessage.DEPARTED:
				break;
			case LoggerMessage.ARRIVED:
				break;
			case LoggerMessage.RETURNING:
				break;
			case LoggerMessage.LOGEND:
				break;
			case LoggerMessage.SHUTDOWN:
				break;
		default:
			throw new MessageException("Invalid Type!", inMessage);	
		}
		
		/* Processing */
		
		switch(inMessage.getType()){
		case LoggerMessage.SETPILOTSTATE:
			log.setStatePilot(inMessage.getPilotState());
			outMessage = new LoggerMessage(LoggerMessage.ACK);
			break;
		case LoggerMessage.SETHOSTESSSTATE:
			log.setStateHostess(inMessage.getHostessState());
			outMessage = new LoggerMessage(LoggerMessage.ACK);
			break;
		case LoggerMessage.SETPASSENGERSTATE:
			log.setStatePassenger(inMessage.getPassengerId(), inMessage.getPassengerState());
			outMessage = new LoggerMessage(LoggerMessage.ACK);
			break;
		case LoggerMessage.BOARDINGSTARTED:
			log.boardingStarted();
			outMessage = new LoggerMessage(LoggerMessage.ACK);
			break;
		case LoggerMessage.PASSENGERCHECKED:
			log.passengerChecked(inMessage.getPassengerId());
			outMessage = new LoggerMessage(LoggerMessage.ACK);
			break;
		case LoggerMessage.DEPARTED:
			log.departedWith();
			outMessage = new LoggerMessage(LoggerMessage.ACK);
			break;
		case LoggerMessage.ARRIVED:
			log.arrived();
			outMessage = new LoggerMessage(LoggerMessage.ACK);
			break;
		case LoggerMessage.RETURNING:
			log.returning();
			outMessage = new LoggerMessage(LoggerMessage.ACK);
			break;
		case LoggerMessage.LOGEND:
			log.logEnd();
			outMessage = new LoggerMessage(LoggerMessage.ACK);
			break;
		case LoggerMessage.SHUTDOWN:
			LoggerServerRun.waitConnection = false;
			(((ClientProxy) (Thread.currentThread())).getScon()).setTimeout(10);
			outMessage = new LoggerMessage(LoggerMessage.ACK);
			break;
		}
		
		return outMessage;
	}
}
