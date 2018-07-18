package serverSide;

import java.net.SocketTimeoutException;

import comInf.DestinationStub;
import comInf.ClientProxy;
import comInf.Destination;
import comInf.LoggerStub;
import comInf.PlaneMessage;
import comInf.ServerCom;

/**
 * This data type simulates a server-side solution of the Air Lift Problem
 * that implements the type 2 client-server model (server replication)
 */
public class PlaneServerRun {

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
	 * @param args Input arguments
	 */
	public static void main(String[] args) {
		PlaneImpl plane;
		PlaneInterface planeInterface;
		Destination destStub;
		LoggerStub logStub;
		ServerCom scon, sconi;
		ClientProxy<PlaneMessage> cliProxy;
		
		/* Service establishment */
		
		scon = new ServerCom(portNumb);
		scon.start();
		logStub = new LoggerStub("host", port);
		destStub = new DestinationStub("host", port);
		plane = new PlaneImpl(logStub, destStub);
		planeInterface = new PlaneInterface(plane);
		System.out.println("Service executed!");
		System.out.println("Server is listening...");
		
		/* Order processing */
		
		waitConnection = true;
		while(waitConnection){
			try{
				sconi = scon.accept();
				cliProxy = new ClientProxy<PlaneMessage>(sconi, planeInterface, new PlaneMessage());
				cliProxy.start();
			}catch(SocketTimeoutException e){}
		}
		
		scon.end();
		System.out.println("Server deactivated!");
	}
}
