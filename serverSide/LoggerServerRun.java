package serverSide;

import java.net.SocketTimeoutException;

import comInf.ClientProxy;
import comInf.LoggerMessage;
import comInf.ServerCom;

/**
 * This data type simulates a server-side solution of the Air Lift Problem
 * that implements the type 2 client-server model (server replication)
 */
public class LoggerServerRun {
	
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
		LoggerImpl log;
		LoggerInterface logInterface;
		ServerCom scon, sconi;
		ClientProxy<LoggerMessage> cliProxy;
		
		/* Service establishment */
		
		scon = new ServerCom(portNumb);
		scon.start();
		log = new LoggerImpl("log.txt");
		logInterface = new LoggerInterface(log);
		System.out.println("Service executed!");
		System.out.println("Server is listening...");
		
		/* Order processing */
		
		waitConnection = true;
		while(waitConnection){
			try{
				sconi = scon.accept();
				cliProxy = new ClientProxy<LoggerMessage>(sconi, logInterface, new LoggerMessage());
				cliProxy.start();
			}catch(SocketTimeoutException e){}
		}
		scon.end();
		System.out.println("Server deactivated!");
	}
}
