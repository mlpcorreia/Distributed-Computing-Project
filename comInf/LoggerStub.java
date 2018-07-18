package comInf;

/**
 *This data type defines the stub to the Logger in a solution of the Air Lift Problem 
 * that implements the type 2 client-server model (server replication)
 */
public class LoggerStub implements Logger {

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
	public LoggerStub(String hostName, int portNumb){
		serverHostName = hostName;
		serverPortNumb = portNumb;
	}

	/**
	 * Set the pilot state and save it in the log file
	 * @param state Pilot State
	 */
	@Override
	public void setStatePilot(PilotStates state) {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		LoggerMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new LoggerMessage(LoggerMessage.SETPILOTSTATE, state);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new LoggerMessage(inString);
		if(inMessage.getType() != LoggerMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Set the hostess state and save it in the log file
	 * @param state Hostess State
	 */
	@Override
	public void setStateHostess(HostessStates state) {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		LoggerMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new LoggerMessage(LoggerMessage.SETHOSTESSSTATE, state);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new LoggerMessage(inString);
		if(inMessage.getType() != LoggerMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Set the passenger state and save it in the log file
	 * @param id Passenger Identification
	 * @param state Passenger State
	 */
	@Override
	public void setStatePassenger(int id, PassengerStates state) {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		LoggerMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new LoggerMessage(LoggerMessage.SETPASSENGERSTATE, state, id);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new LoggerMessage(inString);
		if(inMessage.getType() != LoggerMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Saves in the log file that the boarding started
	 */
	@Override
	public void boardingStarted() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		LoggerMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new LoggerMessage(LoggerMessage.BOARDINGSTARTED);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new LoggerMessage(inString);
		if(inMessage.getType() != LoggerMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Saves in the log file the passenger that got is passport checked by the hostess
	 * @param id Passenger Identification
	 */
	@Override
	public void passengerChecked(int id) {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		LoggerMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new LoggerMessage(LoggerMessage.PASSENGERCHECKED, id);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new LoggerMessage(inString);
		if(inMessage.getType() != LoggerMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Saves in the log file the number of passengers that left in the plane to the destination
	 */
	@Override
	public void departedWith() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		LoggerMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new LoggerMessage(LoggerMessage.DEPARTED);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new LoggerMessage(inString);
		if(inMessage.getType() != LoggerMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Saves in the log file that the plane arrived at the destination
	 */
	@Override
	public void arrived() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		LoggerMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new LoggerMessage(LoggerMessage.ARRIVED);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new LoggerMessage(inString);
		if(inMessage.getType() != LoggerMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Saves in the log file that the plane is returning to the airport
	 */
	@Override
	public void returning() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		LoggerMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new LoggerMessage(LoggerMessage.RETURNING);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new LoggerMessage(inString);
		if(inMessage.getType() != LoggerMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString ());
			System.exit(1);
		}
		
		con.close();
	}

	/**
	 * Saves in the log file the final results of the Air Lift problem and closes the buffer 
	 */
	@Override
	public void logEnd() {
		ClientCom con = new ClientCom(serverHostName, serverPortNumb);
		LoggerMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new LoggerMessage(LoggerMessage.LOGEND);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new LoggerMessage(inString);
		if(inMessage.getType() != LoggerMessage.ACK){
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
		LoggerMessage inMessage, outMessage;
		String inString, outString;
		
		while(!con.open()){
			try{
				Thread.currentThread().sleep((long) (10));
			}catch(InterruptedException e){}
		}
		
		outMessage = new LoggerMessage(LoggerMessage.SHUTDOWN);
		outString = outMessage.toXMLString();
		con.writeObject(outString);
		 
		inString = (String) con.readObject();
		inMessage = new LoggerMessage(inString);
		if(inMessage.getType() != LoggerMessage.ACK){
			System.out.println("Thread " + Thread.currentThread ().getName () + ": Invalid Type!");
			System.out.println(inMessage.toString());
			System.exit(1);
		}
		
		con.close();
	}
}
