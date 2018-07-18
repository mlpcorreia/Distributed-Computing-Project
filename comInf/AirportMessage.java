package comInf;

import java.io.*;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * This data type defines the Airport messages that are exchanged between the clients and the server in a Air Lift Problem solution that implements the type 2 client-server model (server replication)
 * The communication itself is based on the exchange of Message objects on a TCP channel.
 */
public class AirportMessage implements Serializable, Message{

	/**
	 * Serialization Key
	 * @serialField serialVersionUID
	 */
	private static final long serialVersionUID = 1002L;
	
	/*Message Types*/
	
	/**
	 * Operation executed with success (answer sent by the server)
	 */
	public static final int ACK = 1;
	
	/**
	 * Pilot signals plane is ready for boarding (operation requested by the client)
	 */
	public static final int SIGNALBOARD = 2;
	
	/**
	 * Pilot waits until the flight is ready to start (operation requested by the client)
	 */
	public static final int WAITREADYFLIGHT = 3;
	
	/**
	 * Pilot waiting done (answer sent by the server)
	 */
	public static final int WAITREADYFLIGHTREPLY = 4;
	
	/**
	 * Hostess waits for the next flight (operation requested by the client)
	 */
	public static final int WAITNEXTFLIGHT = 5;
	
	/**
	 * Hostess waiting done (answer sent by the server)
	 */
	public static final int WAITNEXTFLIGHTREPLY = 6;
	
	/**
	 * Hostess waits for passengers enter the queue (operation requested by the client)
	 */
	public static final int WAITPASSENGER = 7;
	
	/**
	 * Passenger waits in the queue (operation requested by the client)
	 */
	public static final int WAITINQUEUE = 8;
	
	/**
	 * Hostess checks the passport of the passenger (operation requested by the client)
	 */
	public static final int CHECKPASSPORT = 9;
	
	/**
	 * Hostess checked the passport (answer sent by the server)
	 */
	public static final int CHECKPASSPORTREPLY = 10;
	
	/**
	 * Hostess signals that the flight is ready to flight
	 */
	public static final int READYTOFLIGHT = 11;
	
	/**
	 * Pilot checks if there is more flights to do (operation requested by the client)
	 */
	public static final int ISFINISHED = 12;
	
	/**
	 * Pilot receives the information if there are more flights to do (answer sent by the server)
	 */
	public static final int ISFINISHEDREPLY = 13;
	
	/**
	 * Shutdown the server (operation requested by the client)
	 */
	public static final int SHUTDOWN = 28;
	
	/* Message Fields*/
	
	/**
	 * Message Type
	 */
	private int msgType = -1;
	
	/**
	 * Passenger Identification
	 */
	private int passengerId = -1;
	
	/**
	 * Passengers Boarded on Plane
	 */
	private int passengersBoarded = -1;
	
	/**
	 * Hostess waiting for flights
	 */
	private boolean waitingFlight = false;
	
	/**
	 * Check passport variable (done)
	 */
	private boolean checkPassport = false;
	
	/**
	 * Flights finished variable
	 */
	private boolean isFinished = false;
	
	/**
	 * Constructs a default AirportMessage (so that can be used in the ClientProxy)
	 */
	public AirportMessage(){
	}
	
	/**
	 * Constructs a AirportMessage (Type 1)
	 * @param type Message type
	 */
	public AirportMessage(int type){
		msgType = type;
	}
	
	/**
	 * Constructs a AirportMessage (Type 2)
	 * @param type Message type
	 * @param tmp_int Passenger Identification or Number of passengers in plane
	 */
	public AirportMessage(int type, int tmp_int){
		msgType = type;
		if(AirportMessage.WAITREADYFLIGHTREPLY == type)
			passengersBoarded = tmp_int;
		else
			passengerId = tmp_int;
	}
	
	/**
	 * Constructs a AirportMessage (Type 3)
	 * @param type Message type
	 * @param tmp_bol temporary boolean
	 */
	public AirportMessage(int type, boolean tmp_bol){
		msgType = type;
		if(AirportMessage.WAITNEXTFLIGHTREPLY == type)
			waitingFlight = tmp_bol;
		else if(AirportMessage.CHECKPASSPORTREPLY == type)
			checkPassport = tmp_bol;
		else if(AirportMessage.ISFINISHEDREPLY == type)
			isFinished = tmp_bol;
	}
	
