package comInf;

/**
 * This data type defines the stub to the Plane in a solution of the Air Lift Problem 
 * that implements the type 2 client-server model (server replication)
 */
public class PlaneStub implements Plane{

	/**
	 * Name of the computer system where the server is located
	 * @serialField serverHostName
	 */
	private String serverHostName = null;
	
	/**
	 * Server listening port number
	 * @serialField serverPortNumb
	 */
	private int serverPortNumb;
	
	/**
	 * Construct a stub to AirportImpl
	 * @param hostName Name of the computer system where the server is located
	 * @param portNumb Server listening port number
	 */
	public PlaneStub(String hostName, int portNumb){
		serverHostName = hostName;
		serverPortNumb = portNumb;
	}
	
	/**
	 * Pilot flying to destination and back to the Airport (service request)
	 * @param state Pilot flying state
	 */
	@Override
	public void flight(boolean state) {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		PlaneMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new PlaneMessage(PlaneMessage.FLIGHT, state);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new PlaneMessage(inString);
		if(inMessage.getType() != PlaneMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Passengers waits in plane until arrival (service request)
	 * @param passengerId Passenger Identification
	 */
	@Override
	public void waitUntilDestination(int passengerId) {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		Message inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new PlaneMessage(PlaneMessage.WAITDESTINATION, passengerId);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new PlaneMessage(inString);
		if(inMessage.getType() != PlaneMessage.ACK){
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
		PlaneMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new PlaneMessage(PlaneMessage.SHUTDOWN);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new PlaneMessage(inString);
		if(inMessage.getType() != PlaneMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString());
			System.exit(1);
		}
		
		con.close();
	}
}
