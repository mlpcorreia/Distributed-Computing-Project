package comInf;

import java.io.*;
import java.net.*;

/**
 * This data type implements the client-side communication channel for TCP / IP-based communication.
 * The data transfer is based in objects, one object at a time.
 */

public class ClientCom {

	/**
	 * Communication Socket
	 * @serialField commSocket
	 */
	private Socket commSocket = null;
	
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
	 * Input Stream of the communication channel
	 * @serialField in
	 */
	private ObjectInputStream in = null;
	
	/**
	 * Output Stream of the communication channel
	 * @serialField out
	 */
	private ObjectOutputStream out = null;
	
	/**
	 * Construct a communication channel
	 * @param hostName Name of the computer system where the server is located
	 * @param portNumb Server listening port number
	 */
	public ClientCom(String hostName, int portNumb){
		serverHostName = hostName;
		serverPortNumb = portNumb;
	}
	
	/**
	 * Opening the communication channel
	 * Instantiation of a communication socket and its association with the server address.
	 * @return boolean if the opening of the communication channel was successful
	 */
	public boolean open(){
		boolean success = true;
		SocketAddress serverAddress = new InetSocketAddress(serverHostName, serverPortNumb);
		
		try{
			commSocket = new Socket();
			commSocket.connect(serverAddress);
		}catch(UnknownHostException e){
			System.out.println(Thread.currentThread ().getName () +
                    " - the name of the computer system where the server is located is unknown: " +
                    serverHostName + "!");
			e.printStackTrace ();
	        System.exit (1);
		}
		catch (NoRouteToHostException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - the name of the computer system where the server is located is unattainable: " +
	                                 serverHostName + "!");
	        e.printStackTrace ();
	        System.exit (1);
	      }
	      catch (ConnectException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - the server doesn't responde in: " + serverHostName + "." + serverPortNumb + "!");
	        if (e.getMessage ().equals ("Connection refused"))
	           success = false;
	           else { System.out.println (e.getMessage () + "!");
	                  e.printStackTrace ();
	                  System.exit (1);
	                }
	      }
	      catch (SocketTimeoutException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - a time out occurred in establishing the connection to: " +
	                                 serverHostName + "." + serverPortNumb + "!");
	        success = false;
	      }
	      catch (IOException e)                           // erro fatal --- outras causas
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - an undetermined error occurred in establishing the connection to: " +
	                                 serverHostName + "." + serverPortNumb + "!");
	        e.printStackTrace ();
	        System.exit (1);
	      }
		
		if(!success) return (success);
		
		try
	      { out = new ObjectOutputStream (commSocket.getOutputStream ());
	      }
	      catch (IOException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - could not open socket outgoing channel!");
	        e.printStackTrace ();
	        System.exit (1);
	      }

	      try
	      { in = new ObjectInputStream (commSocket.getInputStream ());
	      }
	      catch (IOException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - could not open socket input channel!");
	        e.printStackTrace ();
	        System.exit (1);
	      }

	      return (success);
	}
	
	/**
	 * Closing the communication channel
	 * Closing the input and output socket streams
	 * Closing the communication socket
	 */
	
	public void close(){
		try
	      { in.close();
	      }
	      catch (IOException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - could not close socket input channel!");
	        e.printStackTrace ();
	        System.exit (1);
	      }

	      try
	      { out.close();
	      }
	      catch (IOException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - could not close socket outgoing channel!");
	        e.printStackTrace ();
	        System.exit (1);
	      }

	      try
	      { commSocket.close();
	      }
	      catch (IOException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - could not close the communication socket!");
	        e.printStackTrace ();
	        System.exit (1);
	      }
	}
	
	/**
	 * Reading an object from the communication channel
	 * @return object read
	 */
	public Object readObject(){
		Object fromServer = null;                            // object

	      try
	      { fromServer = in.readObject ();
	      }
	      catch (InvalidClassException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - the read object is not susceptible to deserialization!");
	        e.printStackTrace ();
	        System.exit (1);
	      }
	      catch (IOException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - error in reading an input channel object from the communication socket!");
	        e.printStackTrace ();
	        System.exit (1);
	      }
	      catch (ClassNotFoundException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - the read object corresponds to an unknown data type!");
	        e.printStackTrace ();
	        System.exit (1);
	      }

	      return fromServer;
	}
	
	/**
	 * Writing an object to the communication channel
	 * @param toServer Object to be written
	 */
	public void writeObject (Object toServer)
	   {
	      try
	      { out.writeObject (toServer);
	      }
	      catch (InvalidClassException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - the object to be written is not serializable!");
	        e.printStackTrace ();
	        System.exit (1);
	      }
	      catch (NotSerializableException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - the object to be written belongs to a non-serializable data type!");
	        e.printStackTrace ();
	        System.exit (1);
	      }
	      catch (IOException e)
	      { System.out.println(Thread.currentThread ().getName () +
	                                 " - error writing an object to the output channel of the communication socket!");
	        e.printStackTrace ();
	        System.exit (1);
	      }
	   }
}
