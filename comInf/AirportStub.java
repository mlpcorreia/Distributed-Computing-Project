package comInf;

/**
 * This data type defines the stub to the Airport in a solution of the Air Lift Problem 
 * that implements the type 2 client-server model (server replication)
 */
public class AirportStub implements Airport{

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
	public AirportStub(String hostName, int portNumb){
		serverHostName = hostName;
		serverPortNumb = portNumb;
	}
	
	/**
	 *  Pilot signals that the plane is ready for boarding (service request)
	 */
	@Override
	public void signalReadyForBoarding() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		AirportMessage inMessage, outMessage;
		String inString, outString;
		
		 while (!con.open ())                                  // waits connection
	      { try
	        { Thread.currentThread ().sleep ((long) (10));
	        }
	        catch (InterruptedException e) {}
	      }
		 
		 outMessage = new AirportMessage(AirportMessage.SIGNALBOARD);
		 outString = outMessage.toXMLString();
		 con.writeObject(outString);
		 
		 inString = (String) con.readObject();
		 inMessage = new AirportMessage(inString);
		 if(inMessage.getType() != AirportMessage.ACK){
			 System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			 System.out.println(inMessage.toString ());
			 System.exit(1);
		 }
		 
		 con.close();
	}

	/**
	 * Pilot waits until the flight is ready to take off (service request)
	 * @return the number of passengers in the plane
	 */
	@Override
	public int waitUntilReadyToFlight() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		AirportMessage inMessage, outMessage;
		String inString, outString;
		
		while (!con.open ())                                  // waits connection
	     { try
	       { Thread.currentThread ().sleep ((long) (10));
	       }
	       catch (InterruptedException e) {}
	     }
		
		outMessage = new AirportMessage(AirportMessage.WAITREADYFLIGHT);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new AirportMessage(inString);
		if(inMessage.getType() != AirportMessage.WAITREADYFLIGHTREPLY){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		 
		con.close();
		
		return inMessage.getPassengersBoarded();
	}

	/**
	 * Hostess waits for the next flight (service request)
	 * @return boolean that indicates if there are more passengers to flight
	 */
	@Override
	public boolean waitForNextFlight() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		AirportMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new AirportMessage(AirportMessage.WAITNEXTFLIGHT);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new AirportMessage(inString);
		if(inMessage.getType() != AirportMessage.WAITNEXTFLIGHTREPLY){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString());
			System.exit(1);
		}
		
		con.close();
		
		return inMessage.getWaitingFlight();
	}

	/**
	 * Hostess waits for passengers to enter the queue (service request)
	 */
	@Override
	public void waitForPassenger() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		AirportMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new AirportMessage(AirportMessage.WAITPASSENGER);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new AirportMessage(inString);
		if(inMessage.getType() != AirportMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Passengers waits in queue until Hostess verifies the passport (service request)
	 * @param passengerId Passenger Identification
	 */
	@Override
	public void waitInQueue(int passengerId) {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		AirportMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new AirportMessage(AirportMessage.WAITINQUEUE, passengerId);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new AirportMessage(inString);
		if(inMessage.getType() != AirportMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Hostess waits until Passengers shows passport (service request)
	 * @return boolean that indicates if the flight is ready to take off
	 */
	@Override
	public boolean checkPassport() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		AirportMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new AirportMessage(AirportMessage.CHECKPASSPORT);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new AirportMessage(inString);
		if(inMessage.getType() != AirportMessage.CHECKPASSPORTREPLY){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString());
			System.exit(1);
		}
		
		con.close();
		
		return inMessage.getCheckPassport();
	}

	/**
	 * Hostess signals the pilot that the flight is ready to start (service request)
	 */
	@Override
	public void signalReadyToFlight() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		AirportMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new AirportMessage(AirportMessage.READYTOFLIGHT);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new AirportMessage(inString);
		if(inMessage.getType() != AirportMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Pilot checks if the flights are finished (service request)
	 * @return boolean that indicates if the flights are finished
	 */
	@Override
	public boolean isFinished() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		AirportMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new AirportMessage(AirportMessage.ISFINISHED);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new AirportMessage(inString);
		if(inMessage.getType() != AirportMessage.ISFINISHEDREPLY){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString());
			System.exit(1);
		}
		
		con.close();
		
		return inMessage.getIsFinished();
	}
	
	/**
	 * Shutdown the server (request the service)
	 */
	public void shutdown(){
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		AirportMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new AirportMessage(AirportMessage.SHUTDOWN);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new AirportMessage(inString);
		if(inMessage.getType() != AirportMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString());
			System.exit(1);
		}
		
		con.close();
	}
}
