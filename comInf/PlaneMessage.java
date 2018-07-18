package comInf;

import java.io.*;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * This data type defines the Plane messages that are exchanged between the clients and the server in a Air Lift Problem solution that implements the type 2 client-server model (server replication)
 * The communication itself is based on the exchange of Message objects on a TCP channel.
 */
public class PlaneMessage implements Serializable, Message{

	/**
	 * Serialization Key
	 * @serialField serialVersionUID
	 */
	private static final long serialVersionUID = 1003L;
	
	/*Message Types*/
	
	/**
	 * Operation executed with success (answer sent by the server)
	 */
	public static final int ACK = 1;
	
	/**
	 * Pilot is flying the plane (operation requested by the client)
	 */
	public static final int FLIGHT = 14;
	
	/**
	 * Passenger is waiting in plane until arrival (operation requested by the client)
	 */
	public static final int WAITDESTINATION = 15;
	
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
	 * Pilot flying to the destination and back variable
	 */
	private boolean flying = false;
	
	/**
	 * Constructs a default PlaneMessage (so that can be used in the ClientProxy)
	 */
	public PlaneMessage(){
	}
	
	/**
	 * Constructs a PlaneMessage (Type 1)
	 * @param type Message type
	 */
	public PlaneMessage(int type){
		msgType = type;
	}
	
	/**
	 * Constructs a PlaneMessage (Type 2)
	 * @param type Message type
	 * @param tmp_int Passenger Identification
	 */
	public PlaneMessage(int type, int tmp_int){
		msgType = type;
		passengerId = tmp_int;
	}
	
	/**
	 * Constructs a PlaneMessage (Type 3)
	 * @param type Message type
	 * @param tmp_bol temporary boolean
	 */
	public PlaneMessage(int type, boolean tmp_bol){
		msgType = type;
		flying = tmp_bol;
	}
	
	/**
	 * Constructs a PlaneMessage (Type 4)
	 * @param stringXML message in XML format
	 */
	public PlaneMessage(String stringXML){
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
	 * Conversion to an XML string
	 * @return string with the message description
	 */
	@Override
	public String toXMLString() {
		return ("<Message>"
				+ "<Type>" + msgType + "</Type>"
				+ "<PassengerId>" + passengerId + "</PassengerId>"
				+ "<Flying>" + flying + "</Flying>"
				+ "</Message>");
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
	 * Get flying state
	 * @return boolean that states if the pilot is flying back or to the destination
	 */
	@Override
	public boolean getFlying(){
		return flying;
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
			flying = false;
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
				if(elemName.equals("Flying")){
					flying = new Boolean(elem);
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
