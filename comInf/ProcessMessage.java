package comInf;

/**
 * Process Message Interface
 */
public interface ProcessMessage {

	public Message processAndReply (Message inMessage) throws MessageException;
}