	/**
	 * Constructs a AirportMessage (Type 4)
	 * @param stringXML message in XML format
	 */
	public AirportMessage(String stringXML){
		InputSource in = new InputSource(new StringReader(stringXML));
		SAXParserFactory spf;
		SAXParser saxParser = null;
		
		spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(false);
		spf.setValidating(false);
		try{
			saxParser = spf.newSAXParser();
			saxParser.parse(in, new HandlerXML());
		}catch(ParserConfigurationException e){
			System.out.println("Error in parser instantiation (configuration):" + e.getMessage() + "!");
			System.exit(1);
		}
		catch(SAXException e){
			System.out.println("Error in parser instantiation (SAX):" + e.getMessage() + "!");
			System.exit(1);
		}
		catch(IOException e){
			System.out.println("Error in parser execution (SAX):" + e.getMessage() + "!");
			System.exit(1);
		}
	}
	
	/**
	 * Get Message type 
	 * @return msgType
	 */
	public int getType(){
		return msgType;
	}
	
	/**
	 * Get Passengers on Plane
	 * @return passengersBoarded 
	 */
	public int getPassengersBoarded(){
		return passengersBoarded;
	}
	
	/**
	 * Get boolean that states if there is more flights for the hostess
	 * @return waitingFlight
	 */
	public boolean getWaitingFlight(){
		return waitingFlight;
	}
	
	/**
	 * Get boolean that states if the boarding is finished
	 * @return checkPassport
	 */
	public boolean getCheckPassport(){
		return checkPassport;
	}
	
	/**
	 * Get passenger identification
	 * @return passenger id
	 */
	public int getPassengerId(){
		return passengerId;
	}

	/**
	 * Get boolean that states if all the flights are done
	 * @return isFinished
	 */
	public boolean getIsFinished(){
		return isFinished;
	}
	
	/**
	 * Conversion to an XML string
	 * @return string with the message description
	 */
	public String toXMLString(){
		return ("<AirportMessage>"
				+ "<Type>" + msgType + "</Type>"
				+ "<PassengerId>" + passengerId + "</PassengerId>"
				+ "<PassengersBoarded>" + passengersBoarded + "</PassengersBoarded>"
				+ "<WaitingFlight>" + waitingFlight + "</WaitingFlight>"
				+ "<CheckPassport>" + checkPassport + "</CheckPassport>"
				+ "<IsFinished>" + isFinished + "</IsFinished>"
				+ "</AirportMessage>");
	}
	
	/**
	 * This data type defines the handler that handles the events that occur during the parsing of an XML string.
	 */
	private class HandlerXML extends DefaultHandler{
		
		/**
		 * Parsing the XML string in progress
		 * @serialField parsing
		 */
		private boolean parsing;
		
		/**
		 * Parsing of an element in course
		 * @serialField element
		 */
		private boolean element;
		
		/**
		 * Element name in processing
		 * @serialField elemName
		 */
		private String elemName;
		
		/**
		 * Beginning of XML string processing
		 */
		@Override
		public void startDocument() throws SAXException{
			msgType = -1;
			passengerId = -1;
			passengersBoarded = -1;
			waitingFlight = false;
			checkPassport = false;
			isFinished = false;
			parsing = true;
		}
		
		/**
		 * Ending the XML string processing
		 */
		@Override
		public void endDocument() throws SAXException{
			parsing = false;
		}
		
		/**
		 * Beginning of processing an XML string element
		 */
		@Override
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException{
			element = parsing;
			if(parsing)
				elemName = qName;
		}
		
		/**	
		 * Ending of processing an XML string element
		 */
		@Override
		public void endElement(String namespaceURI, String localName, String qName) throws SAXException{
			element = false;
			elemName = null;
		}
		
		/**
		 * Processing the content of an XML string element
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException{
			String elem;
			
			elem = new String(ch, start, length);
			if(parsing && element){
				if(elemName.equals("Type")){
					msgType = new Integer(elem);
					return;
				}
				if(elemName.equals("PassengerId")){
					passengerId = new Integer(elem);
					return;
				}
				if(elemName.equals("PassengersBoarded")){
					passengersBoarded = new Integer(elem);
					return;
				}
				if(elemName.equals("WaitingFlight")){
					waitingFlight = new Boolean(elem);
					return;
				}
				if(elemName.equals("CheckPassport")){
					checkPassport = new Boolean(elem);
					return;
				}
				if(elemName.equals("IsFinished")){
					isFinished = new Boolean(elem);
					return;
				}
			}
		}
	}

	@Override
	public boolean getFlying(){
		return false;
	}
	
	@Override
	public PilotStates getPilotState() {
		return null;
	}

	@Override
	public HostessStates getHostessState() {
		return null;
	}

	@Override
	public PassengerStates getPassengerState() {
		return null;
	}
}
