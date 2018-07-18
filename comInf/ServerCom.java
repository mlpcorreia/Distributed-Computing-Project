package comInf;

import java.io.*;
import java.net.*;

/**
 *   This type of data implements the communication channel, server side, for 
 *   communication based on message passing over sockets using the TCP protocol.
 *   Data transfer is based on objects, one object at a time.
 */

public class ServerCom
{
  /**
   *  Listening socket
   *    @serialField listeningSocket
   */

   private ServerSocket listeningSocket = null;

  /**
   *  Communication socket
   *    @serialField commSocket
   */

   private Socket commSocket = null;

  /**
   *  Server listening port number
   *    @serialField serverPortNumb
   */

   private int serverPortNumb;

  /**
   *  Communication channel input stream
   *    @serialField in
   */

   private ObjectInputStream in = null;

  /**
   *  Communication channel output stream
   *    @serialField out
   */

   private ObjectOutputStream out = null;

  /**
   *  Constructs a communication channel (Type 1).
   *
   *    @param portNumb Server listening port number
   */

   public ServerCom (int portNumb)
   {
      serverPortNumb = portNumb;
   }

  /**
   *  Constructs a communication channel (Type 2).
   *
   *    @param portNumb Server listening port number
   *    @param lSocket listening socket
   */

   public ServerCom (int portNumb, ServerSocket lSocket)
   {
      serverPortNumb = portNumb;
      listeningSocket = lSocket;
   }

  /**
   *  Establishment of service.
   *  Instantiation of a listening socket and its association to the local machine address 
   *  and the public listening port.
   */

   public void start ()
   {
      try
      { listeningSocket = new ServerSocket (serverPortNumb);
        setTimeout (10000);
      }
      catch (BindException e)                         // erro fatal --- port já em uso
      { System.out.println(Thread.currentThread ().getName () +
                                 " - it was not possible to connect the listening socket to port: " +
                                 serverPortNumb + "!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (IOException e)                           // erro fatal --- outras causas
      { System.out.println(Thread.currentThread ().getName () +
                                 " - there was an undetermined error in the association of the listening socket with port: " +
                                 serverPortNumb + "!");
        e.printStackTrace ();
        System.exit (1);
      }
   }

  /**
   *  Closing of service.
   *  Close the listening socket.
   */

   public void end ()
   {
      try
      { listeningSocket.close ();
      }
      catch (IOException e)
      { System.out.println(Thread.currentThread ().getName () +
                                 " - could not close listening socket!");
        e.printStackTrace ();
        System.exit (1);
      }
   }

  /**
   *  Listening process.
   *  Creation of a communication channel for a pending request.
   *  Instantiation of a communication socket and its association with the client address.
   *  Opening the input and output streams of the socket.
   *
   *    @return communication channel
   *    @throws SocketTimeoutException Signals that a timeout has occurred on a socket accept.
   */

   public ServerCom accept () throws SocketTimeoutException
   {
      ServerCom scon;                                      // canal de comunicação

      scon = new ServerCom(serverPortNumb, listeningSocket);
      try
      { scon.commSocket = listeningSocket.accept();
      }
      catch (SocketTimeoutException e)
      { throw new SocketTimeoutException ("Timeout!");
      }
      catch (SocketException e)
      { System.out.println(Thread.currentThread ().getName () +
                                 " - the listening socket was closed during the listening process!");
        e.printStackTrace ();
        System.exit (1);
      }
      catch (IOException e)
      { System.out.println(Thread.currentThread ().getName () +
                                 " - could not open a communication channel for a pending request!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { scon.in = new ObjectInputStream (scon.commSocket.getInputStream ());
      }
      catch (IOException e)
      { System.out.println(Thread.currentThread ().getName () +
                                 " - could not open socket input channel!");
        e.printStackTrace ();
        System.exit (1);
      }

      try
      { scon.out = new ObjectOutputStream (scon.commSocket.getOutputStream ());
      }
      catch (IOException e)
      { System.out.println(Thread.currentThread ().getName () +
                                 " - could not open socket outgoing channelt!");
        e.printStackTrace ();
        System.exit (1);
      }

      return scon;
   }

  /**
   *  Close the communication channel.
   *  Close the input and output streams of the socket.
   *  Close the communication socket.
   */

   public void close ()
   {
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
                                 " - could not close communication socket!");
        e.printStackTrace ();
        System.exit (1);
      }
   }

  /**
   *  Definition of a time out of listening.
   *  @param time time out of listening
   */
   public void setTimeout (int time)
   {
      try
      { listeningSocket.setSoTimeout (time);
      }
      catch (SocketException e)
      { System.out.println(Thread.currentThread ().getName () +
                                 " - there was an error in setting a listening timeout!");
        e.printStackTrace ();
        System.exit (1);
      }
   }

  /**
   *  Reading an object from the communication channel.
   *
   *    @return read object
   */

   public Object readObject ()
   {
      Object fromClient = null;                            // objecto

      try
      { fromClient = in.readObject ();
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

      return fromClient;
   }

  /**
   *  Writing an object in the communication channel.
   *
   *    @param toClient object to be written
   */

   public void writeObject (Object toClient)
   {
      try
      { out.writeObject (toClient);
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
                                 " - error in writing an object of the communication socket output channel!");
        e.printStackTrace ();
        System.exit (1);
      }
   }
}