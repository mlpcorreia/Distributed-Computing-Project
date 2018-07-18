package comInf;

import java.io.*;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * This data type defines the Logger messages that are exchanged between the clients and the server in a Air Lift Problem solution that implements the type 2 client-server model (server replication)
 * The communication itself is based on the exchange of Message objects on a TCP channel.
 */
public class LoggerMessage implements Serializable, Message{

	/**
	 * Serialization Key
	 * @serialField serialVersionUID
	 */
	private static final long serialVersionUID = 1005L;
	
	/*Message Types*/
	
	/**
	 * Operation executed with success (answer sent by the server)
	 */
	public static final int ACK = 1;
	
	/**
	 * Pilot state is set in the log file (operation requested by the client)
	 */
	public static final int SETPILOTSTATE = 19;
	
	/**
	 * Hostess state is set in the log file (operation requested by the client)
	 */
	public static final int SETHOSTESSSTATE = 20;
	
	/**
	 * Passenger state is set in the log file (operation requested by the client)
	 */
	public static final int SETPASSENGERSTATE = 21;
	
	/**
	 * Its saved in the log file that the boarding started (operation requested by the client)
	 */
	public static final int BOARDINGSTARTED = 22;
	
	/**
	 * Its saved in the log file the passenger that the hostess checked (operation requested by the client)
	 */
	public static final int PASSENGERCHECKED = 23;
	
	/**
	 * Its saved in the log file the passengers that left in the plane (operation requested by the client)
	 */
	public static final int DEPARTED = 24;
	
	/**
	 * Its saved in the log file that the plane arrived at the destination (operation requested by the client)
	 */
	public static final int ARRIVED = 25;
	
	/**
	 * Its saved in the log file that the plane is returning to the airport (operation requested by the client)
	 */
	public static final int RETURNING = 26;
	
	/**
	 * Its saved the final results of the Air Lift problem and the log closed (operation requested by the client)
	 */
	public static final int LOGEND = 27;
	
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
	 * Pilot State variable
	 */
	private PilotStates pilotState = PilotStates.FB;
	
	/**
	 * Hostess state variable
	 */
	private HostessStates hostessState = HostessStates.WFF;
	
	/**
	 * Passenger state variable
	 */
	private PassengerStates passengerState = PassengerStates.GTA;
	
	/**
	 * Constructs a default LoggerMessage (so that can be used in the ClientProxy)
	 */
	public LoggerMessage(){
	}
	
	/**
	 * Constructs a LoggerMessage (Type 1)
	 * @param type Message type
	 */
	public LoggerMessage(int type){
		msgType = type;
	}
	
	/**
	 * Constructs a LoggerMessage (Type 2)
	 * @param type Message type
	 * @param tmp_int Passenger Identification
	 */
	public LoggerMessage(int type, int tmp_int){
		msgType = type;
		passengerId = tmp_int;
	}
	
	/**
	 * Constructs a LoggerMessage (Type 3)
	 * @param type Message type
	 * @param state pilotState
	 */
	public LoggerMessage(int type, PilotStates state){
		msgType = type;
		pilotState = state;
	}
	
	/**
	 * Constructs a LoggerMessage (Type 4)
	 * @param type Message type
	 * @param state hostessState
	 */
	public LoggerMessage(int type, HostessStates state){
		msgType = type;
		hostessState = state;
	}
	
	/**
	 * Constructs a LoggerMessage (Type 5)
	 * @param type Message type
	 * @param state passengerState
	 * @param id Passenger Identification
	 */
	public LoggerMessage(int type, PassengerStates state, int id){
		msgType = type;
		passengerState = state;
		passengerId = id;
	}
	
	/**
	 * Constructs a LoggerMessage (Type 6)
	 * @param stringXML message in XML format
	 */
	public LoggerMessage(String stringXML){
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
	@Override
	public int getType() {
		return msgType;
	}

	/**
	 * Get passenger identification
	 * @return passenger id
	 */
	@Override
	public int getPassengerId() {
		return passengerId;
	}
	
	/**
	 * Get the pilot state at a given moment
	 * @return the pilot state
	 */
	public PilotStates getPilotState(){
		return pilotState;
	}
	
	/**
	 * Get the hostess state at a given moment
	 * @return the hostess state
	 */
	public HostessStates getHostessState(){
		return hostessState;
	}
	
	/**
	 * Get the passenger state at a given moment
	 * @return the passenger state
	 */
	public PassengerStates getPassengerState(){
		return passengerState;
	}
	
	/**
	 * Conversion to an XML string
	 * @return string with the message description
	 */
	@Override
	public String toXMLString() {
		return ("<Message>"
				+ "<Type>" + msgType + "</Type>"
				+ "<PassengerId>" + passengerId + "</PassengerId>"
				+ "<PilotState>" + pilotState + "</PilotState>"
				+ "<HostessState>" + hostessState + "</HostessState>"
				+ "<PassengerState>" + passengerState + "</PassengerState>"
				+ "</Message>");
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
			pilotState = PilotStates.FB;
			hostessState = HostessStates.WFF;
			passengerState = PassengerStates.GTA;
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
				if(elemName.equals("PilotState")){
					pilotState = PilotStates.valueOf(elem);
					return;
				}
				if(elemName.equals("HostessState")){
					hostessState = HostessStates.valueOf(elem);
					return;
				}
				if(elemName.equals("PassengerState")){
					passengerState = PassengerStates.valueOf(elem);
					return;
				}
			}
		}
	}
	
	@Override
	public int getPassengersBoarded() {
		return 0;
	}

	@Override
	public boolean getFlying() {
		return false;
	}
}
