package comInf;

/**
 * This data type defines the stub to the Destination in a solution of the Air Lift Problem 
 * that implements the type 2 client-server model (server replication)
 */
public class DestinationStub implements Destination {

	/**
	 * Name of the computer system where the server is located
	 */
	private String serverHostName = null;
	
	/**
	 * Server listening port number
	 */
	private int serverPortNumb;
	
	/**
	 * Construct a stub to AirportImpl
	 * @param hostName Name of the computer system where the server is located
	 * @param portNumb Server listening port number
	 */
	public DestinationStub(String hostName, int portNumb){
		serverHostName = hostName;
		serverPortNumb = portNumb;
	}
	
	/**
	 * Pilot drops passengers at Destination (service request)
	 * @param passengersOnPlane Number of passengers in the plane
	 */
	@Override
	public void dropPassengers(int passengersOnPlane) {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		DestinationMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new DestinationMessage(DestinationMessage.DROPPASSENGERS, passengersOnPlane);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new DestinationMessage(inString);
		if(inMessage.getType() != DestinationMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Passengers leave the plane (service request)
	 * @param passengerId Passenger Identification
	 */
	@Override
	public void leavePlane(int passengerId) {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		DestinationMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new DestinationMessage(DestinationMessage.LEAVEPLANE, passengerId);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new DestinationMessage(inString);
		if(inMessage.getType() != DestinationMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}
	
	/**
	 * Block the passenger in the plane until arrival
	 */
	public void waitArrival(){
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		DestinationMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new DestinationMessage(DestinationMessage.WAITARRIVAL);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new DestinationMessage(inString);
		if(inMessage.getType() != DestinationMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}
	
	/**
	 * Shutdown the server (request the service)
	 */
	public void shutdown(){
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		DestinationMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new DestinationMessage(DestinationMessage.SHUTDOWN);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new DestinationMessage(inString);
		if(inMessage.getType() != DestinationMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString());
			System.exit(1);
		}
		
		con.close();
	}
}
