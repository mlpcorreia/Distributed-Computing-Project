package serverSide;

import java.net.SocketTimeoutException;

import comInf.AirportMessage;
import comInf.ClientProxy;
import comInf.LoggerStub;
import comInf.ServerCom;

/**
 * This data type simulates a server-side solution of the Air Lift Problem
 * that implements the type 2 client-server model (server replication)
 */
public class AirportServerRun {

	/**
	 * Number of the listening port of the service to be provided
	 */
	public static final int portNumb = 1;
	
	/**
	 * Connection variable open or closed
	 */
	public static boolean waitConnection;
	
	/**
	 * Main Program
	 */
	public static void main(String[] args) {
		AirportImpl airport;
		AirportInterface airportInterface;
		LoggerStub logStub;
		ServerCom scon, sconi;
		ClientProxy<AirportMessage> cliProxy;
		
		/* Service establishment */
		
		scon = new ServerCom(portNumb);
		scon.start();
		logStub = new LoggerStub("host", port);
		airport = new AirportImpl(logStub);
		airportInterface = new AirportInterface(airport);
		System.out.println("Service executed!");
		System.out.println("Server is listening...");
		
		/* Order processing */
		
		waitConnection = true;
		while(waitConnection){
			try{
				sconi = scon.accept();
				cliProxy = new ClientProxy<AirportMessage>(sconi, airportInterface, new AirportMessage());
				cliProxy.start();
			}catch(SocketTimeoutException e){}
		}
		scon.end();
		System.out.println("Server deactivated!");
		
		/* Logger Shutdown Order */
		
		logStub.shutdown();
	}
}
