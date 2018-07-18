package comInf;

/**
 * This data type defines an exception that is thrown if the message is invalid.
 */
public class MessageException extends Exception{

	/**
	 * Message that cause the exception
	 */
	private Message msg;
	
	/**
	 * Constructs a MessageException
	 * @param errorMessage Text indicating the error condition
	 * @param msg message Message that is at the origin of the exception
	 */
	public MessageException(String errorMessage, Message msg){
		super(errorMessage);
		this.msg = msg;	
	}
	
	/**
	 * Getting the message that caused the exception
	 * @return message
	 */
	public Message getMessageVal(){
		return msg;
	}
}
