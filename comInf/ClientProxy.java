package comInf;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * This data type defines the service agent thread for a Air Lift Problem solution 
 * that implements the type 2 client-server model (server replication)
 * Communication is based on message passing over sockets using the TCP protocol.
 */
public class ClientProxy<T> extends Thread{

	/**
	 * Threads launched counter
	 * @serialField nProxy
	 */
	private static int nProxy = 0;
	
	/**
	 * Communication channel
	 * @serialField sconni
	 */
	private ServerCom sconi;
	
	/**
	 * Interface of the different servers
	 * @serialField processMessage
	 */
	private ProcessMessage processMessage;
	
	/**
	 * Message Class Type(to be possible to use a generic type, constructing a string message to XML)
	 */
	private T tType;
	
	/**
	 * Constructs the
	 * @param sconi communication channel
	 * @param processMessage interface of the different servers
	 * @param type Message Class Type
	 */
	public ClientProxy(ServerCom sconi, ProcessMessage processMessage, T type){
		this.sconi = sconi;
		this.processMessage = processMessage;
		tType = type;
	}
	
	/**
	 * Life cycle of the service agent thread.
	 */
	@Override
	public void run(){
		Message inMessage = null,
				outMessage = null;
		String inString,
			   outString;
		
		
		inString = (String) sconi.readObject();
		
		// Using a generic type to construct a different message type
		Class<?> cl = null;
		String tmp = "comInf." + tType.getClass().getSimpleName();
		
		try {
			cl = Class.forName(tmp);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Class<?>[] paramTypes = {String.class};
		Constructor<?> cons = null;
		try {
			cons = cl.getConstructor(paramTypes);
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
		}
		
		// Constructing the message
		Object ar[] = {inString};
		Object theObject = null;
		try {
			theObject = cons.newInstance(ar);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {
			e1.printStackTrace();
		}
		
		// Different Message type (T)
		inMessage = (Message) theObject;
		
		try{
			outMessage = processMessage.processAndReply(inMessage);
		}catch(MessageException e){
			System.out.println("Thread " + getName () + ": " + e.getMessage () + "!");
			System.out.println(e.getMessageVal ().toString ());
			System.exit(1);
		}
		
		outString = outMessage.toXMLString();
		sconi.writeObject(outString);
		sconi.close();
	}
	
	/**
	 * Generating the instance identifier.
	 * @return instantiation identifier
	 */
	public static int getProxyId(){
		Class <?> cl = null;
		
		int proxyId;
		
		try{
			cl = Class.forName("comInf.ClientProxy");
		}catch(ClassNotFoundException e){
			System.out.println("The ClientProxy data type was not found!");
			e.printStackTrace();
			System.exit(1);
		}
		
		synchronized(cl){
			proxyId = nProxy;
			nProxy += 1;
		}
		
		return proxyId;
	}
	
	/**
	 * Getting the communication channel
	 * @return communication channel
	 */
	public ServerCom getScon(){
		return sconi;
	}
}
